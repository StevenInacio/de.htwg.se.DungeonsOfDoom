package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.Weapon

case class Player(name: String,
                  var body: Int,
                  var strength: Int,
                  var hardness: Int,
                  var agility: Int,
                  var mobility: Int,
                  var dexterity: Int,
                  var spirit: Int,
                  var mind: Int,
                  var aura: Int,
                  var currentPosition: (Int, Int) = (0, 0)
                 ) extends Pawn {

  override def toString: String = name

  //scalastyle:off
  def apply(name: String,
            body: Int,
            strength: Int,
            hardness: Int,
            agility: Int,
            mobility: Int,
            dexterity: Int,
            spirit: Int,
            mind: Int,
            aura: Int,
            currentPosition: (Int, Int) = (0, 0),
            inventory): Player = {
    val player = Player(name, body, strength, hardness, agility, mobility, dexterity, spirit, mind, aura, currentPosition)
    player.inventory = inventory
    for (x <- equipped) {
      x match {
        case w: Weapon => player.melee_bonus += w.damage
          player.equipped += w
      }
    }
    player
  }

  //scalastyle:on
}