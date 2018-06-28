package de.htwg.se.DungeonsOfDoom.controller

import java.io.FileInputStream

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.utility.{EventListener, JSONState, TimeManager, XMLstate}
import de.htwg.se.DungeonsOfDoom.model.board.Floor
import de.htwg.se.DungeonsOfDoom.model.items.{HealingPotion, Key}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

@RunWith(classOf[JUnitRunner])
class EventListenerSpec extends WordSpec with Matchers {
  var listener = new EventListener(new JSONState)
  TestGame.init()
  "An EventListener" should {
    val weapon = BoardInteraction.player.equipped.head
    "handle PlayerWalk Up" in {
      BoardInteraction.player.currentPosition should be((1, 2))
      listener.deployEvent("PlayerWalk", Some("Up"), timeShouldAdvance = false)
      BoardInteraction.player.currentPosition should be((1, 1))
    }
    "handle PlayerWalk Down" in {
      listener.deployEvent("PlayerWalk", Some("Down"), timeShouldAdvance = false)
      BoardInteraction.player.currentPosition should be((1, 2))
    }
    "handle Walking against the wall" in {
      listener.deployEvent("PlayerWalk", Some("Left"), timeShouldAdvance = false)
      BoardInteraction.player.currentPosition should be((1, 2))
    }
    "handle attacking an enemy" in {
      listener.deployEvent("PlayerWalk", Some("Right"), timeShouldAdvance = false)
      BoardInteraction.player.currentPosition should be((1, 2))
      (BoardInteraction.enemyList.head.currentHealth <= BoardInteraction.enemyList.head.health) should be(true)
    }
    "handle picking up item event" in {
      BoardInteraction.player.inventory.isEmpty should be(true)
      listener.deployEvent("PickUpItem", timeShouldAdvance = false)
      BoardInteraction.player.inventory.isEmpty should be(false)
    }
    "handle dropping of items" in {
      val key = BoardInteraction.player.inventory.find(x => x.isInstanceOf[Key]).get
      listener.deployEvent("DropItem", Some(key), timeShouldAdvance = false)
      BoardInteraction.board.map(1)(2).asInstanceOf[Floor].inventory.isEmpty should be(false)
    }
    "handle unequipping and equipping" in {
      listener.deployEvent("UnequipItem", Some(weapon), timeShouldAdvance = false)
      BoardInteraction.player.equipped.isEmpty should be(true)
      listener.deployEvent("UseItem", Some(weapon), timeShouldAdvance = false)
      BoardInteraction.player.equipped.isEmpty should be(false)
    }
    "handle use of health potions" in {
      BoardInteraction.player.currentHealth = 5
      val potion = BoardInteraction.player.inventory.find(x => x.isInstanceOf[HealingPotion]).get
      listener.deployEvent("UseItem", Some(potion), timeShouldAdvance = false)
      BoardInteraction.player.currentHealth should be(10)
    }
    "handle undo" in {
      val previousState = listener.stateManager.getState
      listener.deployEvent("PlayerWalk", Some("Up"), timeShouldAdvance = false)
      listener.deployEvent("Undo", timeShouldAdvance = false)
      val currentState = listener.stateManager.getState
      currentState should be(previousState)
    }
    "save the game on exit" in {
      listener.deployEvent("PlayerWalk", Some("Up"), timeShouldAdvance = false)
      listener.deployEvent("Exit", timeShouldAdvance = false)
      val bw = new FileInputStream("savegame.json")
      val content = try { Json.parse(bw) } finally { bw.close() }
      val savedState = listener.stateManager.getState
      savedState.contents should be(Json.prettyPrint(content))
    }
  }
}
