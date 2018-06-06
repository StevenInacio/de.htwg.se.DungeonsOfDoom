package de.htwg.se.DungeonsOfDoom.model.board

case class Door(var doorState: DoorState.Value) extends Field with Walkable {

  // Nothing Yet
  override def toString: String = doorState match {
    case _@(DoorState.closed | DoorState.locked) => "a closed door"
    case DoorState.open => "an open door"
  }

  def isLocked: Boolean = doorState == DoorState.locked

  def isClosed: Boolean = doorState == DoorState.closed

  def isOpen: Boolean = doorState == DoorState.open

  /*
    *
    * Tries to open the door.
    *
    * @return Some(false) if the door is locked, Some(true) if it succeeded, None if there was nothing to do.
    */
  /*def open(): Option[Boolean] = {
    if (isLocked) {
      Some(false)
    }
    else if (isClosed) {
      doorState = DoorState.open
      Some(true)
    }
    else {
      None
    }
  }

  def close(): Boolean = {
    if (isOpen) {
      doorState = DoorState.closed
      true
    }
    else {
      false
    }
  }
   */
}
