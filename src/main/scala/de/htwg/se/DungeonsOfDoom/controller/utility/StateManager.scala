package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Map, Walkable}
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

trait StateManager {
  def getState: State

  def saveState(state: State): Unit

  def loadState: (Map, Player, ListBuffer[Enemy])

  def undo(): Unit = {
    val result = loadState
    BoardInteraction.board = result._1
    BoardInteraction.player = result._2
    BoardInteraction.enemyList = result._3
    BoardInteraction.board.map(result._2.currentPosition._1)(result._2.currentPosition._2).asInstanceOf[Walkable].visitedBy = Some(result._2)
    for (enemy <- BoardInteraction.enemyList) {
      BoardInteraction.board.map(enemy.currentPosition._1)(enemy.currentPosition._2).asInstanceOf[Walkable].visitedBy = Some(enemy)
    }
  }

}