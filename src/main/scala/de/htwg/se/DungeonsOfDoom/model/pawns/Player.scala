package de.htwg.se.DungeonsOfDoom.model.pawns

case class Player(name: String) extends Spielfigur {
   override def toString:String = name
}

case class DeadPlayer(name: String) extends Spielfigur with Dead {
   override def toString: String = name
}