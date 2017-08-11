/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.r;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class RApp {

	public static void main(String[] args){
		ActorSystem actorSystem = ActorSystem.create("RSystem");
		ActorRef ping = actorSystem.actorOf(Props.create(RPingActor.class), "rPingActor");
		ping.tell("start", ActorRef.noSender());
	}

}
