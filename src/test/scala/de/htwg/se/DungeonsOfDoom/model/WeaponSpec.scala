package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.items.Weapon
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WeaponSpec extends WordSpec with Matchers {
  "A Weapon" when {
    "new" should {
      //                        name        durability  weight  value   damage  minStrength
      val weapon = Weapon(      "Shiny Dagger",    10,      5,      100,    20,     3)
      val brokenWeapon = Weapon("Rusty Dagger",     3,      5,      20,     15,     3)
      "have some values" in {
        weapon.name should be("Shiny Dagger")
        weapon.durability should be(10)
        weapon.weight should be(5)
        weapon.value should be(100)
        weapon.damage should be(20)
        weapon.minStrength should be(3)
      }
      "lose durability" in {
        weapon.lowerDurability(7) should be(brokenWeapon)
      }
      "gain durability" in {
        brokenWeapon.repair(7) should be(weapon)
      }
    }
  }
}
