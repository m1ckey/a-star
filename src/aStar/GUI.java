package aStar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JButton btRun;
   private JButton btDefault;
   private JButton btObstacle;
   private JButton btStart;
   private JButton btEnd;
   public static Graphics g;
   private char settingState = 'd';
   private Node startPoint;
   private Node endPoint;
   private Node lastHighlighted;
   private Labyrinth plLabyrinth;
   private Node[][] node = new Node[10][10];
   private AStar aStar;
   private JButton btClear;

   public static void main(String[] args) {
      System.out.println("Preparing GUI.....processing");
      System.out.print("Setting Look and Feel.....");

      try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

      System.out.println("done");
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               GUI frame = new GUI();
               System.out.println("GUI.....done");
               frame.setVisible(true);
            } catch (Exception var2) {
               var2.printStackTrace();
            }

         }
      });
   }

   public GUI() {
      this.setResizable(false);
      System.out.print("Generating Frame.....");
      this.setBackground(new Color(255, 255, 255));
      this.setTitle("A*");
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 434, 360);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
      this.contentPane.setLayout(new BorderLayout(10, 0));
      this.setContentPane(this.contentPane);
      System.out.println("done");
      System.out.print("Generating Control Panel.....");
      JPanel plControl = new JPanel();
      plControl.setBorder(new TitledBorder((Border)null, "Control", 4, 2, (Font)null, (Color)null));
      this.contentPane.add(plControl, "West");
      plControl.setLayout(new GridLayout(6, 1, 0, 0));
      System.out.println("done");
      System.out.print("Generating Buttons.....");
      this.btDefault = new JButton("Default");
      this.btDefault.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GUI.this.settingState = 'd';
         }
      });
      plControl.add(this.btDefault);
      this.btObstacle = new JButton("Obstacle");
      this.btObstacle.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GUI.this.settingState = 'o';
         }
      });
      plControl.add(this.btObstacle);
      this.btStart = new JButton("Start");
      this.btStart.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GUI.this.settingState = 's';
         }
      });
      plControl.add(this.btStart);
      this.btEnd = new JButton("End");
      this.btEnd.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GUI.this.settingState = 'e';
         }
      });
      plControl.add(this.btEnd);
      this.btClear = new JButton("Clear");
      this.btClear.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            GUI.this.clearGrid();
         }
      });
      plControl.add(this.btClear);
      this.btRun = new JButton("Run");
      this.btRun.setEnabled(false);
      this.btRun.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GUI.this.run();
         }
      });
      plControl.add(this.btRun);
      System.out.println("done");
      System.out.print("Generating Labyrinth Panel.....");
      this.plLabyrinth = new Labyrinth();
      this.plLabyrinth.setBounds(new Rectangle(this.getX(), this.getY(), 311, 311));
      this.plLabyrinth.addMouseListener(new MouseAdapter() {
         public void mouseExited(MouseEvent e) {
            GUI.this.clearHighlightedLabyrinth();
         }

         public void mouseClicked(MouseEvent e) {
            GUI.this.setNode(e);
         }
      });
      this.plLabyrinth.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent e) {
            GUI.this.highlightLabyrinth(e);
         }
      });
      this.plLabyrinth.setCursor(Cursor.getPredefinedCursor(1));
      this.plLabyrinth.setBackground(new Color(255, 255, 255));
      this.plLabyrinth.setBorder(new LineBorder(new Color(0, 0, 0)));
      this.contentPane.add(this.plLabyrinth, "Center");
      System.out.println("done");
      System.out.print("Initialising Coordinate Array...");

      for(int i = 0; i < 10; ++i) {
         for(int ii = 0; ii < 10; ++ii) {
            this.node[i][ii] = new Node(i, ii);
         }
      }

      this.lastHighlighted = this.node[0][0];
      System.out.println("done");
   }

   private void highlightLabyrinth(MouseEvent e) {
      int x = (int)((double)e.getX() / 31.1D);
      int y = (int)((double)e.getY() / 31.1D);
      if (x != this.lastHighlighted.x || 9 - y != this.lastHighlighted.y) {
         this.clearHighlightedLabyrinth();
      }

      this.lastHighlighted = this.node[x][9 - y];
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(x * 31 + 1, y * 31 + 1, 30, 30);
   }

   private void clearHighlightedLabyrinth() {
      if (this.lastHighlighted.type == 'd') {
         g.setColor(Color.WHITE);
      } else if (this.lastHighlighted.type == 'o') {
         g.setColor(Color.GRAY);
      } else if (this.lastHighlighted.type == 's') {
         g.setColor(Color.GREEN);
      } else if (this.lastHighlighted.type == 'e') {
         g.setColor(Color.RED);
      } else if (this.lastHighlighted.type == 'w') {
         g.setColor(Color.BLUE);
      }

      g.fillRect(this.lastHighlighted.x * 31 + 1, (9 - this.lastHighlighted.y) * 31 + 1, 30, 30);
   }

   private void setNode(MouseEvent e) {
      int x = (int)((double)e.getX() / 31.1D);
      int y = (int)((double)(311 - e.getY()) / 31.1D);
      switch(this.settingState) {
      case 'd':
         System.out.print("Setting Node (" + x + "|" + y + ") Type Default.....");
         if (this.node[x][y].type == 's') {
            this.startPoint = null;
         }

         if (this.node[x][y].type == 'e') {
            this.endPoint = null;
         }

         this.node[x][y].type = 'd';
         break;
      case 'e':
         System.out.print("Setting Node (" + x + "|" + y + ") Type End.....");
         if (this.node[x][y].type == 's') {
            this.startPoint = null;
         }

         this.node[x][y].type = 'e';
         if (this.endPoint == null) {
            this.endPoint = this.node[x][y];
         } else if (this.node[x][y] != this.endPoint) {
            this.endPoint.type = 'd';
            g.setColor(Color.WHITE);
            g.fillRect(this.endPoint.x * 31 + 1, (9 - this.endPoint.y) * 31 + 1, 30, 30);
         }

         this.endPoint = this.node[x][y];
         break;
      case 'o':
         System.out.print("Setting Node (" + x + "|" + y + ") Type Obstacle.....");
         if (this.node[x][y].type == 's') {
            this.startPoint = null;
         }

         if (this.node[x][y].type == 'e') {
            this.endPoint = null;
         }

         this.node[x][y].type = 'o';
         break;
      case 's':
         System.out.print("Setting Node (" + x + "|" + y + ") Type Start.....");
         if (this.node[x][y].type == 'e') {
            this.endPoint = null;
         }

         this.node[x][y].type = 's';
         if (this.startPoint == null) {
            this.startPoint = null;
         } else if (this.node[x][y] != this.startPoint) {
            this.startPoint.type = 'd';
            g.setColor(Color.WHITE);
            g.fillRect(this.startPoint.x * 31 + 1, (9 - this.startPoint.y) * 31 + 1, 30, 30);
         }

         this.startPoint = this.node[x][y];
         break;
      default:
         System.out.print("Illegal Setting State.....");
      }

      if (this.startPoint != null && this.endPoint != null) {
         this.btRun.setEnabled(true);
      } else {
         this.btRun.setEnabled(false);
      }

      System.out.println("done");
   }

   private void clearGrid() {
      System.out.print("Clearing Grid.....");
      this.settingState = 'c';

      for(int i = 0; i < 10; ++i) {
         for(int ii = 0; ii < 10; ++ii) {
            this.node[i][ii] = new Node(i, ii);
         }
      }

      this.lastHighlighted = this.node[0][0];
      this.startPoint = null;
      this.endPoint = null;
      this.plLabyrinth.setBorder(new LineBorder(new Color(0, 0, 0)));
      this.btDefault.setEnabled(true);
      this.btObstacle.setEnabled(true);
      this.btStart.setEnabled(true);
      this.btEnd.setEnabled(true);
      this.btRun.setEnabled(false);
      System.out.println("done");
   }

   private void run() {
      this.settingState = 'c';
      this.aStar = new AStar(this.startPoint, this.endPoint, this.node, g);
      this.aStar.start();
      this.btDefault.setEnabled(false);
      this.btObstacle.setEnabled(false);
      this.btStart.setEnabled(false);
      this.btEnd.setEnabled(false);
      this.btRun.setEnabled(false);
   }
}
