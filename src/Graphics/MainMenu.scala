package Graphics

import settings._
import org.newdawn.slick.opengl.Texture
import org.lwjgl.input.Mouse
import game.{Game, BootCl}

import scala.util.control.Breaks
import settings.StateManager1.GameState1
 
// Menu 
class MainMenu() {
  
  
  // Loading background picture
  val background = Helper.quickLoadPic("bg")
  val ui = new UI //Creating user interface
  
  // Adding buttons to the ui
  ui.addButton("Play", "playButton",     Helper.WIDTH / 2 - 128, (Helper.HEIGHT * 0.45f).toInt)
  ui.addButton("Editor", "editorbutton", Helper.WIDTH / 2 - 128, (Helper.HEIGHT * 0.55f).toInt)
  ui.addButton("Quit", "quitButton",     Helper.WIDTH / 2 - 128, (Helper.HEIGHT * 0.65f).toInt)
  
  
  // Makes main menu
  def update = {
    Helper.drawPic(background, 0, 0, 2457, 1024) // We load bigger size since slick has problems with rendering small bgs
    ui.draw
    updateBut
  }
  
  // Makes buttons work
  def updateBut = {
    if (Mouse.isButtonDown(0)) {
      
      // If play is clicked then create a new game
      if (ui.buttonClicked("Play")) {
        
        StateManager1.set(GameState1.GAME1)
      }
      
      // If editor is clicked then go to editor mode
      if (ui.buttonClicked("Editor"))
      {
        
        StateManager1.set(GameState1.EDITOR1)
      }
        
      // If quit is clicked then exit the program
      if (ui.buttonClicked("Quit"))  {
          System.exit(0)
      }
    }
  }
}
