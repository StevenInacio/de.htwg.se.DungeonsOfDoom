package de.htwg.se.DungeonsOfDoom.model.items

class Key extends Item with Consumable {
  override val name: String = "Key"
  override val weight: Int = 0
  override val value: Int = 0
  override var usage: Int = 1
}
