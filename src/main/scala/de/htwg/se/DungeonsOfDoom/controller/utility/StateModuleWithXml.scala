package de.htwg.se.DungeonsOfDoom.controller.utility

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class StateModuleWithXml extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[StateManager].to[XMLstate]
  }

}
