package de.htwg.se.DungeonsOfDoom.model.pawns

trait Spielfigur {
  val name : String
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
  def Lebenskraft : Integer = {
    Körper + Härte + 10
  }
  def Abwehr : Integer = {
    Körper + Härte + Panzerungswert
  }
}
