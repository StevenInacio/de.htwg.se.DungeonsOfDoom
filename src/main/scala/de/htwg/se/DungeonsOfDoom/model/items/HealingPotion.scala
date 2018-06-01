package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion (name : String,
                          weight : Integer,
                          value : Integer,
                          usage : Integer,
                          val healthBonus : Integer
                         ) extends Item with Consumable {
  //add random value between 1 and healthBonus to player health
  //this.health += random(1, healthBonus) access player health??
}
