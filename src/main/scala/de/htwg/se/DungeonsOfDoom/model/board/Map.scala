package de.htwg.se.DungeonsOfDoom.model.board

class Map(var map: Array[Array[Field]] = Array.ofDim[Field](255,255)) { //scalastyle:ignore
  if (map.isEmpty) {
    map = MapFactory.generate()
  }
}
