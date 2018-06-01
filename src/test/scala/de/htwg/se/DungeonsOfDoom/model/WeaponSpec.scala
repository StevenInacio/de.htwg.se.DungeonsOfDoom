package de.htwg.se.DungeonsOfDoom.model

import de.htwg.se.DungeonsOfDoom.model.items.Weapon
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WeaponSpec extends WordSpec with Matchers {
  "A Weapon" when {
    "new" should {
      //                  name        durability  maxDurability weight  value   damage  minStrength
      val weapon = Weapon("Shiny Dagger",    10,      10,       5,        100,    20,     3)
      "have some values" in {
        weapon.name should be("Shiny Dagger")
        weapon.durability should be(10)
        weapon.weight should be(5)
        weapon.value should be(100)
        weapon.damage should be(20)
        weapon.minStrength should be(3)
      }
      "lose durability" in {
        weapon.lowerDurability(7)
        weapon.durability should be(3)
      }
      "lose even more durability" in {
        weapon.lowerDurability(5)
        weapon.durability should be(0)
      }
      "be broken" in {
        weapon.isBroken should be(true)
      }
      "gain durability" in {
        weapon.repair(10)
        weapon.durability should be(10)
      }
      "not gain durability" in {
        weapon.repair(5)
        weapon.durability should be(10)
      }
      "work again" in {
        weapon.isBroken should be(false)
      }
    }
  }
}
