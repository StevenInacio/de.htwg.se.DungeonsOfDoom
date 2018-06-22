package de.htwg.se.DungeonsOfDoom.controller

import de.htwg.se.DungeonsOfDoom.controller.utility.EventListener
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class EventListenerSpec extends WordSpec with Matchers {
  "An EventListener" should {
    "delegate all Other Controller" in {
      EventListener.deployEvent("PlayerWalk", Some("Up"))
    }
  }
}
