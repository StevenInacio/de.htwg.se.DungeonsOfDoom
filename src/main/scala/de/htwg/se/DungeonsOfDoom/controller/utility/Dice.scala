package de.htwg.se.DungeonsOfDoom.controller.utility

object Dice {
  def roll(size: Integer): Integer = {
    if (size > 0){
      val r = scala.util.Random
      getRollWithoutRandom(size, r.nextInt(size))
    } else 0

  }
  def getRollWithoutRandom(size: Integer, rand: Integer): Integer = {
    var mrand = rand
    if (mrand >= size) {
      mrand = size - 1
    }
    if (size > 0) {
      mrand + 1
    } else 0
  }
}
