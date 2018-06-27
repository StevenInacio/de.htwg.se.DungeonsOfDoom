package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction


class XMLstate {
  def toXML() : Unit = {
    <state>
      <player>
        <position>
          {BoardInteraction.player.currentPosition}
        </position>
      </player>
    //TODO save players items
    //TODO save each enemy (BoardInteraction.enemyList)
    //TODO save each enemys items
    //TODO save each field
    //TODO save each fields items
    </state>
  }
}
