package game

import settings._
import scala.collection.mutable.Buffer


// Tower that slows enemies
class TowerNew2(towerType: TowerType.TowerType, startTile: Tile, clock: Clock, enemies: Buffer[EnemySlow]) extends Tower(towerType, startTile, clock, enemies) {
  

  def shoot(target: EnemySlow) = {
    var a = new AbnormalProjectile(ProjectileType.iceBall, target, x + Helper.TILE_SIZE / 2 - Helper.TILE_SIZE / 4, 
                                                                   y + Helper.TILE_SIZE / 2 - Helper.TILE_SIZE / 4,
                                                                clock, Helper.TILE_SIZE / 2,  Helper.TILE_SIZE / 2)
    list += a
    target.hiddenHealth -= towerType.dmg
  }
  
  
}