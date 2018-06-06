package de.htwg.se.DungeonsOfDoom.model.board

import de.htwg.se.DungeonsOfDoom.model.pawns.Pawn

trait Walkable {
  var visitedBy: Option[Pawn] = None
}
