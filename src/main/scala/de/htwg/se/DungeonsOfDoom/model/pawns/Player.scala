package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.Equipable

case class Player(name: String, var body: Integer, var strength: Integer,
                  var hardness: Integer, var agility: Integer, var mobility: Integer,
                  var dexterity: Integer, var spirit: Integer, var mind: Integer,
                  var aura: Integer) extends Pawn {
  override def toString: String = name
}