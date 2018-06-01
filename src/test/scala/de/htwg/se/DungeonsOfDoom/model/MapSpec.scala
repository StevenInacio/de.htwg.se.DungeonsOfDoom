package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.board._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MapSpec extends WordSpec with Matchers {
  "A Map" when {
    "new and empty" should {
      val map = new Map()
      "be the Test Map" in {
        map.map should be(MapFactory.testMap)
      }
    }
    "new and not empty" should {
      val filledMap = Array.ofDim[Field](2, 2)
      filledMap(0)(0) = Floor()
      filledMap(1)(0) = Door(DoorState.open)
      filledMap(0)(1) = Floor()
      filledMap(1)(1) = Wall()
      val newFilledMap = new Map(filledMap)
      "should be the given map" in {
        newFilledMap.map should be(filledMap)
      }
    }
  }
}
