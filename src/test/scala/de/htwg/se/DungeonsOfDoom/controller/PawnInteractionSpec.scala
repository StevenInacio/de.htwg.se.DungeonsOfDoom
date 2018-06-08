package de.htwg.se.DungeonsOfDoom.controller


import de.htwg.se.DungeonsOfDoom.model.items.Weapon
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import de.htwg.se.DungeonsOfDoom.controller.pawn.PawnInteraction


@RunWith(classOf[JUnitRunner])
class PawnInteractionSpec extends WordSpec with Matchers {
  val player = Player("Spike", 1, 1, 1, 1, 1, 1, 1, 1, 1) //currentHealth = 12, hit = 2
  val enemy = Enemy("Evil", 1, 1, 1, 1, 1, 1, 1, 1, 1) //currentHealth = 12, hit = 2

  "A Player" when {
    "attacking an Enemy" should {
      "damage him" in {
        PawnInteraction.attack(player, enemy, 2, 1)
        enemy.currentHealth should be(11)
      }
    }
  }
}
