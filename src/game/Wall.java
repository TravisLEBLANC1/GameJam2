package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import util.Vector;

public class Wall {
	private final double EPSILON = 0.5;
	public static final int NOTFOUND = -1;
	public static final int CORNER = -2;
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
	  Point2D  closestPoint = null;
	  double minDistance = 0;
	  var p2 = player.getHitbox();
	  var playerPoint = player.getPos().toPoint2d();
	  int resultEdge = NOTFOUND;
	  
      for (int i = 0; i < polygon.npoints; i++) {
          int next1 = (i + 1) % polygon.npoints; // Wrap around to the first point
          Line2D.Double edge1 = new Line2D.Double(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[next1], polygon.ypoints[next1]);

          for (int j = 0; j < p2.npoints; j++) {
              int next2 = (j + 1) % p2.npoints;
              Line2D.Double edge2 = new Line2D.Double(p2.xpoints[j], p2.ypoints[j], p2.xpoints[next2], p2.ypoints[next2]);
              
              if (edge1.intersectsLine(edge2)) {
            	  Point2D point = getIntersection(edge1, edge2);
            	  if (closestPoint == null) {
            		  closestPoint = point;
            		  minDistance = point.distance(playerPoint);
            		  resultEdge = i;
            	  }else {
            		  double distance = point.distance(playerPoint);
            		  
            		  if (i != resultEdge && Math.abs(distance - minDistance) < EPSILON) {
            			  // System.out.println("btw " + resultEdge + "  " + i  + " " + Math.abs(distance - minDistance));
            			  resultEdge = CORNER;
            		  }else if (distance < minDistance) {
            			  // System.out.println(distance + " < " + minDistance + " so "+ polygon.xpoints[i] + " and " + polygon.ypoints[i]);
	                      minDistance = distance;
	                      resultEdge = i;
	                      
		              }
            		  
            	  }
              }
          }
      }
      
      return resultEdge;
  }
  
  public static Point2D getIntersection(final Line2D.Double line1, final Line2D.Double line2) {

      final double x1,y1, x2,y2, x3,y3, x4,y4;
      x1 = line1.x1; y1 = line1.y1; x2 = line1.x2; y2 = line1.y2;
      x3 = line2.x1; y3 = line2.y1; x4 = line2.x2; y4 = line2.y2;
      final double x = (
              (x2 - x1)*(x3*y4 - x4*y3) - (x4 - x3)*(x1*y2 - x2*y1)
              ) /
              (
              (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
              );
      final double y = (
              (y3 - y4)*(x1*y2 - x2*y1) - (y1 - y2)*(x3*y4 - x4*y3)
              ) /
              (
              (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
              );

      return new Point2D.Double(x, y);
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
      System.out.println("edge=" + edge);
      // Compute normal (perpendicular vector)
      Vector normal = edge.perpendicular();
      System.out.println("normal=" + normal);
      return normal.normalized();
  }
  
  public boolean intersects(Rectangle rec) {
    return getPolygon().intersects(rec);
  }

  public boolean intersects(Polygon p2) {
      // 1. Quick bounding box check
      if (!polygon.getBounds2D().intersects(p2.getBounds2D())) {
          return false;
      }
   // 2. Vertex inside check
      for (int i = 0; i < polygon.npoints; i++) {
          if (p2.contains(polygon.xpoints[i], polygon.ypoints[i])) {
              return true;
          }
      }
      for (int i = 0; i < p2.npoints; i++) {
          if (polygon.contains(p2.xpoints[i], p2.ypoints[i])) {
              return true;
          }
      }
      // 3 edge check? not necessecary?
      return false;
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

