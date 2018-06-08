package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.controller.utility.Dice
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
      "can (in soecial cases) roll 0" in {
        Dice.roll(0) should be(0)
      }
    }
  }
}
