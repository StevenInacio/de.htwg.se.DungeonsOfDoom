package de.htwg.se.DungeonsOfDoom.model.pawns

case class DeadPlayer(name: String) extends Spielfigur with Dead {
  override def toString: String = name
}