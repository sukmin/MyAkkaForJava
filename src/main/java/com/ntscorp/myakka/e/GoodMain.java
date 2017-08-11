/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.e;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class GoodMain {

	public static void main(String[] args) {

		ActorSystem actorSystem = ActorSystem.create("GoodMain");
		ActorRef ping = actorSystem.actorOf(Props.create(EPingActor.class), "ePingActor");
		//ping.tell("good", ActorRef.noSender());
		ping.tell("bad", ActorRef.noSender());

	}

}
