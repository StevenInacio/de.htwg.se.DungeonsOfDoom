package de.htwg.se.DungeonsOfDoom.model.board

import de.htwg.se.DungeonsOfDoom.model.items.Item

import scala.collection.mutable.ListBuffer

case class Floor(var inventory: ListBuffer[Item] = new ListBuffer[Item]) extends Field with Walkable {

  def putOnFloor(item: Item): Unit = {
    inventory += item
  }

  def pickUpFromFloor(index: Int): Item = {
    inventory.remove(index)
  }

  def pickUpFromFloor(item: Item): Option[Item] = {
    if (inventory.contains(item)) {
      inventory -= item
      Some(item)
    } else {
      None
    }
  }

  /*def visit(pawn: Pawn) : Unit = visitedBy = Some(pawn)

  def leave() : Pawn = {
    val tmp = visitedBy.get
    visitedBy = None
    tmp
  }*/
  /*def displayInventory() : String = {
    if (inventory.isEmpty) {
      "You see nothing on the floor!"
    }
    else {
      var s = "On the floor is:\n"
      inventory.foreach(s += _.toString + "\n")
      s
    }
  }
  */
  /*override def toString : String = {
    visitedBy match {
      case Some(x) => x match {
        case _ : Player => "@"
        case x : Enemy => x.toString.substring(0,1)
      }
      case None => inventory.isEmpty match {
        case true => " "
        case false => "?"
      }
    }
  }*/
}
