package util;

import java.awt.geom.Point2D;

public record Vector(double x, double y) {
  private static final double R2 = 1/Math.sqrt(2); 
  private static final double EPSILON = 0.0001;
  private static final int NBDECIMALS = 3;
  private static final double SCALE = Math.pow(10, NBDECIMALS);
  
  public static final Vector NULL = new Vector(0,0);
  public static final Vector VNORTH = new Vector(0, -1);
  public static final Vector VNORTHEAST = new Vector(R2, -R2);
  public static final Vector VEAST = new Vector(1, 0);
  public static final Vector VSOUTHEAST = new Vector(R2, R2);
  public static final Vector VSOUTH = new Vector(0, 1);
  public static final Vector VSOUTHWEST = new Vector(-R2, R2);
  public static final Vector VWEST = new Vector(-1, 0);
  public static final Vector VNORTHWEST = new Vector(-R2, -R2);
  
  
  public static Vector VectorFromDirection(Direction dir) {
    return switch(dir) {
    case NULL -> NULL;
    case NORTH -> VNORTH;
    case NORTHEAST -> VNORTHEAST;
    case EAST -> VEAST;
    case SOUTHEAST -> VSOUTHEAST;
    case SOUTH -> VSOUTH;
    case SOUTHWEST -> VSOUTHWEST;
    case WEST -> VWEST;
    case NORTHWEST -> VNORTHWEST;
    };
  }

  public Vector(double x, double y) {
    // round the value up to NBDECIMALS decimals
    this.x = Math.ceil(x*SCALE)/SCALE;
    this.y = Math.ceil(y*SCALE)/SCALE;
  }
  
  public double norme() {
    return Math.sqrt(x*x + y*y);
  }
  
  public Vector normalized(double norm) {
    var n = norme();
    if(Math.abs(x) < EPSILON && Math.abs(y) < EPSILON)
      return NULL;
    if(Math.abs(x) < EPSILON)
      return new Vector(0, (y/n)*norm);
    if(Math.abs(y) < EPSILON)
      return new Vector((x/n)*norm, 0);
    
    return new Vector((x/n)*norm, (y/n)*norm);
  }
  
  public Vector perpendicular() {
	  if (x == 0) {
		  return new Vector(-y, x);
	  }else {
		  return new Vector(y, -x);
	  }
  }
  
  public Vector normalized() {
	    var n = norme();
	    if(Math.abs(x) < EPSILON && Math.abs(y) < EPSILON)
	      return NULL;
	    if(Math.abs(x) < EPSILON)
	      return new Vector(0, y/n);
	    if(Math.abs(y) < EPSILON)
	      return new Vector(x/n, 0);
	    
	    return new Vector(x/n, y/n);
	  }
  
  public Vector opposite() {
	  return new Vector(-x, -y);
  }
  
  public static Vector add(Vector v1, Vector v2) {
    return new Vector(v1.x() + v2.x(), v1.y() + v2.y());
  }
  
  public static Vector sub (Vector v1, Vector v2) {
    return new Vector(v1.x() - v2.x(), v1.y() - v2.y());
  }
  
  
  public static double scalar(Vector v1, Vector v2) {
	  return v1.x*v2.x + v1.y*v2.y;
  }
  
  public Vector times(double lambda) {
    return new Vector(lambda*x, lambda*y);
  }
  
  public Point2D.Double toPoint2d() {
	  return new Point2D.Double(x,y);
  }
  
  @Override
  public boolean equals(Object o) {
    return o instanceof Vector v
        && Math.abs(v.x() - x) < EPSILON 
        && Math.abs(v.y() - y) < EPSILON;
  }
  
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
