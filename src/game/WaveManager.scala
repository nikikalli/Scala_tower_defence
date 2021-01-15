package game

import settings._

//This class manages waves

class WaveManager( val enemy: EnemySlow, 
    var timeBetween: Float, var enemiesPerWave: Int, clock: Clock) {
  
  // What wave is going right now
  var currentWave: Wave = null
  var difficulty = 1.0
  
  var timeSinceLastWave: Float = 0
  var waveNumber: Int = 0
  newWave
  
  
  // If wave is done then spawn a new one
  def update = {
    if (!currentWave.waveDone ) {
      currentWave.update
    } else {
      
      newWave
    }
    
  }
  
  //Make a wave
  def newWave = {
  
    val v = new Wave(enemy, timeBetween, (enemiesPerWave * difficulty).toInt, clock)
    currentWave = v
    waveNumber += 1
    println("Beginning Wave " + waveNumber)
    difficulty *= 1.5 // Updating difficulty
    
    
  }
  
}