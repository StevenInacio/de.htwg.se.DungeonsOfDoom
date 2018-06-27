package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Item}

import scala.collection.mutable.ListBuffer

trait Pawn {
  var currentPosition: (Int, Int)
  val name: String
  var experience: Int = 0
  var body: Int
  var strength: Int
  var hardness: Int
  var agility: Int
  var mobility: Int
  var dexterity: Int
  var spirit: Int
  var mind: Int
  var aura: Int
  var currentHealth: Int = health
  var armor_value: Int = 0
  var melee_bonus: Int = 0
  var ranged_bonus: Int = 0
  var inventory: ListBuffer[Item] = new ListBuffer[Item]()
  var equipped: ListBuffer[Equipable] = new ListBuffer[Equipable]()

  def defense: Int = body + hardness + armor_value

  def initiative: Int = agility + mobility

  def hit: Int = body + strength + melee_bonus

  /*def shoot : Int = agility + dexterity + ranged_bonus*/
  /*def cast_spell : Int = spirit + aura - armor_value*/
  /*def sling_spell : Int = spirit + dexterity - armor_value*/
  /*def equip(equipable: Equipable): Unit = {
    // Evaluate Type
    // Evaluate if equip slot is free
    // Increase armor or attack bonuses
    // Increase attribute bonuses if needed
    // remove equipable from inventory
    // add it to equipped list
  }

  def unequip(equipable: Equipable): Boolean = {
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
  }*/

  def changeHealth(amount: Int): Unit = {
    currentHealth += amount
    if (currentHealth > health) currentHealth = health
    if (currentHealth < 0) currentHealth = 0 //he ded, son
  }

  def health: Int = body + hardness + 10

  def isDead: Boolean = currentHealth == 0
}
