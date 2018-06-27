package de.htwg.se.DungeonsOfDoom.controller.utility

import java.io.{BufferedWriter, FileWriter}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, Floor, Map, Wall}
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer
import scala.xml.{Node, XML}


class XMLstate extends StateManager{

  override def getState : State = {
    State(toXML.toString)
  }

  override def saveState(state: State) : Unit = {
    val bw = new BufferedWriter(new FileWriter("savegame.xml"))
    bw.write(state.contents)
    bw.close
  }

  override def loadState : (Map, Player, ListBuffer[Enemy]) = {
    val xml = XML.loadFile("savegame.xml")
    fromXML(xml)
  }

  def fromXML(xml : Node) : (Map, Player, ListBuffer[Enemy]) = {
    val player_name = (xml \ "player" \"name").text
    val player_body = (xml \ "player" \"body").text.toInt
    val player_strength = (xml \ "player" \"strength").text.toInt
    val player_hardness = (xml \ "player" \"hardness").text.toInt
    val player_agility = (xml \ "player" \"agility").text.toInt
    val player_mobility = (xml \ "player" \"mobility").text.toInt
    val player_dexterity = (xml \ "player" \"dexterity").text.toInt
    val player_spirit = (xml \ "player" \"spirit").text.toInt
    val player_mind = (xml \ "player" \"mind").text.toInt
    val player_aura = (xml \ "player" \"aura").text.toInt
    val player_x = (xml \ "player" \"positionx").text.toInt
    val player_y = (xml \ "player" \"positiony").text.toInt
    val INVENTORY_SEQUENCE = (xml \ "player" \ "inventory")
    var inventory = new ListBuffer[Item]
    for(n <- INVENTORY_SEQUENCE){
      n match {
        case <weapon>{w}</weapon> => {
          inventory += Weapon((w \ "name").text,
                              (w \ "durability").text.toInt,
                              (w \ "maxdurability").text.toInt,
                              (w \ "weight").text.toInt,
                              (w \ "value").text.toInt,
                              (w \ "damage").text.toInt,
                              (w \ "minstrength").text.toInt)
        }
        case <healingpostion>{h}</healingpostion> => {
          inventory += HealingPotion((h \ "name").text,
                                     (h \ "weight").text.toInt,
                                     (h \ "value").text.toInt,
                                     (h \ "usage").text.toInt,
                                     (h \ "healthbonus").text.toInt)
        }
        case <key>{k}</key> => {
          inventory += new Key
        }
      }
    }


    //TODO load enemys
    //TODO load board
    //TODO combine to (Map, Player, ListBuffer[Enemy])
  }

  def toXML() : Node = {
    <state>
      <player>
        <name>{BoardInteraction.player.name}</name>
        <body>{BoardInteraction.player.body}</body>
        <strength>{BoardInteraction.player.strength}</strength>
        <hardness>{BoardInteraction.player.hardness}</hardness>
        <agility>{BoardInteraction.player.agility}</agility>
        <mobility>{BoardInteraction.player.mobility}</mobility>
        <dexterity>{BoardInteraction.player.dexterity}</dexterity>
        <spirit>{BoardInteraction.player.spirit}</spirit>
        <mind>{BoardInteraction.player.mind}</mind>
        <aura>{BoardInteraction.player.aura}</aura>
        <positionx>{BoardInteraction.player.currentPosition._1}</positionx>
        <positiony>{BoardInteraction.player.currentPosition._2}</positiony>
        {
          inventoryToXML(BoardInteraction.player.inventory)
          equippedToXML(BoardInteraction.player.equipped)
        }
      </player>
      <enemys>{
        for (enemy <- BoardInteraction.enemyList) {
          <enemy>
            <name>{enemy.name}</name>
            <body>{enemy.body}</body>
            <strength>{enemy.strength}</strength>
            <hardness>{enemy.hardness}</hardness>
            <agility>{enemy.agility}</agility>
            <mobility>{enemy.mobility}</mobility>
            <dexterity>{enemy.dexterity}</dexterity>
            <spirit>{enemy.spirit}</spirit>
            <mind>{enemy.mind}</mind>
            <aura>{enemy.aura}</aura>
            <positionx>{enemy.currentPosition._1}</positionx>
            <positiony>{enemy.currentPosition._2}</positiony>
            {
              inventoryToXML(enemy.inventory)
              equippedToXML(enemy.equipped)
            }
          </enemy>
        }
      }
      </enemys>
      <board>{
        for(row <- BoardInteraction.board.map){
          <row>
            {
              for (field <- row) {
              field match {
                case w: Wall => {
                  <wall></wall>
                }
                case d: Door => {
                  <door>{
                      <doorstate>{d.doorState}</doorstate>
                    }</door>
                }
                case f: Floor => {
                  <floor>{
                      inventoryToXML(f.inventory)
                    }
                  </floor>
                }
              }
            }
          }</row>
        }
      }</board>
    </state>
  }

  def equippedToXML(list : ListBuffer[Equipable]) : Node = {
    <equipped>{
      for(item <- list){
        item match {
          case w : Weapon => <weapon>
            <name>{w.name}</name>
            <durability>{w.durability}</durability>
            <maxdurability>{w.maxDurability}</maxdurability>
            <weight>{w.weight}</weight>
            <value>{w.value}</value>
            <damage>{w.damage}</damage>
            <minstrength>{w.minStrength}</minstrength>
          </weapon>
        }
      }
    }</equipped>
  }

  def inventoryToXML(list : ListBuffer[Item]) : Node = {
    <inventory>{
      for(item <- list){
        item match {
          case w : Weapon => {<weapon>
            <name>{w.name}</name>
            <durability>{w.durability}</durability>
            <maxdurability>{w.maxDurability}</maxdurability>
            <weight>{w.weight}</weight>
            <value>{w.value}</value>
            <damage>{w.damage}</damage>
            <minstrength>{w.minStrength}</minstrength>
          </weapon>}
          case h : HealingPotion => {<healingpotion>
            <name>{h.name}</name>
            <weight>{h.weight}</weight>
            <value>{h.value}</value>
            <usage>{h.usage}</usage>
            <healthbonus>{h.healthBonus}</healthbonus>
          </healingpotion>}
          case k : Key => {<key></key>}
        }
      }
    }</inventory>
  }
}
