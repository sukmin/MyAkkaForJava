package com.ntscorp.myakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class App {

	public static void main(String[] args) {

		// 시스템은 쓰레드 스케줄링, 구성파일, 로그 서비스 등을 공유
		// 시스템은 무거운 객체이기 때문에 하나의 App에서 하나의 시스템을 가지는 것이 일반적
		ActorSystem actorSystem = ActorSystem.create("MyAkka");

		// 시스템을 통해 액터를 만든다. Props는 레시피. 뒤에는 액터이름.
		// 액터는 트리와 비슷한 계층구조를 가진다.
		// 액터를 new 생성하는 것은 막혀있다.
		// 액터의 물리적 위치는 알 필요가 없음.(장소투명성) 심지어 다른 장비에 있을 수 있다.
		ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");

		// 액터가 액터에게 메세지를 전달하는것이 아카의 프로그래밍 방법
		// 메시지, 발신자
		ping.tell("start", ActorRef.noSender());

	}

}
