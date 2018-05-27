package de.htwg.se.DungeonsOfDoom.model.items

case class Sword (name : String,
                  weight : Integer,
                  value : Integer,
                  durability : Integer,
                 ) extends Item with Equipable {
  val attackBonus : Integer = 1
}
