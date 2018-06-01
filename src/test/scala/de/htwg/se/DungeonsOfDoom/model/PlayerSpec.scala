package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.pawns.Player
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", 0, 0, 0, 0, 0, 0, 0, 0, 0)
      "have a name" in {
        player.name should be("Your Name")
        player.body should be(0)
        player.strength should be(0)
        player.hardness should be(0)
        player.agility should be(0)
        player.mobility should be(0)
        player.dexterity should be(0)
        player.spirit should be(0)
        player.mind should be(0)
        player.aura should be(0)
        player.currentHealth should be(10)
      }
      "have a nice String representation" in {
        player.toString should be("Your Name")
      }
      "be able to equip items" in {
      //TODO: test equip
      }
      "be able to unequip items" in {
      //TODO: test unequip
      }
      "get damaged, but should not die" in {
        player.changeHealth(-5)
        player.currentHealth should be(5)
        player.isDead should be(false)
      }
      "get healed, but not overhealed" in {
        player.changeHealth(7)
        player.currentHealth should be(10)
      }
      "be dead at overkill" in {
        player.changeHealth(-50)
        player.currentHealth should be(0)
        player.isDead should be(true)
      }
    }
  }
}
