package Game

trait Interface {
  def displayStartingPlayer(plr : Boolean)
  def displayGameBoard(plr : Boolean, gameState : Array[Int])
  def displayWinner(plr : Boolean)
  def humanPrompt : Int
  def AIPrompt(choice : Int)
  def displaySteal(plr : Boolean, index : Int)
}
