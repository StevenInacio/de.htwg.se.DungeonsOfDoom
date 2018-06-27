package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.controller.utility.{EventListener, JSONstate, TimeManager}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class EventListenerSpec extends WordSpec with Matchers {
  var listener = new EventListener(new TimeManager(new JSONstate))
  "An EventListener" should {
    "delegate all Other Controller" in {
      listener.deployEvent("PlayerWalk", Some("Up"))
    }
  }
}
