package de.htwg.se.DungeonsOfDoom.model.board

import de.htwg.se.DungeonsOfDoom.model.items.Item

import scala.collection.mutable.ListBuffer
import scala.util.Random

object MapFactory {
  class MapTree(columns: Int, rows: Int) {
    class Node(columns: Int, rows: Int, width: Int, height: Int) {
      var parent : Node = _
      var left : Node = _
      var right : Node = _
    }
    var root : Node = new Node(0, 0, columns, rows)

    def split(times: Int) : Unit = {
      //unimplemented
    }

  }

  def generate() : Array[Array[Field]] = {
    val rand = Random
    val columns = rand.nextInt(101) + 50 // scalastyle:ignore
    val rows = rand.nextInt(101) + 50 // scalastyle:ignore
    var map = Array.ofDim[Field](columns, rows)
    var mapTree = new MapTree(columns, rows)
    map
  }

  def testMap : Array[Array[Field]] = {
    val wall = new Wall
    val closedDoor = new Door(DoorState.closed)
    val floor = new Floor
    var floorWithItems = new Floor(new ListBuffer[Item](new Weapon("Sword", 10, 20, 5, 100, 3, 0), new HealingPotion("Ham", 3, 3, 3, 3)))
  }
}
