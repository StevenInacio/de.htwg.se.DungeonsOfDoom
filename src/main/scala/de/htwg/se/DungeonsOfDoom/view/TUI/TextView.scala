package de.htwg.se.DungeonsOfDoom.view.TUI

class TextView {
  val DEBUG = true
  val ERROR = true
  val OUT = true
  def log(channel : Boolean, input : String) : Unit = {
    if(channel) print(input)
  }

}


