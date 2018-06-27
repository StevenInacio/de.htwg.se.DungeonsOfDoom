package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.controller.utility.Command
import org.scalatest.{Matchers, WordSpec}

class CommandSpec extends WordSpec with Matchers{
  "A String" when {
    "creating a command" should {
      "split it up" in {
        val com1 = new Command().fromString("attack enemy")
        val com2 = new Command("attack", "enemy")
        com1.toString should be(com2.toString)
      }
    }
  }
}
