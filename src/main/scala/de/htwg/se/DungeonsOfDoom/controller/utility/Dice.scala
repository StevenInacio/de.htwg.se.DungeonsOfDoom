package de.htwg.se.DungeonsOfDoom.controller.utility

object Dice {
  def roll(size: Integer): Integer = {
    val r = scala.util.Random
    r.nextInt(size) + 1
  }
}
