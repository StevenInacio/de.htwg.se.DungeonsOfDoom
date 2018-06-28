package de.htwg.se.DungeonsOfDoom.controller.utility

import java.util.Observable

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Item}

class EventListener(val stateManager: StateManager) extends Observable {

  def deployEvent(event: String, parameter: Option[Any] = None, timeShouldAdvance: Boolean): Unit = {
    event match {
      case "PlayerWalk" => walk(parameter, timeShouldAdvance)
      case "UseItem" => use(parameter, timeShouldAdvance)
      case "DropItem" => drop(parameter, timeShouldAdvance)
      case "PickUpItem" => pickup(timeShouldAdvance)
      case "UnequipItem" => unequip(parameter, timeShouldAdvance)
      case "Undo" => undo()
      case "Exit" => stateManager.saveState(stateManager.getState)
    }
    if(countObservers() != 0) {
      notifyObservers()
    }
  }

  private[this] def walk(parameter: Option[Any], timeShouldAdvance: Boolean): Unit = {
    stateManager.saveState(stateManager.getState)
    parameter match {
      case Some("Up") => BoardInteraction checkPlayer "Up"
      case Some("Down") => BoardInteraction checkPlayer "Down"
      case Some("Left") => BoardInteraction checkPlayer "Left"
      case Some("Right") => BoardInteraction checkPlayer "Right"
      case _ => Unit
    }
    if(timeShouldAdvance) {
      TimeManager.advanceTime()
    }
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
