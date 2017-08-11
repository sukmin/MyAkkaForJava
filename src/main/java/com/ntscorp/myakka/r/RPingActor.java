/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.r;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.BroadcastPool;
import akka.routing.ConsistentHashingPool;
import akka.routing.RandomPool;
import akka.routing.RandomRoutingLogic;
import akka.routing.RoundRobinPool;
import akka.routing.ScatterGatherFirstCompletedPool;
import akka.routing.SmallestMailboxPool;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class RPingActor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef childRouter;

	public RPingActor() {
		childRouter = getContext().actorOf(new RoundRobinPool(5).props(Props.create(RPing1Actor.class)), "ping1Actor");
		// Random
		//new RandomPool() : 랜덤으로 아무나에게 전송
		//new SmallestMailboxPool() : 메일박스에 메시지수가 가장 적은 녀석에게 할당
		//new BroadcastPool : 전체에 전송
		//new ScatterGatherFirstCompletedPool() : 전체에 전송하고 가장 결과가 빠르게 온 것에 취함. 클러스터 환경에서 유용
		//new ConsistentHashingPool() : 키의 해시값으로 전담 라우티를 할당
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			for (int i = 0; i < 10; i++) {
				childRouter.tell(i, getSelf());
			}
			log.info("PingActor sent 10 messages to the router.");
		} else {
			unhandled(message);
		}
	}

}
