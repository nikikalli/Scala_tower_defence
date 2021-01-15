package game

import settings._
import scala.collection.mutable.Buffer
import org.newdawn.slick.opengl.Texture

// Projectile type  

  object ProjectileType extends Enumeration {
  
  
  // With this we can create new types of projectiles
  class ProjectileType(val texture: Texture, val dmg: Int, val speed: Float) 
  
  
  // Two type for now
  val cannonBall = new ProjectileType(Helper.quickLoadPic("bullet"), 30,  1800)
  val iceBall    = new ProjectileType(Helper.quickLoadPic("Water__01"), 30,  1000)
  
  
 }