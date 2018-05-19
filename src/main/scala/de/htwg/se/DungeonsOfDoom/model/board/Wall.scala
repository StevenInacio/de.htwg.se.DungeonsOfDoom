package de.htwg.se.DungeonsOfDoom.model.board

class Wall(direction: String) extends Field {
  override def toString: String = {
    direction match {
      case "horizontal" => "-"
      case "vertical" => "|"
      case "edge" => "+"
      case _ => "+"
    }
  }
}
