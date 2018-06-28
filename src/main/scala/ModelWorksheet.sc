
import com.google.inject.{Guice, Injector}
import de.htwg.se.DungeonsOfDoom.controller.board.BoardInteraction
import de.htwg.se.DungeonsOfDoom.controller.utility.{JSONState, StateModuleWithJson}
import de.htwg.se.DungeonsOfDoom.model.items.Weapon
import de.htwg.se.DungeonsOfDoom.model.pawns.Player

val player = Player("Herbert", 5, 5, 5, 5, 5, 5, 5, 5, 5)
val weapon = Weapon("Daggerbert Stab", 1, 1, 1, 1, 1, 1)
BoardInteraction.setPlayer(player)

val injector: Injector = Guice.createInjector(new StateModuleWithJson())
val stateManager = injector.getInstance(classOf[JSONState])


stateManager.getState