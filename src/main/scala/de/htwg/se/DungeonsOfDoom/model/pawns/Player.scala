package de.htwg.se.DungeonsOfDoom.model.pawns

case class Player(name: String) extends Spielfigur {
   override def toString:String = name
   
}