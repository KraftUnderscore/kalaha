package Game

import akka.actor.Actor

class Player(controls : Controls) extends Actor{
  def nextMove(gameState : Array[Int]):Int = {
    controls.makeMove(gameState)
  }
  override def receive: Receive = {
    case Player.Move(gameState) => sender ! GameManager.RegisterMove(nextMove(gameState))
  }
}

object Player{
  case class Move(gameState : Array[Int])
}