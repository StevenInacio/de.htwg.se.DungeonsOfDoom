package de.htwg.se.DungeonsOfDoom.view

import de.htwg.se.DungeonsOfDoom.controller.TestGame
import de.htwg.se.DungeonsOfDoom.controller.utility.{EventListener, JSONState}
import de.htwg.se.DungeonsOfDoom.view.tui.TUI
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TUISpec extends WordSpec with Matchers {
  "A TUI" when {
    "new" should {
      val tui = new TUI(new EventListener(new JSONState))
      "have a parsePlayer Function" in {
        TestGame.init()
        tui.printPlayer()
      }
    }
  }
}
