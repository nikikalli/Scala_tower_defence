package game
import settings._
import Graphics.UI
import org.lwjgl.input.Mouse
import Graphics.Button2
import Graphics.Menu
import org.newdawn.slick.opengl.Texture





class Game(map: Array[Array[Int]]) {
  
  
  var loseText = "You lost, try win next time."
  val gameUI = new UI
  val clock = new Clock
  val grid = new TileMap(map)
  var towerMenu: Menu = null
  val en = new EnemySlow(grid.get(0, 7), grid, Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.quickLoadPic("wihu3"), 100, 100, clock)
  var timer = 0
  val waveManager = new WaveManager(en, 2, 2, clock)
  val player = new Player(grid, waveManager, clock)
  val menuBG: Texture = Helper.quickLoadPic("bg2m")
  
  player.setup // sets player's lives and gold
  setupUI
  
  
  def update = {
    Helper.drawPic(menuBG, 1280, 0, 192, 960)
    clock.update //setting "time"
    grid.draw //Drawing the map
   
    waveManager.update //Drawing enemies
    player.update
    player.getFromObjectPlayer
    updateUI
    
    if (player.lives <= 0 ) { // If player dead then print loseText and start counting
      timer += 1
      gameUI.drawString(650, 400, loseText)
    }
    
    if (timer == 600) { // End game when 10 sec has passed
      System.exit(0)
    }
  }
  
  def updateUI = {
    gameUI.draw
    gameUI.drawString(1310, 250, "Lives: " + player.lives)
    gameUI.drawString(1310, 300, "Gold: " + player.gold)
    gameUI.drawString(1310, 350, "FPS: " + StateManager1.framesInLastSecond)
    gameUI.drawString(1340, 600, "Wave " + waveManager.waveNumber)
    
    
    if (Mouse.next()) {
      val mouseClicked = Mouse.isButtonDown(0)
        if (mouseClicked) {
          if (gameUI.getMenu("towerPicker").buttonClicked("tower")) {
            player.pickTower(new TowerNew2(TowerType.frostCannon, grid.get(0, 0), clock, waveManager.currentWave.enemiesInWave))
        }
          if (gameUI.getMenu("towerPicker").buttonClicked("tower2")) {
            player.pickTower(new TowerNew(TowerType.penisCannon, grid.get(0, 0), clock, waveManager.currentWave.enemiesInWave))
          }
      }
    }
  }
  def setupUI = {
    
    gameUI.createMenu("towerPicker", 1280, 100, 192, 960, 2, 0)
    
    towerMenu = gameUI.getMenu("towerPicker")
    towerMenu.quickAddBut("tower", "tower1N")
    towerMenu.quickAddBut("tower2", "fullf")
    
    
  }
  
}