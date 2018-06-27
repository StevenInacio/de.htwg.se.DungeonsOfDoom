package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board._
import de.htwg.se.DungeonsOfDoom.model.items._
import de.htwg.se.DungeonsOfDoom.model.pawns.{Enemy, Player}
import play.api.libs.json._

import scala.collection.mutable.ListBuffer

class JSONstate() extends StateManager {
  implicit val enemyWrites = new Format[Enemy] {
    override def writes(o: Enemy): JsValue = Json.obj(
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
        "Equip" -> Json.toJson(o.equipped)
      )
    )

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
      val equip = (json \ "Equip").as[Array[Equipable]]
      JsSuccess(Enemy(name, body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, equip, currentPosition))
    }
  }

  override def getState: State = {
    implicit val playerWrites = Json.writes[Player]
    implicit val weaponWrites = Json.writes[Weapon]
    implicit val potionWrites = Json.writes[HealingPotion]
    implicit val keyWrites = Json.writes[Key]
    implicit val wallWrites = Json.writes[Wall]
    val playerJson = Json.obj(
      "Player" -> (
        "Status" -> Json.toJson[Player](BoardInteraction.player),
        "Inventory" -> Json.toJson(BoardInteraction.player.inventory),
        "Equipped" -> Json.toJson(BoardInteraction.player.equipped)
      )
    )
    val parsedJsonString: String = ""
    State(parsedJsonString)
  }

  override def saveState(state: State): Unit = {
    //Write state to File
  }

  override def loadState: (Map, Player, ListBuffer[Enemy]) = {
    //Load and parse state from file
    (new Map(), Player("", 0, 0, 0, 0, 0, 0, 0, 0, 0), new ListBuffer[Enemy]())
  }
}
