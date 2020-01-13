package Game

import akka.actor.{Actor, ActorRef}

import scala.util.Random

class GameManager(val player1 : ActorRef, val player2 : ActorRef) extends Actor{
  var playerOneTurn = true
  val gameState : Array[Int] = Array.ofDim(GameManager.GameStateSize)
  val r: Random.type = Random
  var turnCounter : Int = 0

  def resetGame():Unit = {
    for(x<-0 until GameManager.GameStateSize)
      if(x!=GameManager.PlayerOneBase || x!=GameManager.PlayerTwoBase) gameState(x) = GameManager.StartingValues
      else gameState(x) = 0
  }

  def callPlayerMove():Unit = {
    GameManager.Interface.displayGameBoard(playerOneTurn, gameState)
    if(playerOneTurn) player1 ! Player.Move(playerOneTurn, gameState)
    else player2 ! Player.Move(playerOneTurn, gameState)
  }

  def startGame():Unit = {
    resetGame()
    val nextPlr = 0
    if(nextPlr == 0) playerOneTurn = true
    else playerOneTurn = false
    GameManager.Interface.displayStartingPlayer(playerOneTurn)
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
    var _index = index
    if(!playerOneTurn) _index = index + GameManager.PlayerOneBase + 1
    val valueAtIndex = gameState(_index)
    gameState(_index) = 0

    for(x<- 1 to valueAtIndex)
      gameState((x+_index)%GameManager.GameStateSize) += 1

    if(checkIfLost) {
      playerOneTurn = gameState(GameManager.PlayerOneBase) > gameState(GameManager.PlayerTwoBase)
      GameManager.Interface.displayWinner(playerOneTurn)
    }else {
      if(playerOneTurn) {
        if(_index+valueAtIndex > GameManager.PlayerOneBase) playerOneTurn = false
      }else{
        if(_index+valueAtIndex > GameManager.PlayerTwoBase) playerOneTurn = true
      }

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
  val StartingValues = 5
  val GameStateSize = 14
  val PlayerOneBase = 6
  val PlayerTwoBase = 13
  val Interface : Interface = new TextGUI
}
