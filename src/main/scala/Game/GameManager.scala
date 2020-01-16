package Game

import akka.actor.{Actor, ActorRef}

import scala.util.Random

class GameManager(val player1 : ActorRef, val player2 : ActorRef) extends Actor{
  var playerOneTurn = true  //tura gracza
  val gameState : Array[Int] = Array.ofDim(GameManager.GameStateSize) //wszystkie dolki wraz z bazami
  val r: Random.type = Random

  //ustawia wartosci dolkow na wartosci poczatkowe (i zeruje bazy)
  def resetGame():Unit = {
    for(x<-0 until GameManager.GameStateSize)
      if(x!=GameManager.PlayerOneBase && x!=GameManager.PlayerTwoBase) gameState(x) = GameManager.StartingValues
      else gameState(x) = 0
  }

  //wyswietlenie planszy i wyslanie komunikatu do odpowiedniego gracza (ktorego jest teraz tura)
  def callPlayerMove():Unit = {
    GameManager.Interface.displayGameBoard(playerOneTurn, gameState)
    if(playerOneTurn) player1 ! Player.Move(playerOneTurn, gameState)
    else player2 ! Player.Move(playerOneTurn, gameState)
  }

  //poczatek gry - reset wartosci i wylosowanie zaczynajacego gracza, po czym wezwanie jego ruchu
  def startGame():Unit = {
    resetGame()
    val nextPlr = Random.nextInt(2)
    if(nextPlr == 0) playerOneTurn = true
    else playerOneTurn = false
    GameManager.Interface.displayStartingPlayer(playerOneTurn)
    callPlayerMove()
  }

  //sprawdzenie, czy dolki graczy maja kulki - jak nie to przegrana
  def checkIfLost:Boolean = {
    var sum = 0
    for(x<-0 until GameManager.PlayerOneBase)sum+=gameState(x)
    if(sum == 0) return true
    sum = 0
    for(x<-GameManager.PlayerOneBase+1 until GameManager.PlayerTwoBase)sum+=gameState(x)
    if(sum == 0) true
    else false
  }

  //zarejestrowanie ruchu wykonanego przez gracza (komunikat od niego)
  def registerPlayerMove(index : Int) : Unit ={
    var _index = index
    if(!playerOneTurn) _index = index + GameManager.PlayerOneBase + 1 //jezeli gracz 2 to zmieniamy przedzial z 0-5 na 7-12
    val valueAtIndex = gameState(_index) //ilosc kuleczek w dolku => ile musimy sie przesunac do przodu, dodajac po 1 do dolkow
    gameState(_index) = 0 //zabieramy wszystkie kulki z dolka
    //dodajemy po 1 kuleczce na kazdym miejscu po prawej, do wartosci valueAtIndex
    for(x<- 1 to valueAtIndex)
      gameState((x+_index)%GameManager.GameStateSize) += 1
    //sprawdzamy, czy gra sie nie konczy
    if(checkIfLost) {
      //sumujemy do bazy graczy wszystkie dolki - gracz 1
      for(x<-0 until GameManager.PlayerOneBase) {
        gameState(GameManager.PlayerOneBase) += gameState(x)
        gameState(x) = 0
      }
      // --||-- gracz 2
      for(x<-GameManager.PlayerOneBase+1 until GameManager.PlayerTwoBase) {
        gameState(GameManager.PlayerTwoBase) += gameState(x)
        gameState(x) = 0
      }
      //wyswietlamy ostateczny wynik planszy i wybieramy wygranego
      GameManager.Interface.displayGameBoard(playerOneTurn, gameState)
      playerOneTurn = gameState(GameManager.PlayerOneBase) > gameState(GameManager.PlayerTwoBase)
      //ogloszenie wyniku, zamkniecie systemu aktorow
      GameManager.Interface.displayWinner(playerOneTurn)
      context.system.terminate()
    }else {
      //sprawdzamy, czy kulka wyladowala na polu gracza, ktorego teraz jest tura => gra jeszcze raz, moze zabiera kulki przeciwnika
      if(playerOneTurn) {
        if(_index+valueAtIndex > GameManager.PlayerOneBase) playerOneTurn = false //sprawdzenie czy jego tura
        if(gameState((valueAtIndex+_index)%GameManager.GameStateSize) == 1){
          val toSteal = gameState(GameManager.PlayerOneBase + 1 + (GameManager.PlayerOneBase - index))
          println("ELO ELO ELO UKRADNIJ "+toSteal)
        } //TODO: ZABRAC Z DOLKA PRZECIWNIKA
      }else{
        if(_index+valueAtIndex > GameManager.PlayerTwoBase) playerOneTurn = true
        if(gameState((valueAtIndex+_index)%GameManager.GameStateSize) == 1) {
          val toSteal = gameState(GameManager.PlayerOneBase - 1 - (GameManager.PlayerOneBase - _index))
          println("ELO ELO ELO UKRADNIJ " + toSteal)
        }
      }

      callPlayerMove()
    }
  }

  override def receive: Receive = {
    case GameManager.Start => startGame()
    case GameManager.RegisterMove (mov) => registerPlayerMove(mov)
  }
}
//stale i komunikaty do GameManager
object GameManager{
  case object Start
  case class RegisterMove(move : Int)
  val StartingValues = 5
  val GameStateSize = 14
  val PlayerOneBase = 6
  val PlayerTwoBase = 13
  val Interface : Interface = new TextGUI
}
