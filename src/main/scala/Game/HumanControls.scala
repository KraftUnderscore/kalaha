package Game

import scala.util.Random

class HumanControls extends Controls {
  override def makeMove(gameState: Array[Int]): Int = {
    Random.nextInt(6)
  }
}
