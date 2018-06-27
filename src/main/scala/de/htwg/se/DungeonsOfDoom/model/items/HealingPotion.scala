package de.htwg.se.DungeonsOfDoom.model.items

case class HealingPotion(name: String,
                         weight: Int,
                         value: Int,
                         override var usage: Int = 1,
                         healthBonus: Int
                        ) extends Item with Consumable
