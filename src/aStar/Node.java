package aStar;

public class Node {
   public char type = 'd';
   public int x;
   public int y;
   public double efficiency;
   public int distanceToStart;
   public Node lastNode;

   public Node(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public void calculateEfficiency(int distance, Node endPoint) {
      ++distance;
      this.distanceToStart = distance;
      if (this.lastNode != null) {
         if (this.lastNode.lastNode == null || this.lastNode.lastNode.x != this.x && this.lastNode.lastNode.y != this.y) {
            this.efficiency = (double)(this.distanceToStart + Math.abs(this.x - endPoint.x) + Math.abs(this.y - endPoint.y));
         } else {
            this.efficiency = (double)(this.distanceToStart + Math.abs(this.x - endPoint.x) + Math.abs(this.y - endPoint.y)) + 0.5D;
         }
      }

   }
}
