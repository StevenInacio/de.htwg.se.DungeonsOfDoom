package de.htwg.se.DungeonsOfDoom.controller.utility

import com.google.inject.Inject
import de.htwg.se.DungeonsOfDoom.model.board.Map
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

@Inject
class SaveManager(stateManager: StateManager) {
  val this.stateManager = stateManager

  def getState: State = {
    stateManager.getState
  }

  def saveState(state: State): Unit = {
    stateManager.saveState(state)
  }

  def loadState(): (Map, Player, ListBuffer[Enemy]) = {
    stateManager.loadState
  }

}