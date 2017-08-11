package com.ntscorp.myakka;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PongActor extends UntypedAbstractActor {

	private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
	private ActorRef ping;

	public PongActor(ActorRef ping) {
		this.ping = ping;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			logger.info("Pong received {}", msg);
			ping.tell("pong", getSelf());
			Thread.sleep(1000); // 실전에서는 절대 금지!
		}
	}
}
