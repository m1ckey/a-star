package aStar;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Labyrinth extends JPanel {
   private static final long serialVersionUID = 1L;

   public void paintComponent(Graphics g) {
      System.out.print("Drawing Labyrinth Grid.....");
      super.paintComponents(g);
      this.setBounds(this.getX(), this.getY(), 311, 311);
      GUI.g = this.getGraphics();
      int width = this.getWidth();
      int height = this.getHeight();
      g.clearRect(0, 0, width, height);
      g.setColor(Color.BLACK);

      for(int i = 1; i < 11; ++i) {
         g.drawLine((int)((double)i * ((double)width / 10.0D)), 0, (int)((double)i * ((double)width / 10.0D)), height);
         g.drawLine(0, (int)((double)i * ((double)height / 10.0D)), width, (int)((double)i * ((double)height / 10.0D)));
      }

      System.out.println("done");
   }
}
