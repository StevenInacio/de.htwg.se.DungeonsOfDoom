package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion (name : String,
                          usage : Integer,
                          weight : Integer,
                          value : Integer,
                          val healthBonus : Integer
                         ) extends Item with Consumable {
}
