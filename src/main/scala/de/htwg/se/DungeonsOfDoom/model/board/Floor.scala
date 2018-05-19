package de.htwg.se.DungeonsOfDoom.model.board

import de.htwg.se.DungeonsOfDoom.model.items.Item

import scala.collection.mutable.ListBuffer

class Floor(var inventory: ListBuffer[Item] = new ListBuffer[Item]) extends Field {

  def putOnFloor(item : Item) : Unit = {
    inventory += item
  }
  def pickUpFromFloor(index: Int) : Item = {
    inventory.remove(index)
  }
  def displayInventory() : String = {
    if (inventory.isEmpty) {
      "You see nothing on the floor!"
    }
    else {
      var s = "On the floor is:\n"
      inventory.foreach(s += _.toString + "\n")
      s
    }
  }
  override def toString : String = {
    if (inventory.isEmpty) {
      " "
    }
    else {
      inventory.last.toString
    }
  }
}
