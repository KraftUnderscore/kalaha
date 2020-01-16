package Game

class TextGUI extends Interface {
  override def displayStartingPlayer(plr: Boolean): Unit = {
    print("----- Zaczyna gracz #")
    if(plr) println("1")
    else println("2")
  }
  /*
  P2     [12]-[11]-[10]-[9]-[8]-[7]
     {13}                        {6}
         [0]-[1]-[2]-[3]-[4]-[5]
  P1      1   2   3   4   5   6


  P1     [5]-[4]-[3]-[2]-[1]-[0]
     {6}                        {13}
         [7]-[8]-[9]-[10]-[11]-[12]
  P2      1   2   3   4   5   6

   */

  override def displayGameBoard(plr : Boolean, gameState: Array[Int]): Unit = {
    if(plr){
      println("----------------Tura gracza P1--------------------")
      println(s"P2     [${gameState(12)}]-[${gameState(11)}]" +
        s"-[${gameState(10)}]-[${gameState(9)}]" +
        s"-[${gameState(8)}]-[${gameState(7)}]")
      println(s"   {${gameState(GameManager.PlayerTwoBase)}}" +
        s"                        {${gameState(GameManager.PlayerOneBase)}}")

      println(s"       [${gameState(0)}]-[${gameState(1)}]" +
        s"-[${gameState(2)}]-[${gameState(3)}]" +
        s"-[${gameState(4)}]-[${gameState(5)}]")
      println("P1      1   2   3   4   5   6")
    }else{
      println("----------------Tura gracza P2--------------------")
      println(s"P1     [${gameState(5)}]-[${gameState(4)}]" +
        s"-[${gameState(3)}]-[${gameState(2)}]" +
        s"-[${gameState(1)}]-[${gameState(0)}]")

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

  override def humanPrompt: Int = {
    print("Wybierz dolek: ")
    val input = scala.io.StdIn.readInt()
    if(input>=1 || input<=6){
      input
    }else{
      println("Nie ma takiego dolka!")
      humanPrompt
    }
  }

  override def AIPrompt(choice: Int): Unit = {
    println(s"Wybieram dolek: $choice")
  }

  def displaySteal(plr: Boolean, index: Int): Unit = {
    if(plr)println(s"----------------------Gracz 1 kradnie graczowi 2 kulki z pola $index!")
    else println(s"-----------------------Gracz 2 kradnie graczowi 1 kulki z pola $index!")
  }
}
