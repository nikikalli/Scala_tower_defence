package Graphics


import scala.collection.mutable.Buffer
import org.newdawn.slick.opengl.Texture
import settings._
import org.lwjgl.input.Mouse
import org.newdawn.slick.TrueTypeFont
import java.awt.Font


// User interface class
class UI {
  
  // Default font
  val javaFont = new Font("Times New Roman", Font.BOLD, 24)
  
  // Slick font
  val font = new TrueTypeFont(javaFont, false) 
  
  // List of buttons
  val buttonList = Buffer[Button2]()
  
  // List of menus
  val menuList = Buffer[Menu]()
  
  
  // Add a new button
  def addButton(name: String, textureName: String, x: Int, y: Int) = {
    val button = new Button2(name, Helper.quickLoadPic(textureName), x, y)
    buttonList += button
  }
  // Is button clicked?
  def buttonClicked(nameB: String): Boolean = {
    val oneButton = getButton(nameB)
    
    var mouseY = Helper.HEIGHT - Mouse.getY - 1
    // Checking whether mouse is on top of a button
    if (Mouse.getX > oneButton.x && Mouse.getX < oneButton.x + oneButton.width &&
        mouseY > oneButton.y && mouseY < oneButton.y + oneButton.height) {
       return true
    }
     return false
  }
  
  // gets button, used in buttonCliked
  def getButton(nameB: String): Button2 = {
    for (n <- buttonList) {
      if (n.name.equals(nameB)) {
         return n
      }
    }
     return null
  }
  
  
  //  Creates menu
  def createMenu(name: String, x: Int, y: Int, width: Int, height: Int, oWidth: Int, oHeight: Int) = {
    val a = new Menu(name, x, y, width, height, oWidth, oHeight)
    menuList += a
  }
  
  def getMenu(name: String): Menu = {
    for (m <- menuList) {
      if (name == m.name) { // If name exists then return button 
        return m
      }
    }
    return null // else return null
  }
  
  
  // Drawing text
  def drawString(x: Int, y: Int, text: String) = {
    font.drawString(x, y, text)
  }
  
  
  // Drawing
  def draw = {
    
    for (n <- buttonList) {
      Helper.drawPic(n.texture, n.x , n.y , n.width, n.height)
    }
   
    for (m <- menuList) {
      m.draw
    }
  } 
}


  // In game menu for buttons
  // oWidth and oHeight are amount of options wide/high the menu is
class Menu(val name: String, var x: Int, var y: Int, var width: Int, var height: Int, var oWidth: Int, var oHeight: Int) {
  
  var buttonAmount = 0
  val menuButtons = Buffer[Button2]()
  val padding = (width - oWidth * Helper.TILE_SIZE) / (oWidth + 1)
  
  
  // Adding button
  def addButtonToMenu(b: Button2) = {
    setButton(b)
  }
  
  // Gets button, used in buttonCliked
  def getButton(nameB: String): Button2 = {
    for (n <- menuButtons) {
      if (n.name.equals(nameB)) {
        return n
      }
    }
    return null
  }
  
  // Method to simplify adding buttons
  def quickAddBut(name: String, butName: String) = {
    val button = new Button2(name, Helper.quickLoadPic(butName), 0, 0)
    setButton(button)
  }
  
  def setButton(b: Button2) = {
    
    // In case we add new buttons we need to make more space/move old ones to the left
    // In other words this makes adding buttons easier
    // But if we reached the end of option screen then we move buttons down
    if (oWidth != 0) {
      b.setY(y + buttonAmount / oWidth  * Helper.TILE_SIZE)
    }
    b.setX(x + buttonAmount % 2 * (padding + Helper.TILE_SIZE) + padding) // Math for x
    buttonAmount += 1 // update amount
    menuButtons += b
    
  }
  
  
  // Is button clicked?
  def buttonClicked(nameB: String): Boolean = {
    val oneButton = getButton(nameB)
    
    var mouseY = Helper.HEIGHT - Mouse.getY - 1
    // Checking whether mouse is on top of a button
    if (Mouse.getX > oneButton.x && Mouse.getX < oneButton.x + oneButton.width &&
        mouseY > oneButton.y && mouseY < oneButton.y + oneButton.height) {
      return true
    }
    return false
  }
  
  def draw = {
    for (b <- menuButtons) {
      Helper.drawPic(b.texture, b.x, b.y, b.width, b.height)
    }
  }
  
  
}