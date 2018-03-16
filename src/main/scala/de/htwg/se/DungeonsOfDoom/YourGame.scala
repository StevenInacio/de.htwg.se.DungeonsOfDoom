package de.htwg.se.DungeonsOfDoom

import de.htwg.se.DungeonsOfDoom.model.Player

object DungeonsOfDoom {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
