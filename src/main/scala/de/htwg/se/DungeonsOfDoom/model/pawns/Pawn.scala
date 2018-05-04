package de.htwg.se.DungeonsOfDoom.model.pawns

import de.htwg.se.DungeonsOfDoom.model.items.Item

trait Pawn {
  val name : String
  var experience : Integer
  var Körper : Integer
  var Stärke : Integer
  var Härte : Integer
  var Agilität : Integer
  var Bewegung : Integer
  var Geschick : Integer
  var Geist : Integer
  var Verstand : Integer
  var Aura : Integer
  var Panzerungswert : Integer = 0
  var Nahkampfbonus : Integer = 0
  var Fernkampfbonus : Integer = 0
  var Inventory : Array[Item]
  def Lebenskraft : Integer = {
    Körper + Härte + 10
  }
  def Abwehr : Integer = {
    Körper + Härte + Panzerungswert
  }
  def Initiative : Integer = {
    Agilität + Bewegung
  }
  def Schlagen : Integer = {
    Körper + Stärke + Nahkampfbonus
  }
  /*def Schiessen : Integer = {
    Agilität + Geschick + Fernkampfbonus
  }*/
  /*def Zaubern : Integer = {
    Geist + Aura - Panzerungswert
  }
  def Zielzauber : Integer = {
    Geist + Geschick - Panzerungswert
  }*/
}
