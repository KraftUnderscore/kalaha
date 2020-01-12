package Game

///DOESNT WORK :C
class AlwaysFirstControls(interface: Interface) extends Controls {
  override def makeMove(plr: Boolean, gameState: Array[Int]): Int = {
    if(plr)
      for(x<-0 until GameManager.PlayerOneBase)
        if(gameState(x)!=0){
          interface.AIPrompt(x+1)
          return x
        }

    else
      for(x<-0 until GameManager.PlayerOneBase)
        if(gameState(x+GameManager.PlayerOneBase+1)!=0){
          interface.AIPrompt(x+1)
          return x
        }
    0 //niemozliwe
  }
}
