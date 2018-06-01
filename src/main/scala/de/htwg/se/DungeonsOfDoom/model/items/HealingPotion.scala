package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion (name : String,
                          weight : Integer,
                          value : Integer,
                          var usage : Integer,
                          healthBonus : Integer
                         ) extends Item with Consumable {
  //add random value between 1 and healthBonus to player health
  // Do this in the controller layer
  // player changeHealth random(1,HealingPotion.healthBonus)

  // Event Listener ist in Controller
  // View schickt Events an EventListener (Controller)
}
