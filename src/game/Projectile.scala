package game

import org.newdawn.slick.opengl.Texture
import settings._

// Abstract class projectile

abstract class Projectile(texture: ProjectileType.ProjectileType, target: EnemySlow, var x: Float, var y: Float, cl: Clock, var width: Float, var height: Float) extends Entity{
  
  
  // Movement of projectiles
  var xVelocity = 0f 
  var yVelocity = 0f
  var alive = true
  
  calculateDirection
  
  // Algorithm that calculates directions of projectiles
  def calculateDirection = {
    
    var totalAllowedMovement = 1.0f // Maximun movement
    var xDistanceFromTarget = Math.abs(target.x - x - Helper.TILE_SIZE / 4 + Helper.TILE_SIZE / 2) // Distance from target to us on x axis /**/ Adding and dividing is so that our projectile flies to the middle of tile 
    var yDistanceFromTarget = Math.abs(target.y - y - Helper.TILE_SIZE / 4 + Helper.TILE_SIZE / 2) // Distance from target to us on y axis
    var totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget
    var xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget // calculate how much percent of the whole movement is on x axis
    xVelocity = xPercentOfMovement
    yVelocity = totalAllowedMovement - xPercentOfMovement // Sum of xVelocity and yVelocity is 1.0f
    
    if (target.x < x) {
      xVelocity *= -1
    }
    if (target.y < y) {
      yVelocity *= -1
    }
  }
  
  
  def doDmg = {
      target.damage(texture.dmg)
      alive = false
  }
  
  // Moving projectile
  def update = {
    if (alive) {
      
    x += xVelocity * texture.speed * cl.Delta
    y += yVelocity * texture.speed * cl.Delta
    if ( Helper.checkCollision(x, y, width, height, target.x, target.y, target.width, target.height) )
     { 
      target.damage(texture.dmg)
      /*alive = false*/ doDmg
     }
    draw
    }
  }
  
  //drawing projectile
  def draw = Helper.drawPic(texture.texture, x, y, width, height)
  
  def setX(x: Int) = { this.x = x }
  def setY(y: Int) = { this.y = y }
  def setWidth(width: Int) { this.width = width }
  def setheight(height: Int) = { this.height = height }
  
}


// Water/Frost projectile
class AbnormalProjectile(texture: ProjectileType.ProjectileType, target: EnemySlow, x: Float, y: Float, cl: Clock, width: Float, height: Float) 
extends Projectile(texture, target, x, y, cl, width, height) {
  
  
  
  
  override def doDmg = {
    target.speed = 4f // Slows enemy
    alive = false
    
  }
  
}

// Basic projectile
class BallProjectile(texture: ProjectileType.ProjectileType, target: EnemySlow, x: Float, y: Float,  cl: Clock, width: Float, height: Float) 
extends Projectile(texture, target, x, y, cl, width, height) {
  
  override def doDmg = {
    alive = false
  

  }

}



