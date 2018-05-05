package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Item}

import scala.collection.mutable.ListBuffer

trait Pawn {
  val name : String
  var experience : Integer = 0
  var body : Integer
  var strength : Integer
  var hardness : Integer
  var agility : Integer
  var mobility : Integer
  var dexterity : Integer
  var spirit : Integer
  var mind : Integer
  var aura : Integer
  var armor_value : Integer = 0
  var melee_bonus : Integer = 0
  var ranged_bonus : Integer = 0
  var inventory : ListBuffer[Item] = new ListBuffer[Item]()
  var equipped : ListBuffer[Equipable] = new ListBuffer[Equipable]()
  def health : Integer = body + hardness + 10
  def defense : Integer = body + hardness + armor_value
  def initiative : Integer = agility + mobility
  def hit : Integer = body + strength + melee_bonus
  /*def shoot : Integer = agility + dexterity + ranged_bonus*/
  /*def cast_spell : Integer = spirit + aura - armor_value*/
  /*def sling_spell : Integer = spirit + dexterity - armor_value*/
}