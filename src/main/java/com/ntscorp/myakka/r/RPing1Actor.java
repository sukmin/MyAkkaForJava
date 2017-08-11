/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.r;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class RPing1Actor extends UntypedAbstractActor {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof Integer) {
			Integer msg = (Integer)message;
			log.info("Ping1Actor({}) received {}", hashCode(), msg);
			work(msg);
		}
	}

	private void work(Integer n) throws Exception {
		log.info("Ping1Actor({}) working on {}", hashCode(), n);
		Thread.sleep(1000); // 실전에서는 절대 금물!!!
		log.info("Ping1Actor({}) completed ", hashCode());
	}
}
