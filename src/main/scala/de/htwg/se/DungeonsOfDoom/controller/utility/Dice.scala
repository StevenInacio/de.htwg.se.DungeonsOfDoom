package de.htwg.se.DungeonsOfDoom.controller.utility

object Dice {
  def roll(size: Integer): Integer = {
    if (size > 0) {
      val r = scala.util.Random
      r.nextInt(size) + 1
    } else 0
  }
}
