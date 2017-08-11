/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.h;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class HPing2Actor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			if ("work".equals(msg)) {
				log.info("Ping2 received {}", msg);
				work();
				getSender().tell("done", getSelf());
			}
		}
	}

	private void work() throws Exception {
		Thread.sleep(1000); // 실전에서는 절대 금물!!!
		log.info("Ping2 working...");
	}
}
