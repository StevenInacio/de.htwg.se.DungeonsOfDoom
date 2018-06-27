package de.htwg.se.DungeonsOfDoom.controller.utility

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class StateModuleWithJson extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[StateManager].to[JSONstate]
  }
}
