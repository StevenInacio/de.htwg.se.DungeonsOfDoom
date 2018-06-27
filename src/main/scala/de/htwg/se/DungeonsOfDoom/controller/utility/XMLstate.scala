package de.htwg.se.DungeonsOfDoom.controller.utility

import java.io.{BufferedWriter, FileWriter}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, Floor, Map, Walkable, Wall}
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
    val PLAYER_INVENTORY_SEQUENCE = (xml \ "player" \ "inventory")
    var player_inventory = new ListBuffer[Item]
    for(n <- PLAYER_INVENTORY_SEQUENCE){
      n match {
        case <weapon>{w}</weapon> => {
          player_inventory += Weapon((w \ "name").text,
                              (w \ "durability").text.toInt,
                              (w \ "maxdurability").text.toInt,
                              (w \ "weight").text.toInt,
                              (w \ "value").text.toInt,
                              (w \ "damage").text.toInt,
                              (w \ "minstrength").text.toInt)
        }
        case <healingpostion>{h}</healingpostion> => {
          player_inventory += HealingPotion((h \ "name").text,
                                     (h \ "weight").text.toInt,
                                     (h \ "value").text.toInt,
                                     (h \ "usage").text.toInt,
                                     (h \ "healthbonus").text.toInt)
        }
        case <key>{k}</key> => {
          player_inventory += new Key
        }
      }
    }
    val PLAYER_EQUIPPED_SEQUENCE = (xml \ "player" \ "equipped")
    var player_equipped = new ListBuffer[Equipable]
    for(n <- PLAYER_EQUIPPED_SEQUENCE){
      n match{
        case <weapon>{w}</weapon> => {
            player_inventory += Weapon((w \ "name").text,
                                       (w \ "durability").text.toInt,
                                       (w \ "maxdurability").text.toInt,
                                       (w \ "weight").text.toInt,
                                       (w \ "value").text.toInt,
                                       (w \ "damage").text.toInt,
                                       (w \ "minstrength").text.toInt)
        }
      }
    }
    val player = Player(
      player_name,
      player_body,
      player_strength,
      player_hardness,
      player_agility,
      player_mobility,
      player_dexterity,
      player_spirit,
      player_mind,
      player_aura,
      currentPosition = (player_x, player_y)
    )
    player.inventory = player_inventory
    player.equipped = player_equipped
    for (e <- player.equipped) {
      e match {
        case w: Weapon => player.melee_bonus += w.damage
      }
    }

    var enemyList = new ListBuffer[Enemy]
    val ENEMY_SEQUENCE = (xml \ "enemys")
    for (e <- ENEMY_SEQUENCE) {
      val enemy_name = (e \ "name").text
      val enemy_body = (e \ "body").text.toInt
      val enemy_strength = (e \ "strength").text.toInt
      val enemy_hardness = (e \ "hardness").text.toInt
      val enemy_agility = (e \ "agility").text.toInt
      val enemy_mobility = (e \ "mobility").text.toInt
      val enemy_dexterity = (e \ "dexterity").text.toInt
      val enemy_spirit = (e \ "spirit").text.toInt
      val enemy_mind = (e \ "mind").text.toInt
      val enemy_aura = (e \ "aura").text.toInt
      val e_pos_x = (e \ "positionx").text.toInt
      val e_pos_y = (e \ "positiony").text.toInt
      val enemy = Enemy(
        enemy_name,
        enemy_body,
        enemy_strength,
        enemy_hardness,
        enemy_agility,
        enemy_mobility,
        enemy_dexterity,
        enemy_spirit,
        enemy_mind,
        enemy_aura,
        currentPosition = (e_pos_x, e_pos_y)
      )
      val ENEMY_INVENTORY_SEQUENCE = (xml \ "enemy" \ "inventory")
      var enemy_inventory = new ListBuffer[Item]
      for(n <- ENEMY_INVENTORY_SEQUENCE){
        n match {
          case <weapon>{w}</weapon> => {
            enemy_inventory += Weapon((w \ "name").text,
              (w \ "durability").text.toInt,
              (w \ "maxdurability").text.toInt,
              (w \ "weight").text.toInt,
              (w \ "value").text.toInt,
              (w \ "damage").text.toInt,
              (w \ "minstrength").text.toInt)
          }
          case <healingpostion>{h}</healingpostion> => {
            enemy_inventory += HealingPotion((h \ "name").text,
              (h \ "weight").text.toInt,
              (h \ "value").text.toInt,
              (h \ "usage").text.toInt,
              (h \ "healthbonus").text.toInt)
          }
          case <key>{k}</key> => {
            enemy_inventory += new Key
          }
        }
      }
      enemy.inventory = enemy_inventory
      val ENEMY_EQUIPPED_SEQUENCE = (xml \ "enemy" \ "equipped")
      var enemy_equipped = new ListBuffer[Equipable]
      for(n <- ENEMY_EQUIPPED_SEQUENCE){
        n match{
          case <weapon>{w}</weapon> => {
            enemy_inventory += Weapon((w \ "name").text,
              (w \ "durability").text.toInt,
              (w \ "maxdurability").text.toInt,
              (w \ "weight").text.toInt,
              (w \ "value").text.toInt,
              (w \ "damage").text.toInt,
              (w \ "minstrength").text.toInt)
          }
        }
      }
      enemy.equipped = enemy_equipped
      for (e <- enemy.equipped) {
        e match {
          case w: Weapon => enemy.melee_bonus += w.damage
        }
      }
      enemyList += enemy
    }
    var map = new Map //TODO make this array[array]
    val BOARD_SEQUENCE = (xml \ "board")
    for(r <- BOARD_SEQUENCE){
      val FIELD_SEQUENCE = (r \ "row")
      var row = new Array[Walkable] //TODO make this an array
      for(f <- FIELD_SEQUENCE){

        f match {
          case <wall></wall> => row += new Wall() //add new wall to array row
          case <door>{d}</door> => {
            //TODO read doorstate from d and add new door to array row
          }
          case <floor>{f}</floor> => {
            //TODO read inventory from f and add new floor with invenotsy to arra row
          }
        }
      }
      map += row //TODO append row to map
    }
    (map, player, enemyList)
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
