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

  def generate(): Array[Array[Field]] = {
    /*val rand = Random
    val columns = rand.nextInt(101) + 50 // scalastyle:ignore
    val rows = rand.nextInt(101) + 50 // scalastyle:ignore
    var map = Array.ofDim[Field](columns, rows)
    var mapTree = new MapTree(columns, rows)
    map*/
    testMap
  }

  //scalastyle:off magic.number
  def testMap: Array[Array[Field]] = {
    val wall = new Wall
    val closedDoor = Door(DoorState.closed)
    val floor = new Floor
    val fullInventory = new ListBuffer[Item]()
    fullInventory += Weapon("Sword", 10, 20, 5, 100, 3, 0)
    fullInventory += HealingPotion("Ham", 1, 3, 1, 5)
    val floorWithItems = Floor(fullInventory)
    val map = Array.ofDim[Field](4, 4)
    for (i <- 0 to 3) {
      map(i)(0) = wall
      map(0)(i) = wall
      map(i)(3) = wall
      map(3)(i) = wall
    }
    map(1)(1) = floorWithItems
    map(1)(2) = floor
    map(2)(1) = floor
    map(2)(2) = closedDoor
    map
  }

  //scalastyle:on magic.number
}
