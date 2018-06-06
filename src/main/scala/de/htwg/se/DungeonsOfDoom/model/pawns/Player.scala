package de.htwg.se.DungeonsOfDoom.model.pawns

case class Player(name: String,
                  var body: Integer,
                  var strength: Integer,
                  var hardness: Integer,
                  var agility: Integer,
                  var mobility: Integer,
                  var dexterity: Integer,
                  var spirit: Integer,
                  var mind: Integer,
                  var aura: Integer,
                  var currentPosition: (Integer, Integer) = (0, 0)
                 ) extends Pawn {

  override def toString: String = name
}