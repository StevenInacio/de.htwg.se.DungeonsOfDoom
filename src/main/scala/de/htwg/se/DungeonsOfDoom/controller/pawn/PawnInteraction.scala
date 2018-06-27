package de.htwg.se.DungeonsOfDoom.controller.pawn

import de.htwg.se.DungeonsOfDoom.model.pawns.Pawn

object PawnInteraction {
  def attack(attacker: Pawn, target: Pawn, attackerDice: Int, targetDice: Int): Unit = {
    var result = attackerDice
    if (result <= attacker.hit && result < 20) {
      if (targetDice <= target.defense && targetDice < 20) {
        result -= targetDice
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
