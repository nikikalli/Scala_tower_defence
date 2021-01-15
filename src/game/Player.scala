
package game

import org.lwjgl.input.Mouse
import org.lwjgl.input.Keyboard
import settings._
import scala.collection.mutable.Buffer

//Player object.
//Stats of a player.

// Companion object of Player

object Player1 {
  
  var gold: Int = 0
  var hp: Int = 0
}


class Player(val grid: TileMap, val waveManager: WaveManager, clock: Clock) {
  
  var temp: Tower = null
  var holdingTower = false // To check if chosen tile has a tower on it
  val towers = Buffer[Tower]()
  var leftMouseButtonDown = false
  var rightMouseButtonDown = false
  
  var gold = 0
  var lives = 0
  
  
  
  // To set start gold and lives
  def setup = {
    gold = 1000
    lives = 10
  }
  
  // Gets stored gold from player object
  def getFromObjectPlayer = {
    gold += Player1.gold
    lives += Player1.hp
    Player1.gold = 0
    Player1.hp = 0
  }
  
  // Left click to change chosen tile and right button to change what tileType to use
  def update = {
    
    
    // Update the tower we are holding
    if (holdingTower) {
      temp.setX(getMouseTile.y1)
      temp.setY(getMouseTile.x1)
      temp.draw
    }
    
    for (n <- towers) {
      n.update
      n.draw
      n.updateEnemyList(waveManager.currentWave.enemiesInWave)
    }
    
    // Handle mouse input
    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      placeTower
    }
    
    if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
      println("Right clicked")
    }
    
    // We want that only one click happens
    leftMouseButtonDown = Mouse.isButtonDown(0)
    rightMouseButtonDown = Mouse.isButtonDown(1)
    
    
    
    // Handle keyboard input
    while (Keyboard.next) {
      if (Keyboard.getEventKey == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState) {
        
        
        // speed up time by 20%
        clock.changeMultiplier(0.2f)
        
        
        
      }
      if (Keyboard.getEventKey == Keyboard.KEY_LEFT && Keyboard.getEventKeyState) {
        
        
        // slow time by 20%
        if (clock.multiplier < 0) {
          
          clock.multiplier = 0
        } else {
        clock.changeMultiplier(-0.2f)
        
        }
      }
      
      // For tests
      if (Keyboard.getEventKey == Keyboard.KEY_P && Keyboard.getEventKeyState) {
        println("oi")

      }
    }
  }
  
  // Pick tower from menu
  def pickTower(t: Tower) = {
    temp = t
    holdingTower = true
  }
  
  // Place tower on tile
  def placeTower = {
    
    val currentTile = getMouseTile
    
    if (holdingTower) {
      if (!currentTile.occupied && gold >= temp.price) { 
        towers += temp
        gold -= temp.price
        currentTile.setOccupied
        holdingTower = false
        temp = null
      }
    }
  }
  
  
  def getMouseTile = grid.get(Math.floor(Mouse.getX / 64).toInt, Math.floor((Helper.HEIGHT - Mouse.getY - 1) / 64).toInt)
 
}

