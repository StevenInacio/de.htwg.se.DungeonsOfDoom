package de.htwg.se.DungeonsOfDoom.controller.utility

import java.util.Observable

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, DoorState, Floor, Walkable}
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer

class EventListener(val stateManager: StateManager) extends Observable {

  def deployEvent(event: String, parameter: Option[Any] = None, timeShouldAdvance: Boolean = true): Option[Int] = {
    var result: Option[Int] = None
    event match {
      case "CreatePlayer" => createNewPlayer()
      case "PlayerWalk" => result = walk(parameter, timeShouldAdvance)
      case "UseItem" => use(parameter, timeShouldAdvance)
      case "DropItem" => drop(parameter, timeShouldAdvance)
      case "PickUpItem" => pickup(timeShouldAdvance)
      case "UnequipItem" => unequip(parameter, timeShouldAdvance)
      case "Undo" => undo()
      case "Load" => undo()
      case "Exit" => stateManager.saveState(stateManager.getState)
    }
    if(countObservers() != 0) {
      setChanged()
      notifyObservers()
    }
    result
  }

  private[this] def createNewPlayer(): Unit = {
    val player = Player("Herbert", 5, 5, 5, 5, 5, 5, 5, 5, 5)
    val weapon = Weapon("Daggerbert Stab", 1, 1, 1, 1, 1, 1)
    BoardInteraction.reset(player)
    BoardInteraction.player.inventory += weapon
    ItemInteraction.use(BoardInteraction.player, weapon)
    BoardInteraction.board.map(1)(2).asInstanceOf[Floor].inventory ++= ListBuffer(new Key(), HealingPotion("Small Healing Potion", 1, 1, 1, 5))
    BoardInteraction.board.map(2)(3) = Door(DoorState.locked)
    val enemy = Enemy("Günther", 5, 2, 2, 5, 2, 2, 5, 2, 2, Array(Weapon("Günther-Faust", 1, 1, 1, 1, 1, 1)), (2, 2))
    BoardInteraction.board.map(2)(2).asInstanceOf[Walkable].visitedBy = Some(enemy)
    BoardInteraction.enemyList.+=(enemy)
  }

  private[this] def walk(parameter: Option[Any], timeShouldAdvance: Boolean): Option[Int] = {
    var result: Option[Int] = None
    stateManager.saveState(stateManager.getState)
    result = parameter match {
      case Some("Up") => BoardInteraction checkPlayer "Up"
      case Some("Down") => BoardInteraction checkPlayer "Down"
      case Some("Left") => BoardInteraction checkPlayer "Left"
      case Some("Right") => BoardInteraction checkPlayer "Right"
      case _ => None
    }
    if(timeShouldAdvance) {
      TimeManager.advanceTime()
    }
    result
  }

  private[this] def use(parameter: Option[Any], timeShouldAdvance: Boolean): Unit = {
    if (parameter.nonEmpty) {
      val tmp = stateManager.getState
      if (ItemInteraction.use(BoardInteraction.player, parameter.get.asInstanceOf[Item])) {
        stateManager.saveState(tmp)
        if (timeShouldAdvance) {
          TimeManager.advanceTime()
        }
      }
    }
  }

  private[this] def drop(parameter: Option[Any], timeShouldAdvance: Boolean): Unit = {
    if (parameter.nonEmpty) {
      val tmp = stateManager.getState
      if (BoardInteraction.drop(BoardInteraction.player, parameter.get.asInstanceOf[Item])) {
        stateManager.saveState(tmp)
        if (timeShouldAdvance) {
          TimeManager.advanceTime()
        }
      }
    }
  }

  private[this] def pickup(timeShouldAdvance: Boolean): Unit = {
    val tmp = stateManager.getState
    if (BoardInteraction.pickup(BoardInteraction.player)) {
      stateManager.saveState(tmp)
      if (timeShouldAdvance) {
        TimeManager.advanceTime()
      }
    }
  }

  private[this] def unequip(parameter: Option[Any], timeShouldAdvance: Boolean): Unit = {
    if (parameter.nonEmpty) {
      val tmp = stateManager.getState
      if (ItemInteraction.unequip(BoardInteraction.player, parameter.get.asInstanceOf[Equipable])) {
        stateManager.saveState(tmp)
        if(timeShouldAdvance) {
          TimeManager.advanceTime()
        }
      }
    }
  }

  private[this] def undo(): Unit = {
    stateManager.undo()
  }
}
