package com.ntscorp.myakka;

import java.util.Optional;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends UntypedAbstractActor {

	private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
	private ActorRef pong;

	@Override
	// 생성자가 완료된 직후에 실행
	public void preStart() throws Exception {
		this.pong = context().actorOf(Props.create(PongActor.class, getSelf()), "pongActor");
		logger.info("Ping preStart");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			String msg = (String)message;
			logger.info("Ping received {}", msg);
			pong.tell("ping", getSelf());
		}
	}

	@Override
	public void postStop() throws Exception {
		logger.info("Ping postStop");
	}

	@Override
	public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
		logger.info("Ping preRestart");
	}

	@Override
	public void postRestart(Throwable reason) throws Exception {
		logger.info("Ping postRestart");
	}
}
