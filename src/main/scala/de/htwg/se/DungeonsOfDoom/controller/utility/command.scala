package de.htwg.se.DungeonsOfDoom.controller.utility

class Command(command: String, value: String) {

  def this() = this(null, null)

  override def toString: String = {
    "command: ".concat(this.command).concat(" | value: ").concat(this.value)
  }

  def fromString(input: String) : Command = {
    var iter = input.split(" ").iterator
    iter.next() match {
      case "attack" => {
        var commandValue = ""
        for(i <- iter){
          commandValue = commandValue.concat(i).concat(" ")
        }
        new Command("attack", commandValue.trim)
      }
      case "walk" => {
        var commandValue = ""
        for(i <- iter){
          commandValue = commandValue.concat(i).concat(" ")
        }
        new Command("walk", commandValue.trim)
      }
      case _ => new Command("ERROR", "unkown command")
    }
  }
}
