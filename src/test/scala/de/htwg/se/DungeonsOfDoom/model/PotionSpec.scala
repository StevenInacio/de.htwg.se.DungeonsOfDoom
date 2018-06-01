package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.items.HealingPotion
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PotionSpec extends WordSpec with Matchers {
  "A Potion" when {
    "new" should {
      //                         name        weight  value   usage  healthBonus
      val potion = HealingPotion("cheese",      2,      5,      1,        10)
      "have some values" in {
        potion.name should be("cheese")
        potion.weight should be(2)
        potion.value should be(5)
        potion.usage should be(1)
        potion.healthBonus should be(10)
      }
    }
  }
}
