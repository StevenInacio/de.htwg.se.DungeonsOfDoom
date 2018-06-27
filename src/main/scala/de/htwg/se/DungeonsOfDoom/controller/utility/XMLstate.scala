package de.htwg.se.DungeonsOfDoom.controller.utility

import java.io.{BufferedWriter, FileWriter}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, DoorState, Field, Floor, Map, Wall}
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer
import scala.xml.{Node, XML}

class XMLstate extends StateManager {
  override def getState: State = {
    State(toXML.toString)
  }

  override def saveState(state: State): Unit = {
    val bw = new BufferedWriter(new FileWriter("savegame.xml"))
    bw.write(state.contents)
    bw.close()
  }

  override def loadState: (Map, Player, ListBuffer[Enemy]) = {
    val xml = XML.loadFile("savegame.xml")
    fromXML(xml)
  }

  def fromXML(xml: Node): (Map, Player, ListBuffer[Enemy]) = {
    val player = playerFromXML(xml)
    var enemyList = new ListBuffer[Enemy]
    val ENEMY_SEQUENCE = xml \ "enemys"
    for (e <- ENEMY_SEQUENCE) {
      val e_pos_x = (e \ "positionx").text.toInt
      val e_pos_y = (e \ "positiony").text.toInt
      val enemy = Enemy(
        (e \ "name").text,
        (e \ "body").text.toInt,
        (e \ "strength").text.toInt,
        (e \ "hardness").text.toInt,
        (e \ "agility").text.toInt,
        (e \ "mobility").text.toInt,
        (e \ "dexterity").text.toInt,
        (e \ "spirit").text.toInt,
        (e \ "mind").text.toInt,
        (e \ "aura").text.toInt,
        currentPosition = (e_pos_x, e_pos_y)
      )
      val ENEMY_INVENTORY_SEQUENCE = xml \ "enemy" \ "inventory"
      var enemy_inventory = new ListBuffer[Item]
      for (n <- ENEMY_INVENTORY_SEQUENCE) {
        n match {
          case <weapon>
            {w}
            </weapon> =>
            enemy_inventory += Weapon((w \ "name").text,
              (w \ "durability").text.toInt,
              (w \ "maxdurability").text.toInt,
              (w \ "weight").text.toInt,
              (w \ "value").text.toInt,
              (w \ "damage").text.toInt,
              (w \ "minstrength").text.toInt)

          case <healingpotion>
            {h}
            </healingpotion> =>
            enemy_inventory += HealingPotion((h \ "name").text,
              (h \ "weight").text.toInt,
              (h \ "value").text.toInt,
              (h \ "usage").text.toInt,
              (h \ "healthbonus").text.toInt)

          case <key>
            {_}
            </key> =>
            enemy_inventory += new Key

        }
      }
      enemy.inventory = enemy_inventory
      val ENEMY_EQUIPPED_SEQUENCE = xml \ "enemy" \ "equipped"
      val enemy_equipped = new ListBuffer[Equipable]
      for (n <- ENEMY_EQUIPPED_SEQUENCE) {
        n match {
          case <weapon>
            {w}
            </weapon> =>
            enemy_inventory += Weapon((w \ "name").text,
              (w \ "durability").text.toInt,
              (w \ "maxdurability").text.toInt,
              (w \ "weight").text.toInt,
              (w \ "value").text.toInt,
              (w \ "damage").text.toInt,
              (w \ "minstrength").text.toInt)

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
    var map = new Map(Array.ofDim[Field](4, 4))
    val BOARD_SEQUENCE = xml \ "board"
    val wall = new Wall
    var rowIndex = 0
    var fieldIndex = 0
    for (r <- BOARD_SEQUENCE) {
      val FIELD_SEQUENCE = r \ "row"
      for (field <- FIELD_SEQUENCE) {
        field match {
          case <wall></wall> =>
            map.map(rowIndex)(fieldIndex) = wall
            fieldIndex += 1

          case <door>
            {d}
            </door> =>
            map.map(rowIndex)(fieldIndex) = Door(
              (d \ "doorstate").text match {
                case "locked" => DoorState.locked
                case "closed" => DoorState.closed
                case "open" => DoorState.open
              }
            )
            fieldIndex += 1

          case <floor>
            {f}
            </floor> =>
            var floor_inventory = new ListBuffer[Item]
            val FLOOR_INVENTORY_SEQUENCE = f \ "inventory"
            for (n <- FLOOR_INVENTORY_SEQUENCE) {
              n match {
                case <weapon>
                  {w}
                  </weapon> =>
                  floor_inventory += Weapon((w \ "name").text,
                    (w \ "durability").text.toInt,
                    (w \ "maxdurability").text.toInt,
                    (w \ "weight").text.toInt,
                    (w \ "value").text.toInt,
                    (w \ "damage").text.toInt,
                    (w \ "minstrength").text.toInt)

                case <healingpotion>
                  {h}
                  </healingpotion> =>
                  floor_inventory += HealingPotion((h \ "name").text,
                    (h \ "weight").text.toInt,
                    (h \ "value").text.toInt,
                    (h \ "usage").text.toInt,
                    (h \ "healthbonus").text.toInt)

                case <key>
                  {_}
                  </key> =>
                  floor_inventory += new Key

              }
            }
            map.map(rowIndex)(fieldIndex) = Floor(floor_inventory)
            fieldIndex += 1

        }
      }
      fieldIndex = 0
      rowIndex += 1
    }
    (map, player, enemyList)
  }

  def toXML: Node = {
    <state>
      <player>
        <name>
          {BoardInteraction.player.name}
        </name>
        <body>
          {BoardInteraction.player.body}
        </body>
        <strength>
          {BoardInteraction.player.strength}
        </strength>
        <hardness>
          {BoardInteraction.player.hardness}
        </hardness>
        <agility>
          {BoardInteraction.player.agility}
        </agility>
        <mobility>
          {BoardInteraction.player.mobility}
        </mobility>
        <dexterity>
          {BoardInteraction.player.dexterity}
        </dexterity>
        <spirit>
          {BoardInteraction.player.spirit}
        </spirit>
        <mind>
          {BoardInteraction.player.mind}
        </mind>
        <aura>
          {BoardInteraction.player.aura}
        </aura>
        <positionx>
          {BoardInteraction.player.currentPosition._1}
        </positionx>
        <positiony>
          {BoardInteraction.player.currentPosition._2}
        </positiony>{inventoryToXML(BoardInteraction.player.inventory)
      equippedToXML(BoardInteraction.player.equipped)}
      </player>
      <enemys>
        {for (enemy <- BoardInteraction.enemyList) {
        <enemy>
          <name>
            {enemy.name}
          </name>
          <body>
            {enemy.body}
          </body>
          <strength>
            {enemy.strength}
          </strength>
          <hardness>
            {enemy.hardness}
          </hardness>
          <agility>
            {enemy.agility}
          </agility>
          <mobility>
            {enemy.mobility}
          </mobility>
          <dexterity>
            {enemy.dexterity}
          </dexterity>
          <spirit>
            {enemy.spirit}
          </spirit>
          <mind>
            {enemy.mind}
          </mind>
          <aura>
            {enemy.aura}
          </aura>
          <positionx>
            {enemy.currentPosition._1}
          </positionx>
          <positiony>
            {enemy.currentPosition._2}
          </positiony>{inventoryToXML(enemy.inventory)
        equippedToXML(enemy.equipped)}
        </enemy>
      }}
      </enemys>
      <board>
        {for (row <- BoardInteraction.board.map) {
        <row>
          {for (field <- row) {
          field match {
            case _: Wall =>
              <wall></wall>

            case d: Door =>
              <door>
                {<doorstate>
                {d.doorState}
              </doorstate>}
              </door>

            case f: Floor =>
              <floor>
                {inventoryToXML(f.inventory)}
              </floor>

          }
        }}
        </row>
      }}
      </board>
    </state>
  }

  def equippedToXML(list: ListBuffer[Equipable]): Node = {
    <equipped>
      {for (item <- list) {
      item match {
        case w: Weapon => <weapon>
          <name>
            {w.name}
          </name>
          <durability>
            {w.durability}
          </durability>
          <maxdurability>
            {w.maxDurability}
          </maxdurability>
          <weight>
            {w.weight}
          </weight>
          <value>
            {w.value}
          </value>
          <damage>
            {w.damage}
          </damage>
          <minstrength>
            {w.minStrength}
          </minstrength>
        </weapon>
      }
    }}
    </equipped>
  }

  def inventoryToXML(list: ListBuffer[Item]): Node = {
    <inventory>
      {for (item <- list) {
      item match {
        case w: Weapon =>
          <weapon>
            <name>
              {w.name}
            </name>
            <durability>
              {w.durability}
            </durability>
            <maxdurability>
              {w.maxDurability}
            </maxdurability>
            <weight>
              {w.weight}
            </weight>
            <value>
              {w.value}
            </value>
            <damage>
              {w.damage}
            </damage>
            <minstrength>
              {w.minStrength}
            </minstrength>
          </weapon>

        case h: HealingPotion =>
          <healingpotion>
            <name>
              {h.name}
            </name>
            <weight>
              {h.weight}
            </weight>
            <value>
              {h.value}
            </value>
            <usage>
              {h.usage}
            </usage>
            <healthbonus>
              {h.healthBonus}
            </healthbonus>
          </healingpotion>

        case _: Key => <key></key>
      }
    }}
    </inventory>
  }
  def playerFromXML(xml: Node) : Player = {
    val player_x = (xml \ "player" \ "positionx").text.toInt
    val player_y = (xml \ "player" \ "positiony").text.toInt
    val PLAYER_INVENTORY_SEQUENCE = xml \ "player" \ "inventory"
    var player_inventory = new ListBuffer[Item]
    val player = Player(
      (xml \ "player" \ "name").text,
      (xml \ "player" \ "body").text.toInt,
      (xml \ "player" \ "strength").text.toInt,
      (xml \ "player" \ "hardness").text.toInt,
      (xml \ "player" \ "agility").text.toInt,
      (xml \ "player" \ "mobility").text.toInt,
      (xml \ "player" \ "dexterity").text.toInt,
      (xml \ "player" \ "spirit").text.toInt,
      (xml \ "player" \ "mind").text.toInt,
      (xml \ "player" \ "aura").text.toInt,
      currentPosition = (player_x, player_y))
    for (n <- PLAYER_INVENTORY_SEQUENCE) {
      n match {
        case <weapon>
          {w}
          </weapon> =>
          player_inventory += Weapon((w \ "name").text,
            (w \ "durability").text.toInt,
            (w \ "maxdurability").text.toInt,
            (w \ "weight").text.toInt,
            (w \ "value").text.toInt,
            (w \ "damage").text.toInt,
            (w \ "minstrength").text.toInt)

        case <healingpotion>
          {h}
          </healingpotion> =>
          player_inventory += HealingPotion((h \ "name").text,
            (h \ "weight").text.toInt,
            (h \ "value").text.toInt,
            (h \ "usage").text.toInt,
            (h \ "healthbonus").text.toInt)

        case <key>
          {_}
          </key> =>
          player_inventory += new Key

      }
    }
    val PLAYER_EQUIPPED_SEQUENCE = xml \ "player" \ "equipped"
    var player_equipped = new ListBuffer[Equipable]
    for (n <- PLAYER_EQUIPPED_SEQUENCE) {
      n match {
        case <weapon>
          {w}
          </weapon> =>
          player_inventory += Weapon((w \ "name").text,
            (w \ "durability").text.toInt,
            (w \ "maxdurability").text.toInt,
            (w \ "weight").text.toInt,
            (w \ "value").text.toInt,
            (w \ "damage").text.toInt,
            (w \ "minstrength").text.toInt)

      }
    }

    player.inventory = player_inventory
    player.equipped = player_equipped
    for (e <- player.equipped) {
      e match {
        case w: Weapon => player.melee_bonus += w.damage
      }
    }
    player
  }
}