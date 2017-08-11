/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.e;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class EPingActor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;

	public EPingActor() {
		child = context().actorOf(Props.create(EPing1Actor.class), "ePing1Actor");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			if ("good".equals(msg) || "bad".equals(msg)) {
				child.tell(msg, getSelf());
			} else if ("done".equals(msg)) {
				log.info("all works are successully completed.");
			}
		} else {
			unhandled(message);
		}
	}
}
