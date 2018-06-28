package de.htwg.se.DungeonsOfDoom.view.tui

import java.util.{Observable, Observer}

import de.htwg.se.DungeonsOfDoom.controller.utility.EventListener

class TUI(listener : EventListener) extends Observer {
  val DEBUG = true
  val ERROR = true
  val OUT = true
  def logDebug(input : String) : Unit = {
    if(DEBUG) print(input)
  }
  def logError(input : String) : Unit = {
    if(ERROR) print(input)
  }
  def logOut(input : String) : Unit = {
    if(OUT) print(input)
  }

  override def update(o: Observable, arg: scala.Any): Unit = ???
}


