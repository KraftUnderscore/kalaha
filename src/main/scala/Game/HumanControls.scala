package Game

class HumanControls(interface: Interface) extends Controls {
  override def makeMove(plr : Boolean, gameState: Array[Int]): Int = {
    val input = interface.humanPrompt-1
    input
  }
}
