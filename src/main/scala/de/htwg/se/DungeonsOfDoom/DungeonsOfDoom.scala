package de.htwg.se.DungeonsOfDoom

import com.google.inject.{Guice, Injector}
import de.htwg.se.DungeonsOfDoom.controller.utility.{EventListener, JSONState, StateModuleWithJson, TimeManager}

object DungeonsOfDoom {

  def main(args: Array[String]) : Unit = {
    val injector: Injector = Guice.createInjector(new StateModuleWithJson())
    val stateManager = injector.getInstance(classOf[JSONState])
    val listener = new EventListener(stateManager)
    //val tui = new Tui(listener)
    //val gui = new Gui(listener)
  }

}