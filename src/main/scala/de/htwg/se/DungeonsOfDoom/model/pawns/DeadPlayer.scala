package de.htwg.se.DungeonsOfDoom.model.pawns

case class DeadPlayer(player: Player) extends Spielfigur with Dead {
    override def toString: String = name
}