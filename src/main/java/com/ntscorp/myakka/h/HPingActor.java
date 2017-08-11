/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.h;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import akka.japi.pf.FI;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class HPingActor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;

	// 엥 이거 이렇게 써도 되는거에요?
	// 넵 됩니다. 액터는 항상 1개의 쓰레드가 돌리는걸 보장하기에 내부에서 상태를 변경해도 상관없습니다. 물론 만능은 아님.
	private int count = 0;

	public HPingActor() {
		child = context().actorOf(Props.create(HPing1Actor.class), "hPing1Actor");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			if ("work".equals(msg)) {
				child.tell(msg, getSelf());
			} else if ("done".equals(msg)) {
				if (count == 0) {
					count++;
				} else {
					log.info("all works are completed.");
					count = 0;
				}
			}
		}
	}

}
