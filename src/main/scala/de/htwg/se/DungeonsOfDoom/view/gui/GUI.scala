package de.htwg.se.DungeonsOfDoom.view.gui

import java.util.{Observable, Observer}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.utility.EventListener

import scala.collection.mutable.ListBuffer
import scala.swing._

class GUI(listener: EventListener) extends SimpleSwingApplication with Observer {
  listener.addObserver(this)

  val mapLabel = new TextArea("Nothing Yet") {
    editable = false
    font = new Font("Lucida Console", 0, 18)
  }
  val playerLabel = new TextArea("No Player") {
    editable = false
    font = new Font("Lucida Console", 0, 18)
  }

  override def top: Frame = new MainFrame() {
    title = "Dungeons of Doom Test"
    contents = new GridPanel(3, 1) {
      contents += mapLabel
      contents += playerLabel
      contents += new TextField()
    }
    size = new Dimension(640, 480)
    visible = true
  }

  override def update(o: Observable, arg: scala.Any): Unit = {
    parseMap()
    parsePlayer()
  }

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
    mapLabel.text = result
  }

  def parsePlayer(): Unit = {
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
    playerLabel.text = s
  }
}
