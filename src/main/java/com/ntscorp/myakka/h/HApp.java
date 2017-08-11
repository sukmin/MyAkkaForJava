/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.h;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class HApp {

	/*
	public static void main(String[] args) {

		ActorSystem actorSystem = ActorSystem.create("HSystem");
		ActorRef ping = actorSystem.actorOf(Props.create(HPingActor.class), "hPingActor");
		ping.tell("work", ActorRef.noSender());

	}
	*/


	public static void main(String[] args) {

		ActorSystem actorSystem = ActorSystem.create("HSystem");
		ActorRef ping = actorSystem.actorOf(Props.create(HPingAbstractActorWithStash.class), "hPingActor");
		ping.tell("work", ActorRef.noSender());
		ping.tell("reset", ActorRef.noSender());

	}


}
