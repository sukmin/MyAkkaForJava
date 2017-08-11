/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.e;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class EPing1Actor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child1;
	private ActorRef child2;

	public EPing1Actor() {
		child1 = context().actorOf(Props.create(EPing2Actor.class), "ePing2Actor");
		child2 = context().actorOf(Props.create(EPing3Actor.class), "ePing3Actor");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			if ("good".equals(msg) || "bad".equals(msg)) {
				log.info("Ping1Actor received {}", msg);
				child1.tell(msg, getSender());
				child2.tell(msg, getSender());
			}
		} else {
			unhandled(message);
		}
	}

	private static SupervisorStrategy STRATEGY =
		// 1분만에 재시도 10회 허용. 시간안에 횟수를 넘어간다면 재시도 하지 않는다.
		new OneForOneStrategy(10, Duration.create("1 minute"),
			new Function<Throwable, SupervisorStrategy.Directive>() {
				@Override
				public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
					if (t instanceof ArithmeticException) {
						// Ping2Actor는 "bad" 메시지를 받으면 ArithmeticException을 발생
						// 예외를 발생시킨 메시지를 무시하고 다음 메일박스에 있는 메시지를 처리하라
						return SupervisorStrategy.resume();
					} else if (t instanceof NullPointerException) {
						// Ping3Actor는 "bad" 메시지를 받으면 NullPointerException을 발생
						// restart는 객체가 완전히 새로 만들어진다. 만약 내부에서 사용하는 데이터가 있다면 preRestart에서 저장. 생성자 또는 postRestart에서 복구
						// 단, 액터의 실제 객체와 무관하게 메일박스는 영향을 받지 않는다.
						// resume과의 차이점은 예외때문에 내부 데이터가 오염되었다고 판단이 되면 사용하면 된다.
						// 생성자가 호출되기 전 preRestart호출
						// 생성자 호출
						// postRestart 호출
						return SupervisorStrategy.restart();
					} else if (t instanceof IllegalArgumentException) {
						// 형벌중에 사형에 해당. 너에겐 기회따윈 없다.
						// 다른 액터에 의해 다시 생성되기 전까지 다시 부활하지 않는다.
						return SupervisorStrategy.stop();
					} else {
						// 부모로 던진다.
						return SupervisorStrategy.escalate();
					}
				}
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return STRATEGY;
	}
}
