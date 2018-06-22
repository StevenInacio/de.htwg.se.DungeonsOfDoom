package de.htwg.se.DungeonsOfDoom.controller.item

import de.htwg.se.DungeonsOfDoom.model.board.Floor
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.Pawn

object ItemInteraction {
  def use(pawn: Pawn, item: Item): Boolean = {
    if(pawn.inventory.contains(item)){
      item match {
        case x: Consumable => consume(pawn, x)
        case x: Equipable => equip(pawn, x)
      }
      true
    } else {false}
  }
  private[this] def consume(pawn: Pawn, item: Consumable): Unit = {
    if(item.usage > 1) {
      item.usage -= 1
    } else {
      pawn.inventory -= item
    }
    item match {
      case x: HealingPotion => pawn.changeHealth(x.healthBonus)
    }
  }

  private[this] def equip(pawn: Pawn, item: Equipable): Unit = {
    pawn.inventory -= item
    item match {
      case x: Weapon => if (pawn.strength >= x.minStrength) {
        pawn.melee_bonus += x.damage
        pawn.equipped += x
      }
    }
  }
  def unequip(pawn: Pawn, item: Equipable): Boolean = {
    if(pawn.equipped.contains(item)){
      pawn.inventory += item
      item match {
        case x: Weapon => pawn.melee_bonus -= x.damage
          pawn.equipped -= x
      }
      true
    } else {false}
  }
  def pickup(pawn: Pawn, floor: Floor, item: Item): Boolean = {
    if(floor.inventory.contains(item)){
      pawn.inventory += item
      floor.inventory -= item
      true
    } else {false}
  }
  def drop(pawn: Pawn, floor: Floor, item: Item): Boolean = {
    if(pawn.inventory.contains(item)){
      pawn.inventory -= item
      floor.inventory += item
      true
    } else {false}
  }
}
