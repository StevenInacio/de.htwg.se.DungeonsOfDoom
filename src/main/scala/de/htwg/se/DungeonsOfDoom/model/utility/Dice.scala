package de.htwg.se.DungeonsOfDoom.model.utility

object Dice {
  def roll(size: Int): Int = {
    if (size > 0){
      val r = scala.util.Random
      getRollWithoutRandom(size, r.nextInt(size))
    } else 0

  }

  def getRollWithoutRandom(size: Int, rand: Int): Int = {
    var mrand = rand
    if (mrand >= size) {
      mrand = size - 1
    }
    if (size > 0) {
      mrand + 1
    } else 0
  }
}
