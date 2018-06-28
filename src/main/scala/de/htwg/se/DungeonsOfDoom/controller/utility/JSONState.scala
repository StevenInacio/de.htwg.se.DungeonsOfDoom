package de.htwg.se.DungeonsOfDoom.controller.utility

import java.io.{BufferedWriter, FileInputStream, FileWriter}

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}
import play.api.libs.json._

import scala.collection.mutable.ListBuffer

class JSONState() extends StateManager {

  implicit val weaponFormat = Json.format[Weapon]

  implicit val potionFormat = Json.format[HealingPotion]

  implicit val keyFormat: Format[Key] = new Format[Key] {
    override def writes(o: Key): JsValue = Json.obj()

    override def reads(json: JsValue): JsResult[Key] = {
      JsSuccess(new Key)
    }
  }

  implicit val enemyWrites: Format[Enemy] = new Format[Enemy] {
    override def writes(o: Enemy): JsValue = {
      Json.obj(
        "Enemy" -> Json.obj(
          "Name" -> o.name,
          "Stats" -> /*name,body,strength,hardness,agility,mobility,dexterity,spirit,mind,aura,equip,currentPosition*/
            Json.obj(
              "body" -> o.body,
              "strength" -> o.strength,
              "hardness" -> o.hardness,
              "agility" -> o.agility,
              "mobility" -> o.mobility,
              "dexterity" -> o.dexterity,
              "spirit" -> o.spirit,
              "mind" -> o.mind,
              "aura" -> o.aura
            ),
          "Current Position" -> o.currentPosition,
          "Equip" -> getEquipJson(o.equipped)
        )
      )
    }

    override def reads(json: JsValue): JsResult[Enemy] = {
      val name = (json \ "Name").as[String]
      val body = (json \ "Stats" \ "body").as[Int]
      val strength = (json \ "Stats" \ "strength").as[Int]
      val hardness = (json \ "Stats" \ "hardness").as[Int]
      val agility = (json \ "Stats" \ "agility").as[Int]
      val mobility = (json \ "Stats" \ "mobility").as[Int]
      val dexterity = (json \ "Stats" \ "dexterity").as[Int]
      val spirit = (json \ "Stats" \ "spirit").as[Int]
      val mind = (json \ "Stats" \ "mind").as[Int]
      val aura = (json \ "Stats" \ "aura").as[Int]
      val currentPosition = (json \ "Current Position").as[(Int, Int)]
      val equip = deserializeEquip((json \ "Equip").as[JsObject])
      JsSuccess(Enemy(name, body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, equip.toArray, currentPosition))
    }
  }

  implicit val playerWrites: Writes[Player] = new Writes[Player] {
    override def writes(o: Player): JsValue = {
      Json.obj(
        "Name" -> o.name,
        "Stats" ->
          Json.obj(
            "body" -> o.body,
            "strength" -> o.strength,
            "hardness" -> o.hardness,
            "agility" -> o.agility,
            "mobility" -> o.mobility,
            "dexterity" -> o.dexterity,
            "spirit" -> o.spirit,
            "mind" -> o.mind,
            "aura" -> o.aura
          ),
        "Current Position" -> o.currentPosition,
        "Equip" -> getEquipJson(o.equipped),
        "Inventory" -> getInventoryJson(o.inventory)
      )
    }
  }

  implicit val floorFormat: Format[Floor] = new Format[Floor] {
    override def writes(o: Floor): JsValue = {
      Json.obj(
        "Floor" -> Json.obj(
          "Inventory" -> getInventoryJson(o.inventory)
        )
      )
    }

    override def reads(json: JsValue): JsResult[Floor] = {
      val inventory = (json \ "Inventory").as[JsObject]
      val floorInventory = deserializeInventory(inventory)
      JsSuccess(Floor(floorInventory))
    }
  }

  implicit val doorFormat: Format[Door] = new Format[Door] {
    override def writes(o: Door): JsValue = {
      var doorState = ""
      if (o.isLocked) {
        doorState = "Locked"
      } else if (o.isClosed) {
        doorState = "Closed"
      } else {
        doorState = "Open"
      }
      Json.obj("Door" -> Json.obj("State" -> doorState))
    }

    override def reads(json: JsValue): JsResult[Door] = {
      val doorState = (json \ "State").as[String]
      doorState match {
        case "Open" => JsSuccess(Door(DoorState.open))
        case "Closed" => JsSuccess(Door(DoorState.closed))
        case "Locked" => JsSuccess(Door(DoorState.locked))
      }
    }
  }

  implicit val wallFormat: Format[Wall] = new Format[Wall] {
    override def writes(o: Wall): JsValue = {
      Json.obj("Wall" -> Json.obj())
    }

    override def reads(json: JsValue): JsResult[Wall] = {
      JsSuccess(Wall())
    }
  }

  override def getState: State = {
    val playerJson = Json.toJson(BoardInteraction.player)
    val boardJson = getBoardJson(BoardInteraction.board)
    val enemyJson = getEnemiesJson(BoardInteraction.enemyList)
    val parsedJson: JsValue = Json.obj(
      "Player" -> playerJson,
      "Map" -> boardJson,
      "Enemies" -> enemyJson)
    State(Json.prettyPrint(parsedJson))
  }

  private[this] def getBoardJson(map: Map): JsValue = {
    val board = map.map
    val spawnPoint = map.playerSpawnPoint
    var boardJson = Json.obj()
    for (x <- board.indices) {
      var row = new ListBuffer[JsObject]
      for (y <- board(x).indices) {
        board(x)(y) match {
          case f: Floor => row += Json.toJson(f).as[JsObject]
          case d: Door => row += Json.toJson(d).as[JsObject]
          case w: Wall => row += Json.toJson(w).as[JsObject]
        }
      }
      boardJson += x.toString -> JsArray(row)
    }
    Json.obj("Spawn Point" -> spawnPoint,
      "Board" -> boardJson)
  }

  private[this] def getEnemiesJson(enemies: ListBuffer[Enemy]): JsValue = {
    var enemyJson = new ListBuffer[JsObject]
    for (x <- enemies) {
      enemyJson += Json.toJson(x).as[JsObject]
    }
    JsArray(enemyJson)
  }

  override def saveState(state: State): Unit = {
    //Write state to File
    val bw: BufferedWriter = new BufferedWriter(new FileWriter("savegame.json"))
    bw.write(state.contents)
    bw.close()
  }

  override def loadState: (Map, Player, ListBuffer[Enemy]) = {
    val stream = new FileInputStream("savegame.json")
    val json = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }
    val player = parseAsPlayer((json \ "Player").get)
    val map = parseAsMap((json \ "Map").get)
    val enemies = parseEnemies((json \ "Enemies").get)
    (map, player, enemies)
  }

  private[this] def parseEnemies(enemies: JsValue): ListBuffer[Enemy] = {
    val obj = enemies.as[JsArray]
    val list = new ListBuffer[Enemy]()
    for (x <- obj.value) {
      list += (x \ "Enemy").as[Enemy]
    }
    list
  }

  private[this] def parseAsPlayer(player: JsValue): Player = {
    val name = (player \ "Name").as[String]
    val body = (player \ "Stats" \ "body").as[Int]
    val strength = (player \ "Stats" \ "strength").as[Int]
    val hardness = (player \ "Stats" \ "hardness").as[Int]
    val agility = (player \ "Stats" \ "agility").as[Int]
    val mobility = (player \ "Stats" \ "mobility").as[Int]
    val dexterity = (player \ "Stats" \ "dexterity").as[Int]
    val spirit = (player \ "Stats" \ "spirit").as[Int]
    val mind = (player \ "Stats" \ "mind").as[Int]
    val aura = (player \ "Stats" \ "aura").as[Int]
    val currentPosition = (player \ "Current Position").as[(Int, Int)]
    val equip = deserializeEquip((player \ "Equip").as[JsObject])
    val inventory = deserializeInventory((player \ "Inventory").as[JsObject])
    val result = Player(name, body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, currentPosition)
    result.inventory = inventory

    for (x <- equip) {
      x match {
        case w: Weapon => result.melee_bonus += w.damage
      }
      result.equipped += x
    }
    result
  }

  private[this] def deserializeEquip(value: JsObject): ListBuffer[Equipable] = {
    val list = new ListBuffer[Equipable]()

    for (x <- value.keys) {
      x match {
        case "Weapon" => list += value(x).as[Weapon]
      }
    }
    list
  }

  private[this] def deserializeInventory(inventory: JsObject): ListBuffer[Item] = {
    val list = new ListBuffer[Item]()

    for (x <- inventory.keys) {
      x match {
        case "Weapon" => list += inventory(x).as[Weapon]
        case "Healing Potion" => list += inventory(x).as[HealingPotion]
        case "Key" => list += inventory(x).as[Key]
      }
    }
    list
  }

  private[this] def parseAsMap(board: JsValue): Map = {
    val map = board.as[JsObject]
    var spawnPoint = (0, 0)
    var parsableBoard = Json.obj()
    var resultBoard = new ListBuffer[Array[Field]]()
    for (x <- map.keys) {
      x match {
        case "Spawn Point" => spawnPoint = map(x).as[(Int, Int)]
        case "Board" => parsableBoard = map(x).as[JsObject]
      }
    }
    for (x <- parsableBoard.keys) {
      resultBoard += deserializeRow(parsableBoard(x).as[JsArray])
    }
    new Map(resultBoard.toArray, spawnPoint)
  }

  private[this] def deserializeRow(row: JsArray): Array[Field] = {
    val list = new ListBuffer[Field]
    for (x <- row.value) {
      val obj = x.as[JsObject]
      for (y <- obj.keys) {
        y match {
          case "Floor" => list += obj(y).as[Floor]
          case "Door" => list += obj(y).as[Door]
          case "Wall" => list += obj(y).as[Wall]
        }
      }
    }
    list.toArray
  }

  private[this] def getInventoryJson(inventory: ListBuffer[Item]): JsValue = {
    var inventoryJson = Json.obj()
    for (x <- inventory) {
      x match {
        case w: Weapon => inventoryJson += "Weapon" -> Json.toJson(w)
        case h: HealingPotion => inventoryJson += "Healing Potion" -> Json.toJson(h)
        case k: Key => inventoryJson += "Key" -> Json.toJson(k)
      }
    }
    inventoryJson
  }

  private[this] def getEquipJson(equip: ListBuffer[Equipable]): JsValue = {
    var equipJson = Json.obj()
    for (x <- equip) {
      x match {
        case w: Weapon => equipJson += "Weapon" -> Json.toJson(w)
      }
    }
    equipJson
  }
}
