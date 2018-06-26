package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.model.board.Field

object TimeManager {
  //def getState(map: Array[Array[Field]]) : State = {
    // _TODO
  //}
  //def saveState(state: State) : Unit = {
    // _TODO
  //}
  //def advanceTime() : Unit = {
  //}
  //def undo() : Unit = {
    // _TODO
  //}
  //def loadState(xml: File) : State = {
    // _TODO
  //}

  //DEBUG --- this belongs to the UI
    def startGame() : Unit = {
      while(playerTurn("attack") != "quit"){
        enemyTurn()
      }
    }
  // returns false if the user wants to exit the game
  def playerTurn(command : String) : String = {
    var iter = command.split(' ')
    for(i <- iter){

    }

    true//DEBUG
    }

  def enemyTurn() : Unit = {
    //TODO enemy does evil things
  }
}
