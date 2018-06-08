package de.htwg.se.DungeonsOfDoom.controller.item

import de.htwg.se.DungeonsOfDoom.model.board.Floor
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.Pawn

object ItemInteraction {
  def use(pawn: Pawn, item: Item): Unit = {
    if(pawn.inventory.contains(item)){
      item match {
        case x: Consumable => consume(pawn, x)
        case x: Equipable => equip(pawn, x)
      }
    }
  }
  def consume(pawn: Pawn, item: Consumable): Unit = {
    if(item.usage > 1) {
      item.usage -= 1
    } else {
      pawn.inventory -= item
    }
    item match {
      case x: HealingPotion => {
        pawn.changeHealth(x.healthBonus)
      }
    }
  }
  def equip(pawn: Pawn, item: Equipable): Unit = {
    pawn.inventory -= item
    item match {
      case x: Weapon => {
        pawn.melee_bonus += x.damage
        pawn.equipped += x
      }
    }
  }
  def unequip(pawn: Pawn, item: Equipable): Unit = {
    pawn.inventory += item
    item match {
      case x: Weapon => {
        pawn.melee_bonus -= x.damage
        pawn.equipped -= x
      }
    }
  }
  def pickup(pawn: Pawn, floor: Floor, item: Item): Unit = {
    if(floor.inventory.contains(item)){
      pawn.inventory += item
      floor.inventory -= item
    }
  }
  def drop(pawn: Pawn, floor: Floor, item: Item): Unit = {
    if(pawn.inventory.contains(item)){
      pawn.inventory -= item
      floor.inventory += item
    }
  }
}
