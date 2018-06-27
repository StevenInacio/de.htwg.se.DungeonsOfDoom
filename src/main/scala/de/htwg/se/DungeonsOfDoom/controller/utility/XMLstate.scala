package de.htwg.se.DungeonsOfDoom.controller.utility

import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.model.board.{Door, Floor, Wall}
import de.htwg.se.DungeonsOfDoom.model.items.{Equipable, HealingPotion, Item, Weapon}

import scala.collection.mutable.ListBuffer
import scala.xml.Node


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
        {
          inventoryToXML(BoardInteraction.player.inventory)
          equippedToXML(BoardInteraction.player.equipped)
        }

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
            {
              inventoryToXML(enemy.inventory)
              equippedToXML(enemy.equipped)
            }


          </enemy>
        }
      }
      </enemys>
      <board>{
        for(row <- BoardInteraction.board.map){
          <row>
            {
              for (field <- row) {
              field match {
                case w: Wall => {
                  <wall></wall>
                }
                case d: Door => {
                  <door>{
                      <doorstate>{d.doorState}</doorstate>
                      <visited>{d.visitedBy}</visited>
                    }</door>
                }
                case f: Floor => {
                  <floor>{
                      inventoryToXML(f.inventory)
                    }
                    <visited>{f.visitedBy}</visited>
                  </floor>
                }
              }
            }
          }</row>
        }
      }</board>
    </state>
  }

  def equippedToXML(list : ListBuffer[Equipable]) : Node = {
    <equipped>{
      for(item <- list){
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
  }
  def inventoryToXML(list : ListBuffer[Item]) : Node = {
    <inventory>{
      for(item <- list){
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
  }
}
