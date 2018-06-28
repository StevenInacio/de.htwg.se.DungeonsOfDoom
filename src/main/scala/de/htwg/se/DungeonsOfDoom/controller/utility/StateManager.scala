package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.model.board.Map
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

trait StateManager {
  def getState: State

  def saveState(state: State): Unit

  def loadState: (Map, Player, ListBuffer[Enemy])
}