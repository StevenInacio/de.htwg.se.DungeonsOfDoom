package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.mutable.ListBuffer

class JSONstate() extends StateManager {

  implicit val weaponFormat = Json.format[Weapon]
  implicit val potionFormat = Json.format[HealingPotion]
  implicit val keyFormat = new Format[Key] {
    override def writes(o: Key): JsValue = "Key".asInstanceOf[JsValue]

    override def reads(json: JsValue): JsResult[Key] = {
      JsSuccess(new Key)
    }
  }

  implicit val enemyWrites = new Format[Enemy] {
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
      val equip = (json \ "Equip").as[List[JsValue]]
      val equipList = Array[Equipable]()
      for (x <- equip) {
        x.toString() match {
          case "Weapon" => equipList.toBuffer += x.as[Weapon]
        }
      }
      JsSuccess(Enemy(name, body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, equipList, currentPosition))
    }
  }

  implicit val playerFormat = new Format[Player] {
    override def writes(o: Player): JsValue = {
      Json.obj(
        "Player" -> Json.obj(
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
          "Equip" -> getEquipJson(o.equipped),
          "Inventory" -> getInventoryJson(o.inventory)
        )
      )
    }

    override def reads(json: JsValue): JsResult[Player] = {
      implicit lazy val equipRead: Reads[Weapon] = (__ \ "Weapon").read[Weapon]
      implicit lazy val inventoryRead = (__ \ "Weapon").readNullable[Weapon] ~
        (__ \ "Healing Potion").readNullable[HealingPotion] ~
        (__ \ "Key").readNullable[Key]
      implicit val playerRead = ((__ \ "Player" \ "Name").read[String] ~
        (__ \ "Player" \ "Stats" \ "body").read[Int] ~
        (__ \ "Player" \ "Stats" \ "strength").read[Int] ~
        (__ \ "Player" \ "Stats" \ "hardness").read[Int] ~
        (__ \ "Player" \ "Stats" \ "agility").read[Int] ~
        (__ \ "Player" \ "Stats" \ "mobility").read[Int] ~
        (__ \ "Player" \ "Stats" \ "dexterity").read[Int] ~
        (__ \ "Player" \ "Stats" \ "spirit").read[Int] ~
        (__ \ "Player" \ "Stats" \ "mind").read[Int] ~
        (__ \ "Player" \ "Stats" \ "aura").read[Int] ~
        (__ \ "Player" \ "Current Position").read[(Int, Int)] ~
        (__ \ "Player" \ "Equip").lazyReadNullable(Reads.seq[Weapon](equipRead)) ~
        (__ \ "Player" \ "Inventory").lazyReadNullable(Reads.seq[Item](inventoryRead))) (Player.apply _)
      json.validate[Player](playerRead)
      /*      val strength = (json \ "Stats" \ "strength").get.as[Int]
            val hardness = (json \ "Stats" \ "hardness").get.as[Int]
            val agility = (json \ "Stats" \ "agility").get.as[Int]
            val mobility = (json \ "Stats" \ "mobility").get.as[Int]
            val dexterity = (json \ "Stats" \ "dexterity").get.as[Int]
            val spirit = (json \ "Stats" \ "spirit").get.as[Int]
            val mind = (json \ "Stats" \ "mind").get.as[Int]
            val aura = (json \ "Stats" \ "aura").get.as[Int]
            val currentPosition = (json \ "Current Position").get.as[(Int, Int)]
            val equip = (json \ "Equip").as[List[JsValue]]
            val inventory = (json \ "Inventory").as[List[JsValue]]
      //      val player = Player(name.toString(), body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, currentPosition)

            for (x <- equip) {
              println(x.toString())
              x.toString() match {
                case "Weapon" => {
                  val w = x.as[Weapon]
                  player.melee_bonus += w.damage
                  player.equipped += w
                }
                // case a : Armor => player.armor_value += a.defense
                //   player.equipped += a
              }
            }
            JsSuccess(player)*/
    }
  }

  implicit val floorFormat = new Format[Floor] {
    override def writes(o: Floor): JsValue = {
      Json.obj(
        "Floor" -> Json.obj(
          "Inventory" -> getInventoryJson(o.inventory)
        )
      )
    }

    override def reads(json: JsValue): JsResult[Floor] = {
      val inventory = (json \ "Floor" \ "Inventory").as[List[JsValue]]
      var floorInventory = new ListBuffer[Item]
      for (x <- inventory) {
        x.toString() match {
          case "Weapon" => floorInventory += x.as[Weapon]
          case "Healing Potion" => floorInventory += x.as[HealingPotion]
          case "Key" => floorInventory += x.as[Key]
        }
      }
      JsSuccess(Floor(floorInventory))
    }
  }

  implicit val doorFormat = new Format[Door] {
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
      val state = (json \ "Door" \ "State").as[String]
      state match {
        case "Open" => JsSuccess(Door(DoorState.open))
        case "Close" => JsSuccess(Door(DoorState.closed))
        case "Locked" => JsSuccess(Door(DoorState.locked))
      }
    }
  }

  override def getState: State = {

    val player = Player("Herbert", 5, 5, 5, 5, 5, 5, 5, 5, 5, (1, 2))
    val weapon = Weapon("Daggerbert Stab", 1, 1, 1, 1, 1, 1)
    player.equipped += weapon
    player.melee_bonus += weapon.damage

    val playerJson = Json.toJson(player)

    val playerFromJson = playerJson.as[Player]

    println(playerFromJson.name)


    val parsedJsonString: String = playerJson.toString()
    State(parsedJsonString)
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

  override def saveState(state: State): Unit = {
    //Write state to File
  }

  override def loadState: (Map, Player, ListBuffer[Enemy]) = {
    //Load and parse state from file
    (new Map(), Player("", 0, 0, 0, 0, 0, 0, 0, 0, 0), new ListBuffer[Enemy]())
  }
}
