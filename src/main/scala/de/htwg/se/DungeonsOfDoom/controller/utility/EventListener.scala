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
    parameter match {
      case None => Unit
      case Some("Up") => BoardInteraction check "Up"
      case Some("Down") => BoardInteraction check "Down"
      case Some("Left") => BoardInteraction check "Left"
      case Some("Right") => BoardInteraction check "Right"
    }
  }

  private[this] def use(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      ItemInteraction use parameter.get
    }
  }

  private[this] def drop(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      BoardInteraction drop parameter.get
    }
  }

  private[this] def pickup(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      BoardInteraction pickup parameter.get
    }
  }

  private[this] def unequip(parameter: Option[Any]): Unit = {
    if (parameter.nonEmpty) {
      ItemInteraction unequip parameter.get
    }
  }

  private[this] def undo(parameter: Option[Any]): Unit = {
    TimeManager.undo()
  }
}
