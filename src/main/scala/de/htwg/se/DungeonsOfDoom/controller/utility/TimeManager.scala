package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.Walkable

object TimeManager {

  def advanceTime(): Unit = {
    val dead = BoardInteraction.enemyList.filter(x => x.currentHealth == 0)
    for (corpse <- dead) {
      val pos = corpse.currentPosition
      BoardInteraction.board.map(pos._1)(pos._2).asInstanceOf[Walkable].visitedBy = None
      BoardInteraction.enemyList -= corpse
    }
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


}
