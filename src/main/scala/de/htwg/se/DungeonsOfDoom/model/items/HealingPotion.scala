package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion (name : String,
                          weight : Integer,
                          value : Integer,
                          var usage : Integer,
                          healthBonus : Integer
                         ) extends Item with Consumable
