package game

// Simple trait
// If we need to change any of these variables

trait Entity {
  
  def update: Unit
  def draw: Unit
  def setX(x: Int)
  def setY(y: Int)
  def setWidth(width: Int)
  def setheight(height: Int)
}