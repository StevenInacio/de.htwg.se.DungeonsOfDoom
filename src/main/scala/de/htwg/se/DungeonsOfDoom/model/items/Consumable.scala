package de.htwg.se.DungeonsOfDoom.model.items

/** Items which can't be equipped.
  *
  * For example, potions, keys, etc.
  */
trait Consumable extends Item{
  var usage : Integer //how often can I use this item?
}
