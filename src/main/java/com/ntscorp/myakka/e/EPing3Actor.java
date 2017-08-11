/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.e;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class EPing3Actor extends UntypedAbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public EPing3Actor() {
		log.info("Ping3Actor constructgor..");
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message) throws Exception {
		log.info("Ping3Actor preRestart..");
	}

	@Override
	public void postRestart(Throwable reason) throws Exception {
		log.info("Ping3Actor postRestart..");
	}

	@Override
	public void postStop() throws Exception {
		log.info("Ping3Actor postStop..");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			if ("good".equals(msg)) {
				goodWork();
				getSender().tell("done", getSelf());
			} else if ("bad".equals(msg)) {
				badWork();
			}
		} else {
			unhandled(message);
		}
	}

	private void goodWork() throws Exception {
		log.info("Ping3Actor is good.");
	}

	/** 일부러 ArithmeticException을 발생시킨다 */
	private void badWork() throws Exception {
		throw new NullPointerException();
		//throw new IllegalArgumentException();
	}
}
