package aStar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ListIterator;

public class AStar {
   private Node startPoint;
   private Node endPoint;
   private Node bestEfficiency;
   private Node currentNode;
   private Node tmp;
   private Node[][] node;
   private ArrayList possibleNodes;
   private ArrayList checkedNodes;
   ListIterator itPossible;
   private Graphics g;

   public AStar(Node startPoint, Node endPoint, Node[][] node, Graphics g) {
      System.out.print("Initialising A*.....");
      this.startPoint = startPoint;
      this.endPoint = endPoint;
      this.node = node;
      this.g = g;
      this.tmp = new Node(0, 0);
      this.tmp.efficiency = 2.147483647E9D;
      System.out.println("done");
   }

   public void start() {
      System.out.println("Starting A*.....processing");
      this.generateLists();

      while(true) {
         this.currentNode = this.getNextNode();
         System.out.print("Checking if Plausible.....");
         if (this.possibleNodes.isEmpty()) {
            System.out.println("No Way Possible");
            return;
         }

         if (this.currentNode == this.endPoint) {
            System.out.println("Way Possible");
            System.out.print("Drawing Final Way.....");
            this.currentNode = this.currentNode.lastNode;
            this.g.setColor(Color.BLUE);

            while(this.currentNode.lastNode != null) {
               this.g.fillRect(this.currentNode.x * 31 + 1, (9 - this.currentNode.y) * 31 + 1, 30, 30);
               this.currentNode.type = 'w';
               this.currentNode = this.currentNode.lastNode;
            }

            System.out.println("done");
            System.out.println("A*.....done");
            return;
         }

         System.out.println("done");
         if (this.currentNode.y + 1 < 10) {
            this.checkConnectedNode(this.node[this.currentNode.x][this.currentNode.y + 1]);
         }

         if (this.currentNode.x + 1 < 10) {
            this.checkConnectedNode(this.node[this.currentNode.x + 1][this.currentNode.y]);
         }

         if (this.currentNode.y - 1 >= 0) {
            this.checkConnectedNode(this.node[this.currentNode.x][this.currentNode.y - 1]);
         }

         if (this.currentNode.x - 1 >= 0) {
            this.checkConnectedNode(this.node[this.currentNode.x - 1][this.currentNode.y]);
         }

         this.possibleNodes.remove(this.currentNode);
         this.checkedNodes.add(this.currentNode);
      }
   }

   private void generateLists() {
      System.out.print("Generating Lists.....");
      this.possibleNodes = new ArrayList();
      this.checkedNodes = new ArrayList();
      this.startPoint.calculateEfficiency(-1, this.endPoint);
      this.possibleNodes.add(this.startPoint);
      System.out.println("done");
   }

   private Node getNextNode() {
      System.out.print("Calculating next Node.....");
      this.bestEfficiency = this.tmp;
      this.itPossible = this.possibleNodes.listIterator();

      while(this.itPossible.hasNext()) {
         if (((Node)this.itPossible.next()).efficiency < this.bestEfficiency.efficiency) {
            this.bestEfficiency = (Node)this.itPossible.previous();
         }
      }

      System.out.println("done (" + this.bestEfficiency.x + "|" + this.bestEfficiency.y + ")");
      return this.bestEfficiency;
   }

   private void checkConnectedNode(Node n) {
      System.out.print("Checking Connected Node (" + n.x + "|" + n.y + ").....");
      if (n.type != 'o' && !this.checkedNodes.contains(n)) {
         if (this.possibleNodes.contains(n)) {
            if (n.distanceToStart > this.currentNode.distanceToStart + 1) {
               System.out.println("Found a better way for this Node");
               n.lastNode = this.currentNode;
               n.calculateEfficiency(this.currentNode.distanceToStart, this.endPoint);
            } else {
               System.out.println("Node already in List of Possible Ways");
            }
         } else {
            System.out.println("Possible Way to the End");
            n.lastNode = this.currentNode;
            n.calculateEfficiency(this.currentNode.distanceToStart, this.endPoint);
            this.possibleNodes.add(n);
         }

      } else {
         if (n.type == 'o') {
            System.out.println("Node is an Obstacle");
         } else {
            System.out.println("Node has already been verified");
         }

      }
   }
}
