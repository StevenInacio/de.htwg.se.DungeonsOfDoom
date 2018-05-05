package de.htwg.se.DungeonsOfDoom.model.pawns

case class Enemy(name: String) extends Pawn {
  override def toString: String = name
  override var body: Integer = _
  override var strength: Integer = _
  override var hardness: Integer = _
  override var agility: Integer = _
  override var mobility: Integer = _
  override var dexterity: Integer = _
  override var spirit: Integer = _
  override var mind: Integer = _
  override var aura: Integer = _
}
