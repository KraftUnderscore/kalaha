package Game

import scala.util.Random

class RandomControls(interface: Interface) extends Controls {
  override def makeMove(plr : Boolean, gameState: Array[Int]): Int = {
    val choice = Random.nextInt(GameManager.PlayerOneBase)
    interface.AIPrompt(choice+1)
    choice
  }
}
