package de.htwg.se.DungeonsOfDoom.model.items

case class Weapon (name : String,
                   durability : Integer,
                   maxDurability : Integer,
                   weight : Integer,
                   value : Integer,
                   damage : Integer,
                   minStrength : Integer
                  ) extends Item with Equipable{

}
