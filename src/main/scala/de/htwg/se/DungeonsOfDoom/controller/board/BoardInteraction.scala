package de.htwg.se.DungeonsOfDoom.controller.board


import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.controller.pawn.PawnInteraction
import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.items.Item
import de.htwg.se.DungeonsOfDoom.model.pawns._
import de.htwg.se.DungeonsOfDoom.model.utility.Dice

import scala.collection.mutable.ListBuffer

object BoardInteraction {

  var player = Player("", 0, 0, 0, 0, 0, 0, 0, 0, 0)
  var board = new Map()
  var enemyList = new ListBuffer[Enemy]()

  def reset(player: Player = Player("", 0, 0, 0, 0, 0, 0, 0, 0, 0)) : Unit = {
    board = new Map()
    setPlayer(player)
    enemyList = new ListBuffer[Enemy]()
  }

  def checkPlayer(direction: String): Option[Int] = {
    check(player, direction)
  }

  def check(pawn: Pawn, direction: String): Option[Int] = {
    val pos = pawn.currentPosition
    direction match {
      case "Up" => checkAction(pawn, board.map(pos._1)(pos._2 - 1), (pos._1, pos._2 - 1))
      case "Down" => checkAction(pawn, board.map(pos._1)(pos._2 + 1), (pos._1, pos._2 + 1))
      case "Left" => checkAction(pawn, board.map(pos._1 - 1)(pos._2), (pos._1 - 1, pos._2))
      case "Right" => checkAction(pawn, board.map(pos._1 + 1)(pos._2), (pos._1 + 1, pos._2))
    }
  }

  def checkAction(pawn: Pawn, field: Field, newPosition: (Int, Int)): Option[Int] = {
    fieldContains(field) match {
      case 0 => None
      case 1 => walk(pawn, field.asInstanceOf[Walkable], newPosition)
        None
      case 2 => openDoor(pawn, field.asInstanceOf[Door])
        None
      case 3 => Some(PawnInteraction.attack(player, field.asInstanceOf[Walkable].visitedBy.get, Dice.roll(20), Dice.roll(20)))
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
        x.visitedBy match {
          case None => 1
          case Some(_) => 3
        }
    }
  }

  def walk(pawn: Pawn, field: Walkable, newPosition: (Int, Int)): Unit = {
    val currentPos = pawn.currentPosition
    val oldField: Walkable = board.map(currentPos._1)(currentPos._2).asInstanceOf[Walkable]
    oldField.visitedBy = None
    field.visitedBy = Some(pawn)
    pawn.currentPosition = newPosition
  }

  def openDoor(pawn: Pawn, door: Door): Unit = {
    if (door.isLocked) {
      val list = pawn.inventory.filter(x => x.name == "Key")
      if (list.nonEmpty) {
        pawn.inventory -= list.head
        door.doorState = DoorState.open
      }
    }
    else {
      door.doorState = DoorState.open
    }
  }

  def pickup(pawn: Pawn): Boolean = {
    val curPos = pawn.currentPosition
    val field = board.map(curPos._1)(curPos._2).asInstanceOf[Floor]
    var result = true
    for (item <- field.inventory) {
      result = ItemInteraction.pickup(pawn, field, item)
    }
    result
  }

  def drop(pawn: Pawn, item: Item): Boolean = {
    val curPos = pawn.currentPosition
    ItemInteraction.drop(pawn, board.map(curPos._1)(curPos._2).asInstanceOf[Floor], item)
  }

  def setPlayer(player: Player): Unit = {
    this.player = player
    board.map(board.playerSpawnPoint._1)(board.playerSpawnPoint._2).asInstanceOf[Walkable].visitedBy = Some(player)
    player.currentPosition = board.playerSpawnPoint
  }

  def spawnEnemy(): Unit = {
    var rand: scala.util.Random = new scala.util.Random()
    var x, y = 0
    var counter = 0
    do {
      rand = new scala.util.Random()
      x = rand.nextInt(board.map.length - 2) + 1
      rand = new scala.util.Random()
      y = rand.nextInt(board.map(0).length - 2) + 1
      counter += 1
    } while ((!isFree(board.map(x)(y))) && counter <= 50)
    if (counter <= 50) {
      val enemy: Enemy = EnemyFactory.generate((x, y))
      board.map(x)(y).asInstanceOf[Walkable].visitedBy = Some(enemy)
      enemyList += enemy
    }
  }

  def isFree(field: Field): Boolean = {
    if (field.isInstanceOf[Wall]) {
      false
    }
    else {
      field.asInstanceOf[Walkable].visitedBy match {
        case None => true
        case Some(_) => false
      }
    }
  }

  def hasLoot(field: Field): Boolean = {
    if (!field.isInstanceOf[Floor]) {
      false
    }
    else {
      field.asInstanceOf[Floor].inventory.nonEmpty
    }
  }

  def isDoorOpen(field: Field): Boolean = {
    if (!field.isInstanceOf[Door]) {
      false
    }
    else {
      field.asInstanceOf[Door].isOpen
    }
  }

  def hasEnemy(field: Field): Option[Char] = {
    if (!field.isInstanceOf[Walkable]) {
      None
    }
    else {
      field.asInstanceOf[Walkable].visitedBy match {
        case None => None
        case Some(x) => Some(x.name.head)
      }
    }
  }

}
