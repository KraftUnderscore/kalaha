package Game

import akka.actor.Actor

class Player(controls : Controls) extends Actor{
  def nextMove(plr:Boolean, gameState : Array[Int]):Int = {
    controls.makeMove(plr, gameState)
  }
  override def receive: Receive = {
    case Player.Move(plr, gameState) => sender ! GameManager.RegisterMove(nextMove(plr,gameState))
  }
}

object Player{
  case class Move(plr : Boolean, gameState : Array[Int])
}