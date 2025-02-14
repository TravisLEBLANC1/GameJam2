package util;

import java.util.HashMap;
import java.util.List;
public enum Direction {
  NULL(0,0),
  NORTH(0,-1),
  NORTHEAST(1,-1),
  EAST(1, 0),
  SOUTHEAST(1, 1),
  SOUTH(0, 1),
  SOUTHWEST(-1, 1),
  WEST(-1, 0),
  NORTHWEST(-1,-1);
  
  public final int x;
  public final int y;
  private static HashMap<Integer, Direction> map = new HashMap<>();
  private static List<Direction> dirArray = List.of(EAST, NORTHEAST, NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST);
  static {
      var values = Direction.values();
      for (int i = 0; i < values.length; i++) {
          map.put(i, values[i]);
      }
  }

  public static Direction valueOf(int num) {
    if(map.containsKey(num)) {
      return map.get(num);
    }else {
      System.err.println(num + " is not a direction");
      return NULL;
    }
  }
  
  private Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  /* 
   * find the direction that match the better the vector (dx, dy)
   */
  public static Direction closest(int dx, int dy) {
    if (dx == 0 && dy == 0) return NULL;
    if (dx == 0 && dy >0 ) return NORTH;
    if (dx == 0 && dy <0 ) return SOUTH;
    // Calculate the angle in radians
    double angle = Math.atan2(dy, dx);
    // Map the angle to one of the 8 directions (each covering π/4 radians)
    int dirIndex = ((int)Math.round((angle )/ (Math.PI / 4))+8) % 8;
    
    
    return dirArray.get(dirIndex);
  }
}
