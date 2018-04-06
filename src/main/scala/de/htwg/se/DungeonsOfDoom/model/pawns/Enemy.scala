package de.htwg.se.DungeonsOfDoom.model.pawns

case class Enemy(name: String) extends Spielfigur {
  override def toString: String = name
}
