package Game

///DOESNT WORK :C
class AlwaysFirstControls extends Controls {
  override def makeMove(plr: Boolean, gameState: Array[Int]): Int = {
    if(plr)
      for(x<-0 until GameManager.PlayerOneBase) {
        println("FIRSTPLR: "+x)
        if(gameState(x)!=0){
          GameManager.Interface.AIPrompt(x+1)
          return x
        }

    else
      for(x<-0 until GameManager.PlayerOneBase) {
        println("SECNODPLR: "+x)
        if(gameState(x+GameManager.PlayerOneBase+1)!=0){
          GameManager.Interface.AIPrompt(x+1)
          return x
        }
      }
      }
    0 //niemozliwe
  }
}
