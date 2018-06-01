package de.htwg.se.DungeonsOfDoom.model.items

case class Weapon (name : String,
                   durability : Integer,
                   weight : Integer,
                   value : Integer,
                   damage : Integer,
                   minStrength : Integer
                  ) extends Item with Equipable{
  def lowerDurability(amount : Integer): Weapon = {
    copy(durability = durability - amount)
  }
  def repair(amount : Integer): Weapon = {
    copy(durability = durability + amount)
  }
}
