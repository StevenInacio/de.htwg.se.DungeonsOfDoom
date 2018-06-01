package de.htwg.se.DungeonsOfDoom.model.items

/** Items that are equipable.
  *
  * Items which can be equipped, like weapons or clothes.
  */
trait Equipable extends Item{
  var durability : Integer
  val maxDurability : Integer
  def isBroken : Boolean = durability == 0

  def lowerDurability(amount : Integer): Unit = {
    durability = durability - amount
    if(durability < 0) durability = 0
  }
  def repair(amount : Integer): Unit = {
    durability = durability + amount
    if(durability > maxDurability) durability = maxDurability
  }
}
