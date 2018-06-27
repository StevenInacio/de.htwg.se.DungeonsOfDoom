package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.items.{HealingPotion, Weapon}


class XMLstate {
  def toXML() : Unit = {
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
        <inventory>{
          for(item <- BoardInteraction.player.inventory){
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
              case h : HealingPotion => <healingpotion>
                                          <name>{h.name}</name>
                                          <weight>{h.weight}</weight>
                                          <value>{h.value}</value>
                                          <usage>{h.usage}</usage>
                                          <healthbonus>{h.healthBonus}</healthbonus>
                                        </healingpotion>
            }
          }
        }</inventory>
        <equipped>{
          for(item <- BoardInteraction.player.equipped){
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

            <inventory>{
              for(item <- enemy.inventory){
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
                  case h : HealingPotion => <healingpotion>
                                              <name>{h.name}</name>
                                              <weight>{h.weight}</weight>
                                              <value>{h.value}</value>
                                              <usage>{h.usage}</usage>
                                              <healthbonus>{h.healthBonus}</healthbonus>
                                            </healingpotion>
                }
              }
              }</inventory>
            <equipped>{
              for(item <- enemy.equipped){
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

          </enemy>
        }
      }
      </enemys>

    //TODO save each field
    //TODO save each fields items

    </state>
  }
}
