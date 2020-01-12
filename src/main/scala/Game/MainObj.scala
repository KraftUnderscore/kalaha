package Game

import akka.actor.{ActorRef, ActorSystem, Props}

object MainObj extends App {
  val actorSystem = ActorSystem()
  val startingValues = 6
  val interface = new TextGUI()
  val player1 : ActorRef = actorSystem.actorOf(Props(classOf[Player], new HumanControls(interface)))
  val player2 : ActorRef = actorSystem.actorOf(Props(classOf[Player], new HumanControls(interface)))
  val gameManager : ActorRef = actorSystem.actorOf(Props(classOf[GameManager], startingValues, player1, player2, interface))
  gameManager ! GameManager.Start
}
