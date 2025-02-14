package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import util.Vector;

public class Wall {
  private List<Integer> xpoints = null;
  private List<Integer> ypoints = null;
  private Polygon polygon = new Polygon();
  private int nbpoints;

  public Wall(List<Integer> xpoints, List<Integer> ypoints) {
    this.xpoints = xpoints;
    this.ypoints = ypoints;
    this.nbpoints = xpoints.size();
    initPolygon();
  }
  
  public Wall(int[] xpoints, int[] ypoints, int nbpoints) {
    this.nbpoints = nbpoints;
    var tmp = new ArrayList<Integer>();
    for(int i = 0; i < nbpoints; i++) {
      tmp.add(Integer.valueOf(xpoints[i]));
    }
    this.xpoints = tmp;
    tmp = new ArrayList<Integer>();
    for(int i = 0; i < nbpoints; i++) {
      tmp.add(Integer.valueOf(ypoints[i]));
    }
    this.ypoints = tmp;
    initPolygon();
  }
  
  public Wall(Rectangle r) {
	  xpoints = List.of(r.x, r.x+r.width, r.x+r.width, r.x);
	  ypoints = List.of(r.y, r.y, r.y+r.height, r.y+r.height);
	  this.nbpoints = 4;
	  initPolygon();
  }
  
  private void initPolygon() {
    polygon.reset();
    for (int i = 0; i < nbpoints; i++) {
      polygon.addPoint(xpoints.get(i), ypoints.get(i));
     }
  }
  
  // Check collision and return the index of the edge it collided with
  public int getCollidingEdge(Player player) {
	  var box = player.getHitbox();
	  
      for (int i = 0; i < nbpoints; i++) {
          int x1 = xpoints.get(i);
          int y1 = ypoints.get(i);
          int x2 = xpoints.get((i+1)%nbpoints);
          int y2 = ypoints.get((i+1)%nbpoints);

          var edge = new Line2D.Double(x1, y1, x2, y2);
          
          // Check if the movement line intersects this edge
          if (box.intersectsLine(edge)) {
              return i; // Return the index of the colliding edge
          }
      }

      return -1; // No collision detected
  }
  
  public Vector getEdgeNormal(int edgeIndex) {
      if (edgeIndex < 0 || edgeIndex >= nbpoints) {
          throw new IllegalArgumentException("Invalid edge index");
      }

      // Get two consecutive points of the edge
      int x1 = xpoints.get(edgeIndex);
      int y1 = ypoints.get(edgeIndex);
      int x2 = xpoints.get((edgeIndex+1) %nbpoints);
      int y2 = ypoints.get((edgeIndex+1) %nbpoints);

      Vector edge = new Vector(x2 - x1, y2 - y1);

      // Compute normal (perpendicular vector)
      Vector normal = edge.perpendicular();

      return normal.normalized();
  }
  
  public boolean intersects(Rectangle rec) {
    return getPolygon().intersects(rec);
  }
  
  public List<Integer> getXpoints() {
      return xpoints;
  }

  public List<Integer> getYpoints() {
      return ypoints;
  }
  
  public int nbpoints() {
    return nbpoints;
  }
  
  public Polygon getPolygon() {
    return polygon;
  }
  
  @Override
  public String toString() {
    return xpoints + " " + ypoints;
  }
}

