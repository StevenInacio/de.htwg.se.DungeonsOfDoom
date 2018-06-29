package de.htwg.se.DungeonsOfDoom

import com.google.inject.{Guice, Injector}
import de.htwg.se.DungeonsOfDoom.controller.utility.{EventListener, JSONState, StateModuleWithJson}
import de.htwg.se.DungeonsOfDoom.view.gui.GUI
import de.htwg.se.DungeonsOfDoom.view.tui.TUI

object DungeonsOfDoom {

  def main(args: Array[String]) : Unit = {
    val injector: Injector = Guice.createInjector(new StateModuleWithJson())
    val stateManager = injector.getInstance(classOf[JSONState])
    val listener = new EventListener(stateManager)
    val tui = new TUI(listener)
    val gui = new GUI(listener)
    gui.top
    tui.init()
  }

}