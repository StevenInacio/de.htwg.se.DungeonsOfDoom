package de.htwg.se.DungeonsOfDoom.controller.board


import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.controller.pawn.PawnInteraction
import de.htwg.se.DungeonsOfDoom.controller.utility.Dice
import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.items.{Item, Key}
import de.htwg.se.DungeonsOfDoom.model.pawns._

import scala.collection.mutable.ListBuffer

object BoardInteraction {

  val player = Player("", 0, 0, 0, 0, 0, 0, 0, 0, 0)
  var board = new Map()
  val enemyList = new ListBuffer[Enemy]()

  def playerCheck(direction: String): Unit = {
    check(player, direction)
  }

  def check(pawn: Pawn, direction: String): Unit = {
    val pos = pawn.currentPosition
    direction match {
      case "Up" => checkAction(pawn, board.map(pos._1)(pos._2 + 1), (pos._1, pos._2 + 1))
      case "Down" => checkAction(pawn, board.map(pos._1)(pos._2 - 1), (pos._1, pos._2 - 1))
      case "Left" => checkAction(pawn, board.map(pos._1 - 1)(pos._2), (pos._1 - 1, pos._2))
      case "Right" => checkAction(pawn, board.map(pos._1 + 1)(pos._2), (pos._1 + 1, pos._2))
    }
  }

  def checkAction(pawn: Pawn, field: Field, newPosition: (Integer, Integer)): Unit = {
    fieldContains(field) match {
      case 0 => Unit
      case 1 => walk(pawn, field.asInstanceOf[Walkable], newPosition)
      case 2 => openDoor(pawn, field.asInstanceOf[Door])
      case 3 => PawnInteraction.attack(player, field.asInstanceOf[Walkable].visitedBy.get, Dice.roll(20), Dice.roll(20))
    }
  }

  def fieldContains(field: Field): Int = {
    //TODO: Replace with Enums
    field match {
      case _: Wall => 0
      case x: Door =>
        if (x.isOpen) {
          1
        } else {
          2
        }
      case x: Walkable =>
        if (x.visitedBy.isEmpty) {
          1
        } else {
          3
        }
    }
  }

  def walk(pawn: Pawn, field: Walkable, newPosition: (Integer, Integer)): Unit = {
    val currentPos = pawn.currentPosition
    val oldField: Walkable = board.map(currentPos._1)(currentPos._2).asInstanceOf[Walkable]
    oldField.visitedBy = None
    field.visitedBy = Some(pawn)
    pawn.currentPosition = newPosition
  }

  def openDoor(pawn: Pawn, door: Door): Unit = {
    if (door.isLocked) {
      pawn.inventory.find((x: Item) => x.isInstanceOf[Key]) match {
        case Some(x) =>
          ItemInteraction.use(pawn, x)
          door.doorState = DoorState.open
        case None => Unit
      }
    }
    else {
      door.doorState = DoorState.open
    }
  }

  def pickup(pawn: Pawn, item: Item): Boolean = {
    val curPos = pawn.currentPosition
    ItemInteraction.pickup(pawn, board.map(curPos._1)(curPos._2), item)
  }

  def drop(pawn: Pawn, item: Item): Boolean = {
    val curPos = pawn.currentPosition
    ItemInteraction.drop(pawn, board.map(curPos._1)(curPos._2), item)
  }

  def setPlayer(player: Player): Unit = {
    board.map(board.playerSpawnPoint._1)(board.playerSpawnPoint._2).asInstanceOf[Walkable].visitedBy = Some(player)
    player.currentPosition = board.playerSpawnPoint
  }

  def spawnEnemy(): Unit = {
    var rand: scala.util.Random = Unit.asInstanceOf[scala.util.Random]
    var x, y = 0
    do {
      rand = new scala.util.Random()
      x = rand.nextInt(board.map.length)
      rand = new scala.util.Random()
      y = rand.nextInt(board.map(0).length)
    } while (!board.map(x)(y).isInstanceOf[Walkable] || board.map(x)(y).asInstanceOf[Walkable].visitedBy.nonEmpty)
    val enemy: Enemy = EnemyFactory.generate((x, y))
    board.map(x)(y).asInstanceOf[Walkable].visitedBy = Some(enemy)
    enemyList += enemy
  }

}
