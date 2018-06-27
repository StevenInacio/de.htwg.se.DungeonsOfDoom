package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, Weapon}

case class Enemy(name: String,
                 var body: Int,
                 var strength: Int,
                 var hardness: Int,
                 var agility: Int,
                 var mobility: Int,
                 var dexterity: Int,
                 var spirit: Int,
                 var mind: Int,
                 var aura: Int,
                 var equip: Array[Equipable] = new Array[Equipable](0),
                 var currentPosition: (Int, Int) = (0, 0)
                ) extends Pawn {
  for (x <- equip) {
    x match {
      case w: Weapon => this.melee_bonus += w.damage
        this.equipped += w
      // case x : Armor => this.armor_value += x.defense
    }
  }
  override def toString: String = name
}
