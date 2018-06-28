package de.htwg.se.DungeonsOfDoom.controller.utility

import java.io.{BufferedWriter, FileWriter}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, DoorState, Field, Floor, Map, Wall}
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}

import scala.collection.mutable.ListBuffer
import scala.xml.{Node, NodeBuffer, XML}

class XMLstate extends StateManager {
  override def getState: State = {
    val prettyPrinter = new xml.PrettyPrinter(120, 4)
    State(prettyPrinter.format(toXML))
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

  //scalastyle:off
  def fromXML(xml: Node): (Map, Player, ListBuffer[Enemy]) = {
    val player = playerFromXML(xml)
    var enemyList = new ListBuffer[Enemy]
    val ENEMY_SEQUENCE = (xml \ "enemys").head.nonEmptyChildren
    for (e <- ENEMY_SEQUENCE) {
      val e_pos_x = (e \ "positionx").text.trim.toInt
      val e_pos_y = (e \ "positiony").text.trim.toInt
      val enemy = Enemy(
        (e \ "name").text,
        (e \ "body").text.trim.toInt,
        (e \ "strength").text.trim.toInt,
        (e \ "hardness").text.trim.toInt,
        (e \ "agility").text.trim.toInt,
        (e \ "mobility").text.trim.toInt,
        (e \ "dexterity").text.trim.toInt,
        (e \ "spirit").text.trim.toInt,
        (e \ "mind").text.trim.toInt,
        (e \ "aura").text.trim.toInt,
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
              (w \ "durability").text.trim.toInt,
              (w \ "maxdurability").text.trim.toInt,
              (w \ "weight").text.trim.toInt,
              (w \ "value").text.trim.toInt,
              (w \ "damage").text.trim.toInt,
              (w \ "minstrength").text.trim.toInt)

          case <healingpotion>
            {h}
            </healingpotion> =>
            enemy_inventory += HealingPotion((h \ "name").text,
              (h \ "weight").text.trim.toInt,
              (h \ "value").text.trim.toInt,
              (h \ "usage").text.trim.toInt,
              (h \ "healthbonus").text.trim.toInt)

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
              (w \ "durability").text.trim.toInt,
              (w \ "maxdurability").text.trim.toInt,
              (w \ "weight").text.trim.toInt,
              (w \ "value").text.trim.toInt,
              (w \ "damage").text.trim.toInt,
              (w \ "minstrength").text.trim.toInt)

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
    //var map = new Map(Array.ofDim[Field](4, 4))
    val tempBoard = new ListBuffer[Array[Field]]
    val playerStartX = (xml \ "board" \ "playerstartx").text.trim.toInt
    val playerStartY = (xml \ "board" \ "playerstarty").text.trim.toInt
    val BOARD_SEQUENCE = xml \ "board"
    val wall = new Wall
    //var rowIndex = 0
    //var fieldIndex = 0
    for (r <- BOARD_SEQUENCE) {
      val FIELD_SEQUENCE = r \ "row"
      val row = new ListBuffer[Field]
      for (field <- FIELD_SEQUENCE) {
        field match {
          case <wall></wall> =>
            row += wall

          case <door>
            {d}
            </door> =>
            row += Door(
              (d \ "doorstate").text match {
                case "locked" => DoorState.locked
                case "closed" => DoorState.closed
                case "open" => DoorState.open
              }
            )

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
                    (w \ "durability").text.trim.toInt,
                    (w \ "maxdurability").text.trim.toInt,
                    (w \ "weight").text.trim.toInt,
                    (w \ "value").text.trim.toInt,
                    (w \ "damage").text.trim.toInt,
                    (w \ "minstrength").text.trim.toInt)

                case <healingpotion>
                  {h}
                  </healingpotion> =>
                  floor_inventory += HealingPotion((h \ "name").text,
                    (h \ "weight").text.trim.toInt,
                    (h \ "value").text.trim.toInt,
                    (h \ "usage").text.trim.toInt,
                    (h \ "healthbonus").text.trim.toInt)

                case <key>
                  {_}
                  </key> =>
                  floor_inventory += new Key

              }
            }
            row += Floor(floor_inventory)
        }
        tempBoard += row.toArray
      }
    }
    val map = new Map(tempBoard.toArray, (playerStartX, playerStartY))
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
        </positiony>
        {inventoryToXML(BoardInteraction.player.inventory)}
        {equippedToXML(BoardInteraction.player.equipped)}
      </player>
      <enemys>
        {
        val buffer = new NodeBuffer
        for (enemy <- BoardInteraction.enemyList) {
          buffer += <enemy>
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
            </positiony>
            {inventoryToXML(enemy.inventory)}
            {equippedToXML(enemy.equipped)}
          </enemy>
          }
        buffer
        }
      </enemys>
      <board>
        <playerstartx>{BoardInteraction.board.playerSpawnPoint._1}</playerstartx>
        <playerstarty>{BoardInteraction.board.playerSpawnPoint._2}</playerstarty>
        {
        val boardBuffer = new NodeBuffer
        for (row <- BoardInteraction.board.map) {
        boardBuffer += <row>
          {
          val rowBuffer = new NodeBuffer
          for (field <- row) {
          field match {
            case _: Wall =>
              rowBuffer += <wall>
              </wall>

            case d: Door =>
              rowBuffer += <door>
                {<doorstate>
                  {d.doorState}
                </doorstate>}
              </door>

            case f: Floor =>
              rowBuffer += <floor>
                {inventoryToXML(f.inventory)}
              </floor>
          }
        }
          rowBuffer}
        </row>
      }
        boardBuffer}
      </board>
    </state>
  }

  def equippedToXML(list: ListBuffer[Equipable]): Node = {
    val equips : xml.NodeBuffer = new xml.NodeBuffer
    for (item <- list) {
      item match {
        case w: Weapon => equips += <weapon>
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
    }
    <equipped>
      {equips}
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
    val player_x = (xml \ "player" \ "positionx").text.trim.toInt
    val player_y = (xml \ "player" \ "positiony").text.trim.toInt
    val PLAYER_INVENTORY_SEQUENCE = (xml \ "player" \ "inventory").head.child
    var player_inventory = new ListBuffer[Item]
    val player = Player(
      (xml \ "player" \ "name").text,
      (xml \ "player" \ "body").text.trim.toInt,
      (xml \ "player" \ "strength").text.trim.toInt,
      (xml \ "player" \ "hardness").text.trim.toInt,
      (xml \ "player" \ "agility").text.trim.toInt,
      (xml \ "player" \ "mobility").text.trim.toInt,
      (xml \ "player" \ "dexterity").text.trim.toInt,
      (xml \ "player" \ "spirit").text.trim.toInt,
      (xml \ "player" \ "mind").text.trim.toInt,
      (xml \ "player" \ "aura").text.trim.toInt,
      (player_x, player_y))
    for (n <- PLAYER_INVENTORY_SEQUENCE) {
      n.label match {
        case "weapon" =>
          val w = (n \ "weapon").head.child
          player_inventory += Weapon((w \ "name").text,
            (w \ "durability").text.trim.toInt,
            (w \ "maxdurability").text.trim.toInt,
            (w \ "weight").text.trim.toInt,
            (w \ "value").text.trim.toInt,
            (w \ "damage").text.trim.toInt,
            (w \ "minstrength").text.trim.toInt)

        case "healingpotion" =>
          val h = (n \"healingpotion").head.child
          player_inventory += HealingPotion((h \ "name").text,
            (h \ "weight").text.trim.toInt,
            (h \ "value").text.trim.toInt,
            (h \ "usage").text.trim.toInt,
            (h \ "healthbonus").text.trim.toInt)

        case "key" =>
          player_inventory += new Key
        case _ => Unit

      }
    }
    val PLAYER_EQUIPPED_SEQUENCE = (xml \ "player" \ "equipped").head.nonEmptyChildren
    var player_equipped = new ListBuffer[Equipable]
    for (n <- PLAYER_EQUIPPED_SEQUENCE) {
      n match {
        case <weapon>{w}</weapon> =>
          player_equipped += Weapon((w \ "name").text,
            (w \ "durability").text.trim.toInt,
            (w \ "maxdurability").text.trim.toInt,
            (w \ "weight").text.trim.toInt,
            (w \ "value").text.trim.toInt,
            (w \ "damage").text.trim.toInt,
            (w \ "minstrength").text.trim.toInt)
        case _ => Unit

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
  //scalastyle:on
}