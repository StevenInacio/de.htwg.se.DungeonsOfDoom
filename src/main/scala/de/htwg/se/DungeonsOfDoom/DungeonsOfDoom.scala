package de.htwg.se.DungeonsOfDoom

import model.pawns.Player

object DungeonsOfDoom {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}