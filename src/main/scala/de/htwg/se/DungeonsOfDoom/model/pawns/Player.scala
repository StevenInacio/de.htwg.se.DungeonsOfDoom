package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.Equipable

case class Player(name: String, var body: Integer, var strength: Integer,
                  var hardness: Integer, var agility: Integer, var mobility: Integer,
                  var dexterity: Integer, var spirit: Integer, var mind: Integer,
                  var aura: Integer) extends Pawn {
  override def toString: String = name
  def equip(equipable: Equipable) : Unit = {
    // Evaluate Type
    // Evaluate if equip slot is free
    // Increase armor or attack bonuses
    // Increase attribute bonuses if needed
    // remove equipable from inventory
    // add it to equipped list
  }
  def unequip(equipable: Equipable) : Boolean = {
    // See if equipped list contains equipable
    if (!equipped.contains(equipable)) {
      false
    }
    else {
      // decrease armor or attack bonuses
      // if equipable is armor {
        // armor_value -= equipable.armor_value
      // decrease attribute bonuses if necessary
      // remove equipable from equipped list
      // add it to inventory
      true
    }
  }
}