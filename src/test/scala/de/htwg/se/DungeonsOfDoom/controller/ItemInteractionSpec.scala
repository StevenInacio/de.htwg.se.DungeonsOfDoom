package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.model.items.{HealingPotion, Weapon}
import de.htwg.se.DungeonsOfDoom.model.pawns.Player
import de.htwg.se.DungeonsOfDoom.model.board.Floor
import de.htwg.se.DungeonsOfDoom.controller.item.ItemInteraction
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ItemInteractionSpec extends WordSpec with Matchers {
  val weapon = Weapon("Wooden Stick Of Death", 5, 5, 1, 0, 2, 1)
  val potion = HealingPotion("Magical Cheesecake Of Resurrection", 3, 10, 5, 3)
  val player = Player("xX1nicerDudeXx", 4, 4, 4, 4, 4, 4, 4, 4, 4)
  val floor = Floor()
  floor.putOnFloor(weapon)

  "A Player" when {
    "picking up an item" should {
      "should be able to do so" in {
        ItemInteraction.pickup(player, floor, weapon) should be(true)
        player.inventory.contains(weapon) should be(true)
        floor.inventory.contains(weapon) should be(false)
      }
      "not be able to pick it up twice" in {
        ItemInteraction.pickup(player, floor, weapon) should be(false)
      }
    }
  }
}