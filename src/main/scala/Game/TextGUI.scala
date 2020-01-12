package Game

class TextGUI extends Interface {
  override def displayStartingPlayer(plr: Boolean): Unit = {
    print("----- Zaczyna gracz #")
    if(plr) println("1")
    else println("2")
  }
  /*
  P2     [1]-[2]-[3]-[4]-[5]-[6]
     {3}                        {7}
         [1]-[2]-[3]-[4]-[5]-[6]
  P1      1   2   3   4   5   6

   */

  override def displayGameBoard(plr : Boolean, gameState: Array[Int]): Unit = {
    if(plr){
      println("----------------Tura gracza P1--------------------")
      println(s"P2     [${gameState(7)}]-[${gameState(8)}]" +
        s"-[${gameState(9)}]-[${gameState(10)}]" +
        s"-[${gameState(11)}]-[${gameState(12)}]")
      println(s"   {${gameState(GameManager.PlayerTwoBase)}" +
        s"                        {${gameState(GameManager.PlayerOneBase)}")

      println(s"       [${gameState(0)}]-[${gameState(1)}]" +
        s"-[${gameState(2)}]-[${gameState(3)}]" +
        s"-[${gameState(4)}]-[${gameState(5)}]")
      println("P1      1   2   3   4   5   6")
    }else{
      println("----------------Tura gracza P2--------------------")
      println(s"P1     [${gameState(0)}]-[${gameState(1)}]" +
        s"-[${gameState(2)}]-[${gameState(3)}]" +
        s"-[${gameState(4)}]-[${gameState(5)}]")

      println(s"   {${gameState(GameManager.PlayerOneBase)}}" +
        s"                        {${gameState(GameManager.PlayerTwoBase)}}")
      println(s"       [${gameState(7)}]-[${gameState(8)}]" +
        s"-[${gameState(9)}]-[${gameState(10)}]" +
        s"-[${gameState(11)}]-[${gameState(12)}]")
      println("P2      1   2   3   4   5   6")
    }

  }

  override def displayWinner(plr: Boolean): Unit = {
    print("----- Gratulacje! Wygrywa gracz #")
    if(plr) println("1")
    else println("2")
  }
}
