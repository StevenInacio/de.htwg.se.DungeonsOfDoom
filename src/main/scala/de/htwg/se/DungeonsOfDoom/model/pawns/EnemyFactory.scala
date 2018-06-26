package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.Weapon

object EnemyFactory {
  def generate(currentPosition: (Integer, Integer)): Enemy = {
    val random = new scala.util.Random()
    random.nextInt(2) match {
      case 0 => lesserDemon(currentPosition)
      case 1 => eagle(currentPosition)
    }
  }

  def lesserDemon(pos: (Integer, Integer)): Enemy = Enemy("Lesser Demon", 5, 2, 2, 5, 2, 2, 5, 2, 2, Array(Weapon("Claw", 1, 1, 1, 1, 1, 0)), pos)

  def eagle(pos: (Integer, Integer)): Enemy = Enemy("Eagle", 3, 1, 0, 8, 3, 1, 1, 0, 1, Array(Weapon("Claw", 1, 1, 1, 1, 1, 0)), pos)
}


