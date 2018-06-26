package de.htwg.se.DungeonsOfDoom.model.items

class Key extends Item with Consumable {
  override val name: String = "Key"
  override val weight: Integer = 0
  override val value: Integer = 0
  override var usage: Integer = 1
}
