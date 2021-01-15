
package game
import scala.collection.mutable.Buffer
import settings._
import org.newdawn.slick.opengl.Texture

// Wave class 

class Wave(enemyType: EnemySlow, spawnTime: Float, enemiesPerWave: Int, cl: Clock) {

  
  // Time since last spawn
  var lastSpawn: Float = 0
  // Array of enemies
  var enemiesInWave = Buffer[EnemySlow]()
  var waveDone = false // wave done if all enemies are dead or reached the end
  spawn // Spawns this wave

  // Updating status of a wave
  def update = {
    var allDead = true // All enemies are dead?
    if (enemiesInWave.size < enemiesPerWave) {
        lastSpawn += cl.Delta
        if (lastSpawn > spawnTime) {
          spawn
          lastSpawn = 0
        }
    }
    
    // Spawning, drawing and moving enemies 
    for (n <- enemiesInWave) {
      if (n.alive) {
        allDead = false
        n.update
        n.draw
      } 
    }
    if (allDead) {
      enemiesInWave = Buffer[EnemySlow]() // Override the list of enemies so that towers won't target dead enemies
      waveDone = true
    }
  }

  def spawn = {
    val i = new EnemySlow(enemyType.start, enemyType.grid, Helper.TILE_SIZE, Helper.TILE_SIZE, enemyType.texture, 50, 80, cl)
    enemiesInWave += i
  }
}