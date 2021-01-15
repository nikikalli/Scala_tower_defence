package game

import scala.util.Random
import org.newdawn.slick.opengl.Texture
import settings._
import org.lwjgl.Sys
import settings._
import scala.collection.mutable.Buffer




/* I had many problems with this class
 * the biggest was that my direction algorithm
 * wouldn't work properly and the reason is that I thought/"coded"
 * it like in c-pogramming language because I had c-course this period
 * and so  I used 2d arrays in c-style
 * that means that my x and y axis are backwards.
 * So if you read this, I'm sorry for strange jumps such as x = y in this code
 * it's due to me thinking in c
 */
  //First enemy
  class EnemySlow(val start: Tile, val grid: TileMap, var width: Int, var height: Int, 
              val texture: Texture, var speed: Float, var HP: Float, val clock: Clock) extends Entity {
    
    // Some variables
    val reward = 5
    // Remember that x is y and y is x
    var x: Float = start.y1  // Enemy takes a starting tile as a parameter  
    var y: Float = start.x1  // So we give our enemy x and y of a starting tile
    var alive = true // is enemy alive?
    val maxHp = HP
    var hiddenHealth = maxHp // For towers
    
    
    // hp textures
    val hp_bl = Helper.quickLoadPic("hp_black")
    val hp_green = Helper.quickLoadPic("hp_green")
    val hp_grey = Helper.quickLoadPic("hp_grey")
    
    /* since first clock will put our enemy out of map 
     * we have to manually calculate the first "tick"
     */
    var first = true 
    
    val list = Buffer[Corner]()  //List of corners
    var currentCorner = 0
    var dir =  (0,0)  // Direction tuple ( x, y ). Used in findDir and Update methods
    dir = findNextDir(start)
    fillList // We calculate path for enemies 
    
   // Enemy getting dmg:ed
    def damage(amount: Int) = {
      HP -= amount
      if (HP <= 0 && alive)  {
        die
      }
    }
    
    def die = {
      alive = false
      if (HP <= 0) {
      Player1.gold += 5 // Gold on death
      }
    }
    
    
    // When end of maze is reached by enemy
    def endReached = {
      Player1.hp = -1
      die
    }
    
    /* Moving enemies
     * in case we start the game for the first time
     * we need to skip the first "tick"  
     */
    def update = {
      
      if (first) {
        first = false 
      } else {
        
        // If enemy is at the end
        if (end) {
          if (currentCorner + 1 == list.size) // Check if there's no more corners in the list
          { 
            endReached
          } else {  
          currentCorner += 1
          }
        } else {
          // Moving forward
          // For example if corner is at direction (1, 0) then y will be 0 and x is being calculated
          // That means that with (1, 0) direction enemy will move in direction of x. 
            x += clock.Delta * list(currentCorner).xDir * speed
            y += clock.Delta * list(currentCorner).yDir * speed
        }
      }
    }
    
    // Check whether enemy is at the end of the path.
    def end = {
      
      var reached = false
      var t: Tile = list(currentCorner).tile
      
      
      // Check if position reached tile within variance of 3 (arbitrary)
      if (x > t.y1 - 3 &&
          x < t.y1 + 3 &&
          y > t.x1 - 3 &&
          y < t.x1 + 3) {
      // If so correct the position of enemy
      reached = true
      x = t.y1
      y = t.x1
      }
      
      reached
    }
    
    def findNextDir(s: Tile) = {
      var direction = (0, 0)
      
      // Possible directions enemy can go
      // X is actually Y and Y is X 
      // That means that in fact getX gives Y coordinate instead of X 
      var up    = grid.get(s.getY, s.getX - 1)
      var right = grid.get(s.getY + 1, s.getX)
      var down  = grid.get(s.getY, s.getX + 1)
      var left  = grid.get(s.getY - 1, s.getX)
      
      //We check if enemy can go to the next tile and also say that enemy can't go back
      if (s.texture == up.texture  && dir._2 != 1) {
        
        direction = (0, -1)
      } else if (s.texture == right.texture  && dir._1 != -1) {
        direction = (1,  0)
       
      } else if (s.texture == down.texture   && dir._2 != -1 ) {
        direction = (0,  1)
        
      } else if (s.texture == left.texture   && dir._1 != 1) {
        direction = (-1, 0)
        
      } else {
        //should never occur
        direction = (2,  2)
        
      }
      
    direction
    
    }
    
    
    def findNextCorner(s: Tile, dir: (Int, Int)) = {
      
      var next:    Tile = null
      var current: Corner = null
      var found = false // if we found the tile we are going to change false to true
      var counter = 1
      
      while (!found) {
        
        if (
            s.getY + dir._1 * counter == grid.tilesWide ||
            s.getX + dir._2 * counter == grid.tilesHigh ||
            s.texture != grid.get(s.getY + dir._1 * counter, 
                                  s.getX + dir._2 * counter).texture) {
          found = true
          counter -= 1
          next = grid.get(s.getY + dir._1 * counter, s.getX + dir._2 * counter)
        }
        
        counter += 1 // Moving counter back 1 to get the tile before a different TileType
      }
      
      current = new Corner(next, dir._1, dir._2)
      current
    }
    
    // To fill list with all corners
    def fillList = {
      
      this.dir = findNextDir(start)
      
      // Add first corner manually based on startTile
      list += findNextCorner(start, findNextDir(start))
      
      
      var counter = 0
      var state = true
      // In this loop we are trying to find all corners so that enemy will know where to go
      while (state) {
         var currentLocations = findNextDir(list(counter).tile)
         
         //Check whether the next direction/corner exists, end after 20 corners
         if (currentLocations._1 == 2 || counter == 20 ) {
           state = false // we are at the end of path
         } else {
           
           
           dir = currentLocations
           
           list += (findNextCorner(list(counter).tile, currentLocations))
           
         }
         
        
        counter += 1
        
      }
      
    }
    //draws an enemy
    def draw = {
      
      var healthPercentage = HP / maxHp 
      Helper.drawPic(texture, x, y, width, height) // Enemy
      Helper.drawPic(hp_grey, x, y - 16, width, 8) // HP background
      Helper.drawPic(hp_green, x, y - 16, Helper.TILE_SIZE * healthPercentage, 8) // Green bar
      Helper.drawPic(hp_bl, x, y - 16, width, 8) // black bar
       
      }
    
    def setX(x: Int) = { this.x = x }
    def setY(y: Int) = { this.y = y }
    def setWidth(width: Int) { this.width = width }
    def setheight(height: Int) = { this.height = height }
      
  }
  