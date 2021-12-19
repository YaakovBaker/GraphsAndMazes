package edu.yu.introtoalgs;

import java.util.*;

public class GraphsAndMazes {

  /** A immutable coordinate in 2D space.
   *
   * Students must NOT modify the constructor (or its semantics) in any way,
   * but can ADD whatever they choose.
   */
  public static class Coordinate { 
    public final int x;
    public final int y;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private Coordinate edgeTo;
    
    /** Constructor, defines an immutable coordinate in 2D space.
     *
     * @param x specifies x coordinate
     * @param y specifies x coordinate
     */
    public Coordinate(final int x, final int y) {
      if( (x < 0) || (y < 0) ) {
        throw new IllegalArgumentException("Coordinate must be non-negative");
      }
      this.x = x;
      this.y = y;
      this.up = x - 1;
      this.down = x + 1;
      this.right = y + 1;
      this.left = y - 1;
      this.edgeTo = null;
    }

    public List<Coordinate> adjCoords(){
      List<Coordinate> adjC = new ArrayList<>();
      if( up >= 0 ){
        adjC.add(new Coordinate(up, y));
      }
      if( down >= 0 ){
        adjC.add(new Coordinate(down, y));
      }
      if( right >= 0 ){
        adjC.add(new Coordinate(x, right));
      }
      if( left >= 0){
        adjC.add(new Coordinate(x, left));
      }
      return adjC;
    }

    public void setEdgeTo( Coordinate parent ){
      this.edgeTo = parent;
    }

    public Coordinate getEdgeTo(){
      return this.edgeTo;
    }

    @Override
    public int hashCode() {
      return 0x7FFFFFFF & (31 * x) + (53 * y);
    }

    @Override
    public boolean equals(Object obj) {
      if( obj == null ){
        return false;
      }
      if( obj == this ){
        return true;
      }
      if( !(obj instanceof Coordinate) ){
        return false;
      }
      Coordinate other = (Coordinate) obj;
      return (this.x == other.x) && (this.y == other.y);
    }

    @Override
    public String toString() {
      return "Coordinate(" + x + ", " + y + ")";
    }

    /** Add any methods, instance variables, static variables that you choose
     */
  } // Coordinate class

  /** Given a maze (specified by a 2D integer array, and start and end
   * Coordinate instances), return a path (beginning with the start
   * coordinate, and terminating wih the end coordinate), that legally
   * traverses the maze from the start to end coordinates.  If no such
   * path exists, returns an empty list.  The path need need not be a
   * "shortest path".
   *
   * @param maze 2D int array whose "0" entries are interpreted as
   * "coordinates that can be navigated to in a maze traversal (can be
   * part of a maze path)" and "1" entries are interpreted as
   * "coordinates that cannot be navigated to (part of a maze wall)".
   * @param start maze navigation must begin here, must have a value
   * of "0"
   * @param end maze navigation must terminate here, must have a value
   * of "0"
   * @return a path, beginning with the start coordinate, terminating
   * with the end coordinate, and intervening elements represent a
   * legal navigation from maze start to maze end.  If no such path
   * exists, returns an empty list.  A legal navigation may only
   * traverse maze coordinates, may not contain coordinates whose
   * value is "1", may only traverse from a coordinate to one of its
   * immediate neighbors using one of the standard four compass
   * directions (no diagonal movement allowed).  A legal path may not
   * contain a cycle.  It is legal for a path to contain only the
   * start coordinate, if the start coordinate is equal to the end
   * coordinate.
   */
  public static List<Coordinate> searchMaze (final int[][] maze, final Coordinate start, final Coordinate end){
    if( (maze == null) || (start == null) || (end == null) ){//null arguments
      throw new IllegalArgumentException("One or more argument is null");
    }

    int mazeLen = maze.length;
    int mazeSubLen = maze[0].length;
    if( (mazeLen == 0) || (mazeSubLen == 0) ){//empty maze
      throw new IllegalArgumentException("Maze is empty");
    }

    int startX = start.x;
    int startY = start.y;
    int endX = end.x;
    int endY = end.y;
    
    if( (startX >= mazeLen) || (startY >= mazeSubLen) || (endX >= mazeLen) || (endY >= mazeSubLen) ){//checks out of bounds
      throw new IllegalArgumentException("One or more argument is out of bounds");
    }
    if( (maze[startX][startY]) != 0 || (maze[endX][endY] != 0) ){//checks if coordinate is a block (1)
      throw new IllegalArgumentException("One or more argument is not a valid coordinate");
    }

    List<Coordinate> path = new ArrayList<>();
    Stack<Coordinate> reversePath = new Stack<>();
    Coordinate endCoord = pathBFS(maze, start, end);
    reversePath.push(endCoord);
    if( endCoord != null ){
      Coordinate last = reversePath.peek();
      Coordinate current = last.getEdgeTo();
      while( current != null ){//place elements on the stack
        reversePath.push(current);
        current = current.getEdgeTo();
      }
      while( !reversePath.isEmpty() ){//remove the stack into a list
        path.add(reversePath.pop());
      }
    }
    return path;                
  }

  private static Coordinate pathBFS( int[][] maze, Coordinate current, Coordinate end){
    Queue<Coordinate> nextToVisit = new LinkedList<>();
    Coordinate endCoord = null;
    boolean[][] visitedCoords = new boolean[maze.length][maze[0].length];
    nextToVisit.add(current);
    while( !nextToVisit.isEmpty() ){
      Coordinate coord = nextToVisit.remove();
      if( coord.equals(end) ){
        endCoord = coord;
        return endCoord;
      }
      if( visitedCoords[coord.x][coord.y] ){
        continue;
      }
      visitedCoords[coord.x][coord.y] = true;
      for(Coordinate adj : coord.adjCoords() ){
        if( ((adj.x >= maze.length) || (adj.y >= maze[0].length)) ){
          continue;
        }else if( (maze[adj.x][adj.y] != 0) ){
          visitedCoords[adj.x][adj.y] = true;
        }
        if( !visitedCoords[adj.x][adj.y] ){
          adj.setEdgeTo(coord);
          nextToVisit.add(adj);
        }
      }
    }
    return endCoord;
  }
  
  /** minimal main() demonstrates use of APIs
   */
  public static void main (final String[] args) {
    final int[][] exampleMaze = {
      {0, 0, 0},
      {0, 1, 1},
      {0, 1, 0}
    };

    final Coordinate start = new Coordinate(2, 0);
    final Coordinate end = new Coordinate(0, 2);
    final List<Coordinate> path = searchMaze(exampleMaze, start, end);
    System.out.println("path="+path);
  }

}
