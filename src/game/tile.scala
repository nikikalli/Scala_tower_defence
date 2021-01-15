package game

import org.newdawn.slick.opengl.Texture
import settings.Helper
import scala.collection.mutable.Buffer
import scala.io.Source._
import java.io._
  
  //Type of a tile that is buildable or not
  class  TileType(val nameOfTile: String, val buildOk: Boolean)  {
        //val nameOfTile = name
       // val buildOk = buildable
  
  }
  
  //this class creates types of tiles to be used in future
  object gameMap {
    
  //val grass   = new TileType("Rp9oxyl", true)
  val dirt    = new TileType("sand_tile", false)
  val grass1  = new TileType("grass_tile_2", true)
  val water   = new TileType("c6lqu2o", false)
  val none    = new TileType("c6lqu2o", false) // null type tile

      
  }
  
  
  //Tile that is used in game class
  //Makes creating tiles for game easier in main class 
  class Tile(val y1: Int, val x1: Int, val width: Int, val height: Int, var texture: TileType)  {
    
    // Free Tile is not occupied if buildOk == true
    var occupied = !texture.buildOk
    //val helps = new Helper
    val thisTexture = Helper.quickLoadPic(texture.nameOfTile)
    
    var getX = (x1 / Helper.TILE_SIZE).toInt // Just for the sake of working with small numbers
    var getY = (y1 / Helper.TILE_SIZE).toInt
    
    
    def setOccupied = occupied = true
    
  }
  
  
  //Map of the game
  class TileMap(map: Array[Array[Int]]) {
    
    var tilesWide = map(0).length
    var tilesHigh = map.length
    val tiles = Array.ofDim[Tile](tilesWide, tilesHigh)
    for (i <- 0 until tiles.length) {
      for (j <- 0 until tiles(i).length) {
         map(j)(i) match {
           case 0 => tiles(i)(j) = new Tile(i*Helper.TILE_SIZE, j*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.grass1)
           case 1 => tiles(i)(j) = new Tile(i*Helper.TILE_SIZE, j*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.dirt) 
           case 2 => tiles(i)(j) = new Tile(i*Helper.TILE_SIZE, j*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.water) 
           
         }
        
      }
    }
    //draws the map
    def draw = {
 
      for (i <- 0 until tiles.length) {
        for (j <- 0 until tiles(i).length) {
           
          var oneT = tiles(i)(j)
          Helper.drawPic(oneT.thisTexture, oneT.y1, oneT.x1, oneT.height, oneT.width)
        }
      }
      
    }
    
    //in case we need to update tile
    def set(x: Int, y: Int, tile: TileType) = {
      tiles(x)(y) = new Tile(x*Helper.TILE_SIZE, y*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, tile)
    }
    
    //returns tile at x,y
    def get(x: Int, y: Int) = {
      if (x < tilesWide  && y < tilesHigh && x > - 1 && y > - 1) {
      tiles(x)(y) 
      } else { //in case we are out of map we just return a null type tile so that we won't get error message
        var a = new Tile(0, 0, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.none)
        a
      }
    }
    
  }
  
  // Same as TileMap class but used in editor
  
  class TileGrid {
    
    val tiles = Array.ofDim[Tile](20,15)
    var tilesWide = tiles(0).length
    var tilesHigh = tiles.length
    for (i <- 0 until tiles.length) {
      for (j <- 0 until tiles(i).length) {
        tiles(i)(j) = new Tile(i*Helper.TILE_SIZE, j*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.grass1) 
      }
    } 
    
    def draw = {
      
      for (i <- 0 until tiles.length) {
        for (j <- 0 until tiles(i).length) {
            
          var oneT = tiles(i)(j)
          Helper.drawPic(oneT.thisTexture, oneT.y1, oneT.x1, oneT.height, oneT.width)
        }
      }
      
    }
    
     //in case we need to update tile
     def set(x: Int, y: Int, tile: TileType) = {
       if (x < tilesHigh && y < tilesWide) {
      tiles(x)(y) = new Tile(x*Helper.TILE_SIZE, y*Helper.TILE_SIZE, Helper.TILE_SIZE, Helper.TILE_SIZE, tile)
      }
     }
    
     //returns tile at x,y
     def get(x: Int, y: Int) = {
      if (x < tilesWide  && y < tilesHigh && x > - 1 && y > - 1) {
      tiles(x)(y) 
      } else { //in case we are out of map we just return a null type tile so that we won't get error message
        var a = new Tile(0, 0, Helper.TILE_SIZE, Helper.TILE_SIZE, gameMap.none)
        a
      }
    }
    
  }
  
  


  


