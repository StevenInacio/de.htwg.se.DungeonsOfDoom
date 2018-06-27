package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction

class TimeManager(stateManager: StateManager) {
  // returns false if the user wants to exit the game
  def getState: State = {
    stateManager.getState
  }

  def saveState(state: State): Unit = {
    stateManager.saveState(state)
  }

  def advanceTime(): Unit = {
    for (enemy <- BoardInteraction.enemyList) {
      val (x, y) = enemy.currentPosition
      val (pX, pY) = BoardInteraction.player.currentPosition
      if (((pX - x) + (pY - y)).abs == 1) {
        BoardInteraction.checkAction(enemy, BoardInteraction.board.map(pX)(pY), (pX, pY))
      }
      else {
        val rand = scala.util.Random
        val dir: String = rand.nextInt(4) match {
          case 0 => "Up"
          case 1 => "Down"
          case 2 => "Left"
          case 3 => "Right"
        }
        BoardInteraction.check(enemy, dir)
      }
    }
    val rand = scala.util.Random
    if ((rand.nextInt(100) + 1) <= 5) {
      BoardInteraction.spawnEnemy()
    }
  }

  def undo(): Unit = {
    val result = stateManager.loadState
    BoardInteraction.board = result._1
    BoardInteraction.player = result._2
    BoardInteraction.enemyList = result._3
  }
}
