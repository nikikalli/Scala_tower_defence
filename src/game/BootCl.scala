package game

import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11._



import settings._
import Graphics.MainMenu

 
 // Starting game window
  object main extends App {
      new BootCl
  }
  
  



//Booting 

  class BootCl {
   
  
  //Call methods that initialize OpenGl calls
  Helper.start
   
    //Game is on if loop is going
    // Creating state manager
    var m = StateManager1
    while(!Display.isCloseRequested()) {
      
    
    // updates the state of the game
    m.update1()

      //Updates the game
      Display.update()
      //fps
      Display.sync(60)
    }
    //exit
    Display.destroy()
  }
  
 

  
  
  
  
  
