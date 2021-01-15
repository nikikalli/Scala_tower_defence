package settings

import org.lwjgl.Sys

// Time class
// Should have been object but it is what it is.
class Clock {
  
  var paused = false
  var totalTime: Float = 0
  var deltaTime: Float = 0
  var multiplier: Float = 1
  var lastFrame: Long = getTime
  
  // Time
  def getTime: Long  = Sys.getTime * 1000 / Sys.getTimerResolution
  
  //Used for moving entities
  //DeltaTime is the time between right now and the last update to the game
  def getDelta: Float = {
    
    var currentTime = getTime
    var delta =  (currentTime - lastFrame).toInt 
    // Every time this updates we are setting this current deltaTime between now and the last frame
    // and then we are setting lastFrame to right now
    lastFrame = getTime //
    // That means that in the feature this will be equal to deltaTime and the next update
    if (delta * 0.001f > 0.05f) {
      0.05f
    } else {
    delta * 0.001f
    }
    
  }
  
  //For out pause method
  def Delta: Float = if (paused) 0 else deltaTime * multiplier
  
  
  //Updates the game
  def update = {
    
    deltaTime = getDelta
    totalTime += deltaTime
    
  }
  
  
  def changeMultiplier(number: Float) = {
    //more than 7 will create perf problems
    if (multiplier + number < -1 || multiplier + number > 7)
    {
    } else { 
      
      multiplier += number }
  }
  
  def pause = { paused = !paused }
  
  
  
}