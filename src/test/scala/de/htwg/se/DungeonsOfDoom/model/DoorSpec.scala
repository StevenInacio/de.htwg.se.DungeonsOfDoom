package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.board.{Door, DoorState}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DoorSpec extends WordSpec with Matchers {
  "A Door" when {
    "new and opened" should {
      val openDoor = Door(DoorState.open)
      "be open" in {
        openDoor.isOpen should be(true)
      }
      "be not closed" in {
        openDoor.isClosed should be(false)
      }
      "and also not locked" in {
        openDoor.isLocked should be(false)
      }
      "have an open description" in {
        openDoor.toString should be("an open door")
      }
      /*"be not opennable" in {
        openDoor.open() should be(None)
        openDoor.isOpen should be(true)
      }
      "be closable" in {
        openDoor.close() should be(true)
        openDoor.isClosed should be(true)
      }*/
    }
    "new and closed" should {
      val closedDoor = Door(DoorState.closed)
      "be closed" in {
        closedDoor.isClosed should be(true)
      }
      "have a closed description" in {
        closedDoor.toString should be("a closed door")
      }
      /*"be not closable" in {
        closedDoor.close() should be(false)
        closedDoor.isClosed should be(true)
      }
      "be openable" in {
        closedDoor.open() should be(Some(true))
        closedDoor.isOpen should be(true)
      }*/
    }
    "new and locked" should {
      val lockedDoor = Door(DoorState.locked)
      "be locked" in {
        lockedDoor.isLocked should be(true)
      }
      "have a closed description" in {
        lockedDoor.toString should be("a closed door")
      }
      /*"be unopenable" in {
        lockedDoor.open() should be(Some(false))
        lockedDoor.isOpen should be(false)
      }
      "be unclosable" in {
        lockedDoor.close() should be(false)
        lockedDoor.isClosed should be(false)
      }*/
    }
  }
}
