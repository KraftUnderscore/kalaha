package Game

class AlwaysLastControls extends Controls {
  override def makeMove(plr: Boolean, gameState: Array[Int]): Int = {
    var start : Int = 0
    var end : Int = GameManager.PlayerOneBase
    if(!plr){
      start = GameManager.PlayerOneBase+1
      end = GameManager.PlayerTwoBase
    }
    for(x<-end-1 to start by -1){
      if(gameState(x)!=0){
        GameManager.Interface.AIPrompt((x+1)%(GameManager.PlayerOneBase+1))
        return x%(GameManager.PlayerOneBase+1)
      }
    }
    //niemozliwe, bo wszystkie wartosci musialy by byc zerami
    // - a wtedy GameManager wykrylby to i zakonczyl gre odpowiednio wczesnie
    -1
  }
}
