package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Weapon}

case class Enemy(name: String,
                 var body: Integer,
                 var strength: Integer,
                 var hardness: Integer,
                 var agility: Integer,
                 var mobility: Integer,
                 var dexterity: Integer,
                 var spirit: Integer,
                 var mind: Integer,
                 var aura: Integer,
                 var equip: Array[Equipable] = new Array[Equipable](0),
                 var currentPosition: (Integer, Integer) = (0, 0)
                ) extends Pawn {
  for (x <- equip) {
    x match {
      case x: Weapon => this.melee_bonus += x.damage
      // case x : Armor => this.armor_value += x.defense
    }
  }

  override def toString: String = name
}
