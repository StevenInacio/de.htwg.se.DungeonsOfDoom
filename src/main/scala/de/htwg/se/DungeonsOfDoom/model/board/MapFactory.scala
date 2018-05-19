package de.htwg.se.DungeonsOfDoom.model.board

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
}
