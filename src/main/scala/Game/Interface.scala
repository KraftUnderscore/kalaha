package Game

trait Interface {
  def displayStartingPlayer(plr : Boolean)
  def displayGameBoard(plr : Boolean, gameState : Array[Int])
  def displayWinner(plr : Boolean)
}
