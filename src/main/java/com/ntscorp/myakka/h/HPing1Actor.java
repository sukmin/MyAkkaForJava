/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.h;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class HPing1Actor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child1;
	private ActorRef child2;

	public HPing1Actor() {
		child1 = context().actorOf(Props.create(HPing2Actor.class), "hPing2Actor");
		child2 = context().actorOf(Props.create(HPing3Actor.class), "hPing3Actor");
	}

	@Override
	public void onReceive(Object message) throws Throwable {

		if (message instanceof String) {
			String msg = (String)message;
			if ("work".equals(msg)) {
				log.info("Ping1 received {}", msg);
				child1.tell("work", getSender());
				child2.tell("work", getSender());
			}
		}

	}
}
