package de.htwg.se.DungeonsOfDoom.model.board

object DoorState extends Enumeration {
  type State = Value
  val locked, closed, open = Value
}
