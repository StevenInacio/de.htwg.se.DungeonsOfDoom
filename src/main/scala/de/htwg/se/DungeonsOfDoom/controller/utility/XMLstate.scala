package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

class XMLstate extends StateManager {
  override def getState: State = ???

  override def saveState(state: State): Unit = ???

  override def loadState: (Map, Player, ListBuffer[Enemy]) = ???
}
