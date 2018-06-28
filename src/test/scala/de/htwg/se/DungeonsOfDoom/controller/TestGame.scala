package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, DoorState, Floor, Walkable}
import de.htwg.se.DungeonsOfDoom.model.items.{HealingPotion, Key, Weapon}
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

object TestGame {
  def init() : Unit = {
    val player = Player("Herbert", 5, 5, 5, 5, 5, 5, 5, 5, 5)
    val weapon = Weapon("Daggerbert Stab", 1, 1, 1, 1, 1, 1)
    BoardInteraction.reset(player)
    BoardInteraction.player.inventory += weapon
    ItemInteraction.use(BoardInteraction.player, weapon)
    BoardInteraction.board.map(1)(2).asInstanceOf[Floor].inventory ++= ListBuffer(new Key(), HealingPotion("Small Healing Potion", 1, 1, 1, 5))
    BoardInteraction.board.map(2)(3) = Door(DoorState.closed)
    val enemy = Enemy("Günther", 5, 2, 2, 5 , 2, 2, 5, 2, 2, Array(Weapon("Günther-Faust", 1, 1, 1, 1, 1, 1)), (2,2))
    BoardInteraction.board.map(2)(2).asInstanceOf[Walkable].visitedBy = Some(enemy)
    BoardInteraction.enemyList += enemy
  }
}
