package settings


import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.LWJGLException
import org.lwjgl.opengl.GL11._
import org.newdawn.slick.opengl.Texture
import java.io._
import org.newdawn.slick.opengl.TextureLoader
import org.newdawn.slick.util.ResourceLoader


// Helper object that have drawing/booting/texture methods
object Helper {
  
  final val WIDTH = 1472
  final val HEIGHT = 960
  final val TILE_SIZE = 64
  
  // this method creates a game
  def start = {
    
    
    //Gives a name to the display (screen)
    Display.setTitle("TowerDefense")
    
    //If something goes wrong we close the game so that computer won't blow up
    try {
      
      //Creating a display
      Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT))
      Display.create()
      
    } catch { 
      
      case e: LWJGLException => e.printStackTrace()
    }
    
    //Size etc.
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity
    glOrtho(0, WIDTH, HEIGHT, 0, 1, -1)
    glMatrixMode(GL_MODELVIEW)
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND) //enable blending
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA) //does the blend
  }
  
  //Name says it all
  def drawTile(x: Float, y: Float, width: Float, height: Float) = {
    
    glBegin(GL_QUADS)
      glVertex2f(x,y) //Top left
      glVertex2f(x+width,y) //Top right
      glVertex2f(x+width,y+height) //Bottom right
      glVertex2f(x,y+height) //Bottom left
      glEnd
  }
  
  //Giving the tile a texture
  def drawPic(texture: Texture, x: Float, y: Float, width: Float, height: Float) = {
      
    texture.bind()
    glTranslatef(x, y, 0)
    glBegin(GL_QUADS)
      glTexCoord2f(0, 0) //Top left
      glVertex2f(0, 0)
      glTexCoord2f(1, 0) // Top right
      glVertex2f(width, 0)
      glTexCoord2f(1, 1) // Bottom right
      glVertex2f(width, height)
      glTexCoord2f(0, 1) // Bottom left
      glVertex2f(0, height)
      glEnd
      glLoadIdentity
  }
  
  def drawPicRotate(texture: Texture, x: Float, y: Float, width: Float, height: Float, angle: Float) = {
      
    texture.bind()
    glTranslatef(x+ width / 2 , y + height / 2, 0)
    glRotatef(angle, 0, 0, 1)
    glTranslatef( -width / 2, -height / 2, 0)
    glBegin(GL_QUADS)
      glTexCoord2f(0, 0) //Top left
      glVertex2f(0, 0)
      glTexCoord2f(1, 0) // Top right
      glVertex2f(width, 0)
      glTexCoord2f(1, 1) // Bottom right
      glVertex2f(width, height)
      glTexCoord2f(0, 1) // Bottom left
      glVertex2f(0, height)
      glEnd
      glLoadIdentity
  }
  
  //Loading a texture so that it can be used in drawPic
  def loadPic(path: String, file: String) = {
    
    var pic: org.newdawn.slick.opengl.Texture = null
    val in = ResourceLoader.getResourceAsStream(path)
      try {
        pic = TextureLoader.getTexture(file, in)
      } catch {
        case e: IOException => e.printStackTrace()
      }
      pic 
  }
  
  /* This methods helps with drawing in main class.
   * 
   */
  def quickLoadPic(name: String) = {
    var pic: org.newdawn.slick.opengl.Texture = null
    pic = loadPic("src/imgs/" + name + ".png", "PNG")
    pic
  }
  
  def checkCollision(x1: Float, y1: Float, width1: Float, height1: Float,
                     x2: Float, y2: Float, width2: Float, height2: Float ) = {
    if (x1 + width1 > x2 && x1 < x2+ width2 && y1+ height1 > y2 && y1 < y2 + height2) {
      true
    } else {
      false
    }
  }
  
  
}