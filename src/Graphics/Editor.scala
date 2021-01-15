package Graphics
import game.TileGrid
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import settings.Helper
import settings.GameLevels
import scala.collection.mutable.Buffer
import game.TileType
import game.Entity
import game.gameMap
import org.newdawn.slick.opengl.Texture

//Editor mode
 class Editor () {
  

  
  val grid = new TileGrid
  
  val editorUI = new UI
  var tileMenu: Menu = null
  val saver = new GameLevels
  val grid1 = saver.loadMap("mappi2")
  var index = 0
  val menuBg: Texture = Helper.quickLoadPic("bg_me")
  
  val types = Buffer[TileType]() // array of tileTypes
  setupUI
  
  // Adding types to array
  types += gameMap.grass1
  types += gameMap.dirt
  types += gameMap.water
  
  // Setting menu
  def setupUI = {
    
    editorUI.createMenu("tilePicker", 1280, 100, 192, 960, 2, 0)
    tileMenu = editorUI.getMenu("tilePicker")
    tileMenu.quickAddBut("grass", "grass_tile_2")
    tileMenu.quickAddBut("dirt", "sand_tile")
    tileMenu.quickAddBut("water", "c6lqu2o")
    
  }
  
  def draw = {
    Helper.drawPic(menuBg, 1280, 0, 192, 960)
    grid1.draw
    editorUI.draw
    

  }
  

  def update =  {
    
    draw
  
    // Handle mouse input
   if (Mouse.next()) {
      val mouseClicked = Mouse.isButtonDown(0)
        if (mouseClicked) {
          if (tileMenu.buttonClicked("grass")) {
            index = 0
        } else if (tileMenu.buttonClicked("dirt")) {
            index = 1
        } else  if (tileMenu.buttonClicked("water")) { 
            index = 2
        } else {
            setTile
            
         }
        }
      }
    // Handle keyboard input
    while (Keyboard.next) {
      if (Keyboard.getEventKey == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState) {
          updateIndex
        
      }
      if (Keyboard.getEventKey == Keyboard.KEY_S && Keyboard.getEventKeyState) {
        saver.saveMap("mappi2", grid1)
        
      }
    }
  }
  
  
  // Method that sets tileTypes 
  def setTile = {
   grid1.set(Math.floor(Mouse.getX / 64).toInt,
             Math.floor((Helper.HEIGHT - Mouse.getY - 1) / 64).toInt, 
             types(index))
  }
  
  
  // If we need to change which TileType is selected in editor mod 
  def updateIndex = {
    index += 1
    
    if (index > types.size - 1) {
      index = 0
    }

  }
}