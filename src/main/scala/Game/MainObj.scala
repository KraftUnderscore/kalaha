package Game

import akka.actor.{ActorRef, ActorSystem, Props}

object MainObj extends App {
  val actorSystem = ActorSystem()
  val player1 : ActorRef = actorSystem.actorOf(Props(classOf[Player], new AlwaysLastControls))
  val player2 : ActorRef = actorSystem.actorOf(Props(classOf[Player], new AlwaysFirstControls))
  val gameManager : ActorRef = actorSystem.actorOf(Props(classOf[GameManager], player1, player2))
  gameManager ! GameManager.Start
}
