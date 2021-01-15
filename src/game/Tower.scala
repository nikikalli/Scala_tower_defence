package game
import settings._
import org.newdawn.slick.opengl.Texture
import scala.collection.mutable.Buffer


// Abstract class of a tower 
abstract class Tower(towerType: TowerType.TowerType, startTile: Tile, clock: Clock, var enemies: Buffer[EnemySlow]) extends Entity{
  
  // Variables
  val textures = towerType.texture
  val price = towerType.price
  var x = startTile.y1
  var y = startTile.x1
  val damage = towerType.dmg
  var width = startTile.width
  var height = startTile.height
  var target: EnemySlow = null
  val range = towerType.range
  var timeSinceLastShot: Float = 0
  val list = Buffer[Projectile]() // Projectiles
  val firingSpeed = towerType.firingSpeed
  var angle: Float = 0
  
  //var enemies: Buffer[EnemySlow]
  var targeted = false // Is enemy targeted?
  
  
  
  def draw = {
    Helper.drawPic(textures(0), x, y, width, height)
    if (textures.size > 1 ) // If we have rotatable parts
    for (t <- 1 until textures.size)
      Helper.drawPicRotate(textures(t), x, y, width, height, angle)
  }
  

    
    // Get the closest enemy
  def enemyTarget =  {
    
      var closest: EnemySlow = null 
      var closestDistance: Float = 10000 // Orbitary distance
    
      // For each our enemies we check whether enemy is within range 
      // And return closest enemy
      for( n <- enemies) {
        if (isInRange(n) && findDistance(n) < closestDistance && n.hiddenHealth > 0)
        {
          closestDistance = findDistance(n)
          closest = n
        }
      }
    
      if (closest != null)
        targeted = true

      closest
    }
    
    // return true if enemy is within range
  def isInRange(e: EnemySlow) = {
    
    // Distance between enemy and tower
    var xDistance = Math.abs(e.x - x) 
    var yDistance = Math.abs(e.y - y)
    
    if (xDistance < range && yDistance < range)
      true else false
    
  }
  
  //Total number of pixel that the enemy is away from our tower
  def findDistance(e: EnemySlow) = {
    
    var xDistance = Math.abs(e.x - x)
    var yDistance = Math.abs(e.y - y)
    
    xDistance + yDistance
  }
  
  
  //Calculates an angle in which "direction" to shoot
  def calculateAngle = {
    if (target != null) {
    var angleTemp = Math.atan2(target.y - y, target.x - x)
    val a  = Math.toDegrees(angleTemp).toFloat + 90
    a } else 0
  }
  
  
  //Shooting
  def shoot(e: EnemySlow)
  
  
  //Updates the list so that a new target could be acquired
  
  def updateEnemyList(enemieS: Buffer[EnemySlow]) = {
    enemies = enemieS
  }
  
  
    //Update method
  def update = {
    
    //Find a target
    if (!targeted || target.hiddenHealth < 0) {
      target = enemyTarget
    } else {
      // Set angle
      angle = calculateAngle
      
       if (timeSinceLastShot > firingSpeed && target!= null) {
         shoot(target) // If allowed to fire then fire
         timeSinceLastShot = 0
       }
    }
    timeSinceLastShot += clock.Delta // Calculating time since last shot
    
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
  
  def setX(x: Int) = { this.x = x }
  def setY(y: Int) = { this.y = y }
  def setWidth(width: Int) { this.width = width }
  def setheight(height: Int) = { this.height = height }
  
  
  
}
