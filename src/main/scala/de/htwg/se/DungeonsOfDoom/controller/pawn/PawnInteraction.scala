package de.htwg.se.DungeonsOfDoom.controller.pawn

import de.htwg.se.DungeonsOfDoom.controller.utility.Dice
import de.htwg.se.DungeonsOfDoom.model.pawns.Pawn

object PawnInteraction {
  def attack(attacker: Pawn, target: Pawn): Unit = {
    var result = Dice.roll(20)
    if (result < attacker.hit && result < 20) {
      val defend = Dice.roll(20)
      if (defend <= target.defense && defend < 20) {
        result -= defend
      }
      if (result < 0) {
        result = 0
      }
      target.changeHealth(-result)
    }
  }

  /*def heal(healer: Pawn, target: Pawn): Unit = {
    var result = Dice.roll(20)
    if (result < healer.castSpell && result < 20){
      target.changeHealth(result)
    }
  }*/
}
