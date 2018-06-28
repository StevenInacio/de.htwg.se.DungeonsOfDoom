package de.htwg.se.DungeonsOfDoom.model.board

import de.htwg.se.DungeonsOfDoom.model.items.{HealingPotion, Item, Weapon}

import scala.collection.mutable.ListBuffer

object MapFactory {
  /*class MapTree(columns: Int, rows: Int) {
    class Node(columns: Int, rows: Int, width: Int, height: Int) {
      var parent : Node = _
      var left : Node = _
      var right : Node = _
    }
    var root : Node = new Node(0, 0, columns, rows)

    def split(times: Int) : Unit = {
      //unimplemented
    }

  }*/

  def generate(): (Array[Array[Field]], (Int, Int)) = {
    /*val rand = Random
    val columns = rand.nextInt(101) + 50 // scalastyle:ignore
    val rows = rand.nextInt(101) + 50 // scalastyle:ignore
    var map = Array.ofDim[Field](columns, rows)
    var mapTree = new MapTree(columns, rows)
    map*/
    (testMap, (1, 2))
  }

  //scalastyle:off magic.number
  def testMap: Array[Array[Field]] = {
    val closedDoor = Door(DoorState.closed)
    val fullInventory = new ListBuffer[Item]()
    fullInventory += Weapon("Sword", 10, 20, 5, 100, 3, 0)
    fullInventory += HealingPotion("Ham", 1, 3, 1, 5)
    val floorWithItems = Floor(fullInventory)
    val map = Array.ofDim[Field](10, 10)
    for (x <- map.indices) {
      for (y <- map(0).indices) {
        map(x)(y) = Floor()
      }
    }
    for (i <- 0 to 9) {
      map(i)(0) = Wall()
      map(0)(i) = Wall()
      map(i)(9) = Wall()
      map(9)(i) = Wall()
    }
    map(4)(3) = floorWithItems
    map(5)(7) = closedDoor
    map
  }

  //scalastyle:on magic.number
}
