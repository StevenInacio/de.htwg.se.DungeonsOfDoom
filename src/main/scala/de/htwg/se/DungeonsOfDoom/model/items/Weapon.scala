package de.htwg.se.DungeonsOfDoom.model.items

case class Weapon (name : String,
                   durability : Integer,
                   weight : Integer,
                   value : Integer,
                   damage : Integer,
                   minStrength : Integer
                  ) extends Item with Equipable{
}
