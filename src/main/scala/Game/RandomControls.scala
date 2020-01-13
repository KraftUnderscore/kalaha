package Game

import scala.util.Random

class RandomControls extends Controls {
  override def makeMove(plr : Boolean, gameState: Array[Int]): Int = {
    val choice = Random.nextInt(GameManager.PlayerOneBase)
    GameManager.Interface.AIPrompt(choice+1)
    choice
  }
}
