package de.htwg.se.DungeonsOfDoom.view.TUI

class TextView {
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
}


