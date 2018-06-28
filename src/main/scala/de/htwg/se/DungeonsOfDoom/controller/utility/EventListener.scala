package de.htwg.se.DungeonsOfDoom.controller.utility

import java.util.Observable

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Item}

class EventListener(val timeManager: TimeManager) extends Observable {

  def deployEvent(event: String, parameter: Option[Any] = None): Unit = {
    event match {
      case "PlayerWalk" => walk(parameter)
      case "UseItem" => use(parameter)
      case "DropItem" => drop(parameter)
      case "PickUpItem" => pickup(parameter)
      case "UnequipItem" => unequip(parameter)
      case "Undo" => undo(parameter)
      case "Exit" => timeManager.saveState(timeManager.getState)
    }
    notifyAll()
  }

  private[this] def walk(parameter: Option[Any]): Unit = {
    timeManager.saveState(timeManager.getState)
    parameter match {
      case Some("Up") => BoardInteraction checkPlayer "Up"
      case Some("Down") => BoardInteraction checkPlayer "Down"
      case Some("Left") => BoardInteraction checkPlayer "Left"
      case Some("Right") => BoardInteraction checkPlayer "Right"
      case _ => Unit
    }
    timeManager.advanceTime()
  }

  private[this] def use(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = timeManager.getState
      if (ItemInteraction.use(BoardInteraction.player, parameter.get.asInstanceOf[Item])) {
        timeManager.saveState(tmp)
        timeManager.advanceTime()
      }
    }
  }

  private[this] def drop(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = timeManager.getState
      if (BoardInteraction.drop(BoardInteraction.player, parameter.get.asInstanceOf[Item])) {
        timeManager.saveState(tmp)
        timeManager.advanceTime()
      }
    }
  }

  private[this] def pickup(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = timeManager.getState
      if (BoardInteraction.pickup(BoardInteraction.player, parameter.get.asInstanceOf[Item])) {
        timeManager.saveState(tmp)
        timeManager.advanceTime()
      }
    }
  }

  private[this] def unequip(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = timeManager.getState
      if (ItemInteraction.unequip(BoardInteraction.player, parameter.get.asInstanceOf[Equipable])) {
        timeManager.saveState(tmp)
        timeManager.advanceTime()
      }
    }
  }

  private[this] def undo(parameter: Option[Any]): Unit = {
    timeManager.undo()
  }
}
