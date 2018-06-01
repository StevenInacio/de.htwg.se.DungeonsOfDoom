package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion (name : String,
                          weight : Integer,
                          value : Integer,
                          override var usage : Integer = 1,
                          healthBonus : Integer
                         ) extends Item with Consumable
