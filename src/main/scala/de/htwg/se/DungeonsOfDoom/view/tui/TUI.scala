package de.htwg.se.DungeonsOfDoom.view.tui

import java.util.{Observable, Observer}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.utility.EventListener

import scala.collection.mutable.ListBuffer

class TUI(listener: EventListener) extends Observer {
  listener.addObserver(this)
  val DEBUG = true
  val ERROR = true
  val OUT = true
  var didDamage: Option[Int] = None

  def logDebug(input: String): Unit = {
    if (DEBUG) print(input)
  }

  def logError(input: String): Unit = {
    if (ERROR) print(input)
  }

  override def update(o: Observable, arg: scala.Any): Unit = {
    parseMap()
    printPlayer()
  }

  def printPlayer(): Unit = {
    var s = ""
    val player = BoardInteraction.player
    s += player.name + "\t\t" + "Health: " + player.currentHealth + "/" + player.health + "\n"
    s += "Stats:\n"
    s += "Body: " + player.body + "\tAgility: " + player.agility + "\tSpirit: " + player.spirit + "\n"
    s += "Strength: " + player.strength + "\tMobility: " + player.mobility + "\tMind: " + player.mind + "\n"
    s += "Hardness: " + player.hardness + "\tDexterity: " + player.dexterity + "\tAura: " + player.aura + "\n"
    s += "Max Damage: " + player.hit + "\tDefense: " + player.defense + "\n"
    s += "Inventory:\n"
    s += player.inventory.map(x => x.name).mkString("", ", ", "\n")
    logOut(s)
  }

  def logOut(input: String): Unit = {
    if (OUT) print(input)
  }

  //scalastyle:off
  def parseMap(): Unit = {
    val map = BoardInteraction.board.map
    var s = ListBuffer[Array[Char]]()
    for (x <- map.indices) {
      val rowBuffer = new ListBuffer[Char]()
      for (y <- map(x).indices) {
        map(x)(y).getClass.getSimpleName match {
          case "Floor" => BoardInteraction.hasEnemy(map(x)(y)) match {
            case Some(x) => rowBuffer += x
            case None => if (BoardInteraction.hasLoot(map(x)(y))) {
              rowBuffer += '?'
            } else {
              rowBuffer += '.'
            }
          }
          case "Wall" => rowBuffer += '#'
          case "Door" => if (BoardInteraction.isDoorOpen(map(x)(y))) {
            BoardInteraction.hasEnemy(map(x)(y)) match {
              case Some(x) => rowBuffer += x
              case None => rowBuffer += '.'
            }
          } else {
            rowBuffer += '&'
          }
        }
      }
      s += rowBuffer.toArray
    }
    val pos = BoardInteraction.player.currentPosition
    s(pos._1)(pos._2) = '@'
    val array = s.toArray.transpose
    var result = ""
    for (x <- array.indices) {
      for (y <- array(x).indices) {
        result += array(x)(y).toString
      }
      result += "\n"
    }
    logOut(result)
  }

  def init(): Unit = {
    logOut("Welcome to Dungeons of Doom!\n")
    logOut("Enter \"New\" to start a new Game\nOr enter \"Load\" to continue where you left off.\n")
    scala.io.StdIn.readLine().capitalize match {
      case "New" => listener.deployEvent("CreatePlayer")
      case "Load" => listener.deployEvent("Load")
    }

    parseInput()
  }

  def parseInput(): Unit = {
    while (true) {
      val input = scala.io.StdIn.readLine("What do you want to do?\n>").split("\\s+", 2).toIterator
      val inputResult = input.next().capitalize
      inputResult match {
        case "Walk" => val result = input.next().capitalize
          listener.deployEvent("PlayerWalk", Some(result)) match {
            case Some(x) => logOut("You did " + x + " Damage!\n")
            case None => Unit
          }
        case "Pickup" => listener.deployEvent("PickUpItem")
        case "Drop" => listener.deployEvent("DropItem",
          BoardInteraction.player.inventory.find(x => x.name == input.next().toLowerCase.split(" ").map(x => x.capitalize).mkString(" ")))
        case "Use" =>
          var result = input.next().toLowerCase.split(" ").map(x => x.capitalize).mkString(" ")
          listener.deployEvent("UseItem",
            BoardInteraction.player.inventory.find(x => x.name == result))
        case "Unequip" => listener.deployEvent("UnequipItem",
          BoardInteraction.player.inventory.find(x => x.name == input.next().toLowerCase.split(" ").map(x => x.capitalize).mkString(" ")))
        case "Undo" => listener.deployEvent("Undo")
        case "Exit" => listener.deployEvent("Exit")
          System.exit(0)
        case _ => logOut("I didn't understand that.\n")
      }
    }
  }

  //scalastyle:on


}


