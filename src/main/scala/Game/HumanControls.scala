package Game

class HumanControls extends Controls {
  override def makeMove(plr : Boolean, gameState: Array[Int]): Int = {
    val input = GameManager.Interface.humanPrompt-1
    input
  }
}
