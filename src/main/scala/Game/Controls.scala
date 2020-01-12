package Game

trait Controls {
  def makeMove(plr : Boolean, gameState : Array[Int]) : Int
}
