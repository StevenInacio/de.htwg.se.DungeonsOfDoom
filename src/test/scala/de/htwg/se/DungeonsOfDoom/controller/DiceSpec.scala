package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.model.utility.Dice
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DiceSpec extends WordSpec with Matchers{
  "A Dice" when {
    "rolled" should {
      "roll between 1 and 20" in {
        var result = Dice.roll(20)
        result <= 20 && result >= 1 should be(true)
      }
      "roll between 1 and 2" in {
        var result = Dice.roll(2)
        result <= 2 && result >= 1 should be(true)
      }
      "never roll lower than 0" in {
        Dice.roll(-5) should be(0)
      }
      "can (in special cases) roll 0" in {
        Dice.roll(0) should be(0)
      }
    }
    "rolled nonrandom" should {
      "roll 5 when random is 4" in {
        Dice.getRollWithoutRandom(6, 4) should be(5)
      }
      "roll 2 when random is 4 but size is 2" in {
        Dice.getRollWithoutRandom(2, 4) should be(2)
      }
      "roll 0 when random is any value but size is 0" in {
        Dice.getRollWithoutRandom(0, 15) should be(0)
      }
    }
  }
}
