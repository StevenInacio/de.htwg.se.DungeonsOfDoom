package de.htwg.se.DungeonsOfDoom.model.items

case class Weapon(name: String,
                  var durability: Int,
                  maxDurability: Int,
                  weight: Int,
                  value: Int,
                  damage: Int,
                  minStrength: Int
                 ) extends Item with Equipable {

}
