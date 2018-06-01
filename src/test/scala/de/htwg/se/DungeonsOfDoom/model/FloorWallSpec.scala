package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.board.{Floor, Wall}
import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Item, Weapon}
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class FloorWallSpec extends WordSpec with Matchers {
  "A Floor" when {
    "new" should {
      val floor = Floor()
      val weapon = Weapon("Sword", 20, 20, 5, 100, 3, 0)
      val filledInventory = new ListBuffer[Item]()
      filledInventory += weapon
      "have an empty inventory" in {
        floor.inventory.isEmpty should be(true)
      }
      "be able to hold an item" in {
        floor.putOnFloor(weapon)
        floor.inventory should be(filledInventory)
      }
      "be able to let go of the items it holds" in {
        floor.pickUpFromFloor(0) should be(weapon)
        floor.inventory.isEmpty should be(true)
      }
      "should be able to hold Players and Enemies" in {
        val player = Player("Bobo", 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val enemy = Enemy("Hektor", 0, 0, 0, 0, 0, 0, 0, 0, 0, new Array[Equipable](0))
        floor.visitedBy should be(None)
        floor.visitedBy = Some(player)
        floor.visitedBy.get should be(player)
        floor.visitedBy = Some(enemy)
        floor.visitedBy.get should be(enemy)
      }
    }
  }
  "A Wall" when {
    "new" should {
      val wall = Wall()
      "exist" in {
        wall.getClass.getSimpleName should be("Wall")
      }
    }
  }

}
