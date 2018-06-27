package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction

object EventListener {
  def deployEvent(event: String, parameter: Option[Any] = None): Unit = {
    event match {
      case "PlayerWalk" => walk(parameter)
      case "UseItem" => use(parameter)
      case "DropItem" => drop(parameter)
      case "PickUpItem" => pickup(parameter)
      case "UnequipItem" => unequip(parameter)
      case "Undo" => undo(parameter)
    }
  }

  private[this] def walk(parameter: Option[Any]): Unit = {
    TimeManager.saveState(TimeManager.getState())
    parameter match {
      case Some("Up") => BoardInteraction checkPlayer "Up"
      case Some("Down") => BoardInteraction checkPlayer "Down"
      case Some("Left") => BoardInteraction checkPlayer "Left"
      case Some("Right") => BoardInteraction checkPlayer "Right"
      case _ => Unit
    }
    TimeManager.advanceTime()
  }

  private[this] def use(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = TimeManager.getState()
      if (ItemInteraction use parameter.get) {
        TimeManager.saveState(tmp)
        TimeManager.advanceTime()
      }
    }
  }

  private[this] def drop(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = TimeManager.getState()
      if (BoardInteraction drop parameter.get) {
        TimeManager.saveState(tmp)
        TimeManager.advanceTime()
      }
    }
  }

  private[this] def pickup(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = TimeManager.getState()
      if (BoardInteraction pickup parameter.get) {
        TimeManager.saveState(tmp)
        TimeManager.advanceTime()
      }
    }
  }

  private[this] def unequip(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      val tmp = TimeManager.getState()
      if (ItemInteraction unequip parameter.get) {
        TimeManager.saveState(tmp)
        TimeManager.advanceTime()
      }
    }
  }

  private[this] def undo(parameter: Option[Any]): Unit = {
    TimeManager.undo()
  }
}
