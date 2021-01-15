package Graphics
import org.newdawn.slick.opengl.Texture
import game.Entity



// Buttons that are used in main menu
class Button2(val name: String, val texture: Texture, var x: Int, var y: Int) {
  
  //Size of passed button texture
  var width = texture.getImageWidth 
  var height = texture.getImageHeight
  
  
  
  
  
  def setX(x: Int) = { this.x = x }
  def setY(y: Int) = { this.y = y }
  def setWidth(width: Int) { this.width = width }
  def setheight(height: Int) = { this.height = height }
  
  
}