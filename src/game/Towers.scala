/*package game
import org.newdawn.slick.opengl.Texture
import settings._
import scala.collection.mutable.Buffer


//Tower class

class Cannon(val start: Tile, range: Int, val dmg: Int, 
             val textureBase: Texture, val cl: Clock, 
             var enemies: Buffer[EnemySlow]) extends Entity {
  
  var x = start.y1 // x and y
  var y = start.x1
  
  var timeSinceLastShot: Float = 0
  
  var firingSpeed = 3
  
  var width = start.width
  var height= start.height
  
  val cannonText: Texture = Helper.quickLoadPic("weapon1") //texture of a "weapon"
  
  var target: EnemySlow = null // Current target
  var targeted = false // Is enemy targeted
  
  var angle: Float = 0 // Angle/Direction of "weapon"
  
  val list = Buffer[Projectile]() // Projectiles
  
  
  
  //Updates the list so that a new target could be acquired
  
  def updateEnemyList(enemieS: Buffer[EnemySlow]) = {
    enemies = enemieS
  }
  
  
  //Update method
  def update = {
    
    //Find a target
    if (!targeted) {
      target = enemyTarget
    } else {
      // Set angle
      angle = calculateAngle
      
       if (timeSinceLastShot > firingSpeed && target!= null) {
         shoot // If allowed to fire then fire
       }
    }
    timeSinceLastShot += cl.Delta // Calculating time since last shot
    
    // If our target doesn't exist or dead then we need to find a new target
    if (target == null || target.alive == false) {
      targeted = false
    }
    
    
    // Update angles/directions in which cannon will shoot
    for(n <- list) {
      n.update
    }
    
    
    // Set angle
    angle = calculateAngle
    draw
  }
  
  
  // return true if enemy is within range
  def isInRange(e: EnemySlow) = {
    
    // Distance between enemy and tower
    var xDistance = Math.abs(e.x - x) 
    var yDistance = Math.abs(e.y - y)
    
    if (xDistance < range && yDistance < range)
      true else false
    
  }
  
  // Get the closest enemy
  def enemyTarget =  {
    
    var closest: EnemySlow = null 
    var closestDistance: Float = 10000 // Orbitary distance
    
    // For each our enemies we check whether enemy is within range 
    // And return closest enemy
    for( n <- enemies) {
      if (isInRange(n) && findDistance(n) < closestDistance && n.alive)
      {
        closestDistance = findDistance(n)
        closest = n
      }
    }
    
    if (closest != null)
        targeted = true

    closest
  }
  
  
  //Total number of pixel away that the enemy is from our tower
  def findDistance(e: EnemySlow) = {
    
    var xDistance = Math.abs(e.x - x)
    var yDistance = Math.abs(e.y - y)
    
    xDistance + yDistance
  }
  
  //Calculates angle in which to shoot
  def calculateAngle = {
    if (target != null) {
    var angleTemp = Math.atan2(target.y - y, target.x - x)
    val a  = Math.toDegrees(angleTemp).toFloat - 180
    a } else 0
  }
  
  //Shooting
  def shoot = {
    
    timeSinceLastShot = 0
    //val proj = new AbnormalProjectile(helps.quickLoadPic("bullet"), target, x + helps.TILE_SIZE / 2 - helps.TILE_SIZE / 4, 
      //                                                              y + helps.TILE_SIZE / 2 - helps.TILE_SIZE / 4,
        //                                                   1500, 10, cl, helps.TILE_SIZE / 2, helps.TILE_SIZE / 2)
    //list += proj
  
  }
  //Drawing
  def draw = {
    Helper.drawPic(textureBase, x, y, width, height)
    Helper.drawPicRotate(cannonText, x, y, width, height, angle)
  }
  
  def setX(x: Int) = { this.x = x }
  def setY(y: Int) = { this.y = y }
  def setWidth(width: Int) { this.width = width }
  def setheight(height: Int) = { this.height = height }
}*/