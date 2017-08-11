/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ntscorp.myakka.h;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;

/**
 * @author sukmin.kwon
 * @since 2017-07-27
 */
public class HPingAbstractActorWithStash extends AbstractActorWithStash {

	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;

	public HPingAbstractActorWithStash() {
		child = context().actorOf(Props.create(HPing1Actor.class), "hPing1Actor");
		getContext().become(initial);
	}

	@Override
	public Receive createReceive() {
		return null;
	}

	/** 맨 처음 상태에서 "work" 메시지를 받으면 zeroDone 상태가 된다. */
	private UntypedAbstractActor.Receive initial = receiveBuilder()
		.match(String.class, new FI.TypedPredicate<String>() {
			@Override
			public boolean defined(String s) {
				return "work".equals(s);
			}
		}, new FI.UnitApply<String>() {
			@Override
			public void apply(String s) throws Exception {
				child.tell("work", getSelf());
				getContext().become(zeroDone);
			}
		}).matchAny(obj -> stash())
		.build();

	/** zeroDone 상태에서 "done" 메시지를 받으면 oneDone 상태가 된다. */
	private UntypedAbstractActor.Receive zeroDone = receiveBuilder()
		.match(String.class, new FI.TypedPredicate<String>() {
			@Override
			public boolean defined(String s) {
				return "done".equals(s);
			}
		}, new FI.UnitApply<String>() {
			@Override
			public void apply(String s) throws Exception {
				log.info("Received the first done");
				getContext().become(oneDone);
			}
		}).matchAny(obj -> stash())
		.build();

	/** oneDone 상태에서 "done" 메시지를 받으면 allDone 상태가 된다. */
	private UntypedAbstractActor.Receive oneDone = receiveBuilder()
		.match(String.class, new FI.TypedPredicate<String>() {
			@Override
			public boolean defined(String s) {
				return "done".equals(s);
			}
		}, new FI.UnitApply<String>() {
			@Override
			public void apply(String s) throws Exception {
				log.info("Received the second done");
				unstashAll();
				getContext().become(allDone);
			}
		}).matchAny(obj -> stash())
		.build();

	/** allDone 상태에서 "reset" 메시지를 받으면 다시 initial 상태가 된다. */
	private UntypedAbstractActor.Receive allDone = receiveBuilder()
		.match(String.class, new FI.TypedPredicate<String>() {
			@Override
			public boolean defined(String s) {
				return "reset".equals(s);
			}
		}, new FI.UnitApply<String>() {
			@Override
			public void apply(String s) throws Exception {
				log.info("Received a reset");
				getContext().become(initial);
			}
		}).build();

}
