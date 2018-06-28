package de.htwg.se.DungeonsOfDoom.model.items

/** Items in general.
  *
  * Items that can be found.
  * Like potions, keys, weapons etc.
  * Also, equipables or consumables.
  */
trait Item {
  val name: String
  val weight: Int
  val value: Int
}
