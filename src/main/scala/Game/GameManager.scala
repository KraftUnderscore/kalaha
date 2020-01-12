package Game

import akka.actor.{Actor, ActorRef}

import scala.util.Random

class GameManager(val startingValues : Int, val player1 : ActorRef, val player2 : ActorRef, val interface: Interface) extends Actor{
  var playerOneTurn = true
  val gameState : Array[Int] = Array.ofDim(GameManager.GameStateSize)
  val r  = Random
  var isPlaying : Boolean = true

  def resetGame():Unit = {
    for(x<-0 until GameManager.GameStateSize)
      if(x!=GameManager.PlayerOneBase || x!=GameManager.PlayerTwoBase) gameState(x) = startingValues
      else gameState(x) = 0
  }

  def callPlayerMove():Unit = {
    if(playerOneTurn) player1 ! Player.Move(gameState)
    else player2 ! Player.Move(gameState)
  }

  def startGame():Unit = {
    resetGame()
    val nextPlr = r.nextInt()
    if(nextPlr == 0) playerOneTurn = true
    else playerOneTurn = false
    interface.displayStartingPlayer(playerOneTurn)
    callPlayerMove()
  }

  def checkIfLost:Boolean = {
    var sum = 0
    for(x<-0 until GameManager.PlayerOneBase)sum+=gameState(x)
    if(sum == 0) return true
    sum = 0
    for(x<-GameManager.PlayerOneBase+1 until GameManager.PlayerTwoBase)sum+=gameState(x)
    if(sum == 0) true
    else false
  }

  def registerPlayerMove(index : Int) : Unit ={
    val valueAtIndex = gameState(index)
    gameState(index) = 0

    for(x<- 1 to valueAtIndex)
      gameState((x+valueAtIndex)%GameManager.GameStateSize) += 1

    if(checkIfLost) {
      interface.displayWinner(!playerOneTurn)
      isPlaying = false
    }else {
      if(playerOneTurn) {
        if(index+valueAtIndex > GameManager.PlayerOneBase) playerOneTurn = false
      }else{
        if(index+valueAtIndex > GameManager.PlayerTwoBase) playerOneTurn = true
      }

      interface.displayGameBoard(playerOneTurn, gameState)
      callPlayerMove()
    }
  }

  override def receive: Receive = {
    case GameManager.Start => startGame()
    case GameManager.RegisterMove (mov) => registerPlayerMove(mov)
  }
}

object GameManager{
  case object Start
  case class RegisterMove(move : Int)
  val GameStateSize = 14
  val PlayerOneBase = 6
  val PlayerTwoBase = 13
}
