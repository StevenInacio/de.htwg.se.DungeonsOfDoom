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
      }
      "have a nice String representation" in {
        player.toString should be("Your Name")
      }

    }
  }
}
