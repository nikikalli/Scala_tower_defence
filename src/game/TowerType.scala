package game

import org.newdawn.slick.opengl.Texture
import settings._
import scala.collection.mutable.Buffer

// TowerTypes  

  object TowerType extends Enumeration {
  
  //val helps = new Helper
  
  // With this we can create new types of towers
  class TowerType(val texture: Buffer[Texture], prjectile: ProjectileType.ProjectileType, val dmg: Int, val range: Int, val firingSpeed: Float, val price: Int) 
  
  
  // Two type for now
  val blackCannon = new TowerType(Buffer(Helper.quickLoadPic("tower1N"), Helper.quickLoadPic("weaponBigg")), ProjectileType.cannonBall, 10, 1000, 3, 50)
  val penisCannon = new TowerType(Buffer(Helper.quickLoadPic("bodyN"), Helper.quickLoadPic("weaponBigg")), ProjectileType.cannonBall, 30, 1000, 3, 50)
  val frostCannon = new TowerType(Buffer(Helper.quickLoadPic("frostbody"), Helper.quickLoadPic("weaponFrost")), ProjectileType.iceBall, 30, 500, 3, 100)
  
 }


  
