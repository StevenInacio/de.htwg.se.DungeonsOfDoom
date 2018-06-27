package de.htwg.se.DungeonsOfDoom.model.pawns

case class Player(name: String,
                  var body: Int,
                  var strength: Int,
                  var hardness: Int,
                  var agility: Int,
                  var mobility: Int,
                  var dexterity: Int,
                  var spirit: Int,
                  var mind: Int,
                  var aura: Int,
                  var currentPosition: (Int, Int) = (0, 0)
                 ) extends Pawn {

  override def toString: String = name
}