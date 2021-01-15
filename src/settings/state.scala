package settings

import game.Game;
import Graphics.Editor;
import Graphics.MainMenu;
import game.TileGrid

// StateManager tells where we are (menu, game, editor)
// And switches them, for example if we are at main menu, the state has value main menu
// But when, for example, play button is pressed, the state switches from main menu to game
// And if game is not created then the state calls createGame methods

object StateManager1 {

  // Not the best looking but works
  object GameState1 extends Enumeration {
    type GameState1 = Value
    val MAINMENU1, GAME1, EDITOR1 = Value
  }
  val loader = new GameLevels
    
  // Kinda disgusting line but works                                   
  var gameState: GameState1.GameState1 = GameState1.MAINMENU1
  var mainMenu: MainMenu = null
  var game: Game = null
  var editor: Editor = null
  
  var nextSecond: Long = System.currentTimeMillis() + 1000
  var framesInLastSecond = 0
  var framesInCurrentSecond = 0

  // Updating state of game
  def update1(): Unit = {
    gameState match {
      case GameState1.MAINMENU1 =>
        if (mainMenu == null) 
        mainMenu = new MainMenu
        mainMenu.update

      case GameState1.GAME1 =>
        if (game == null) game = new Game(mapTest.map)
        game.update

      case GameState1.EDITOR1 =>
        if (editor == null) editor = new Editor
        editor.update

    }
    // For tracking fps in console
    var currentTime: Long = System.currentTimeMillis()
    if (currentTime > nextSecond) {
      nextSecond += 1000
      framesInLastSecond = framesInCurrentSecond
      framesInCurrentSecond = 0
    }
    framesInCurrentSecond += 1
    
  }

  // For changing state of the game
  def set(newState: StateManager1.GameState1.GameState1): Unit = {
    gameState = newState
  }
}
