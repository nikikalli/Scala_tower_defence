package settings
import game.TileGrid
import game.Tile
import game.TileType
import game.TileMap
import java.io._
import game.gameMap


// IO reader class
class GameLevels {
  
  // Save maps
  def saveMap(mapName: String, grid: TileGrid) = {
    
    var string = "" // We are saving our "tiles" into string
    
    for (i <- 0 until 15) {
      for (j <- 0 until 20) {
      
      string += tileID(grid.get(j, i))
      } 
    }
    // Have to catch exceptions if no file/name etc. found
    try {
      
    val file = new File(mapName)
    val fw = new FileWriter(file)
    val bw = new BufferedWriter(fw)
    bw.write(string)
    bw.close()
    
    } catch {
      case e: Exception => e.printStackTrace()
    }
  
  }
  
  // To save the map we need to know which tile (tileType) is at specific x and y
  def getTileType(ID: String): TileType = {
    
    var tileT: TileType = null
    
    val grass = "grass_tile_2"
    val water = "c6lqu2o"
    val dirt = "sand_tile"
    
    
    ID match {
      case "0" => tileT = new TileType(grass, true)
      case "1" => tileT = new TileType(dirt, false)
      case "2" => tileT = new TileType(water, false)
    }
    
    return tileT
  }
  
  // Loading maps
  def loadMap(name: String): TileGrid = {
    var grid = new TileGrid
    
    try {
      
      val br = new BufferedReader(new FileReader(name))
      
      var data = br.readLine()
      
      for { 
          i <- 0 until 15
          j <- 0 until 20 } {
            grid.set(j, i, getTileType(data.substring(i * grid.tilesHigh + j, i * grid.tilesHigh + j + 1)))
    }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    
          
          return grid
  }
  
  // For saving maps we need to know names of tile types
  
  def tileID(t: Tile): String = {
   
   var ID: String = ""
    
    // Could have done with objects but it is what it is
    val grass = "grass_tile_2"
    val water = "c6lqu2o"
    val dirt = "sand_tile"
    //val types = new gameMap
    
    println(t.texture.nameOfTile)
    t.texture.nameOfTile match {
      case gameMap.grass1.nameOfTile  => ID = "0"
      case gameMap.dirt.nameOfTile    => ID = "1"
      case gameMap.water.nameOfTile   => ID = "2"
      case gameMap.none.nameOfTile    => ID = "3"
      case _ => ID = "1"
    }
    
    ID
  }
  
  
  
  
}