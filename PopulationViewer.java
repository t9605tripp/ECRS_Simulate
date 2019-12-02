import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import java.util.concurrent.*; 
import java.util.Date; 


class Surface extends JPanel implements ActionListener {

   private final int DELAY = 600;
   private Timer timer;
   private Population population;
   private int windowSize = 1000;
   private int iters = 0;
//1000 by 1000 gives 1million sqr ft. if one square is one square foot.
//We will test several acres, and also one acre for the window sizes.
   public Surface() {
      initPop();
      initTimer();
      
      /*JLabel popLabel = new JLabel("Total Population");
      popLabel.setBackground(Color.WHITE);
      add(popLabel);*/
     
   }

   private void initPop() {
      Plant[] plantTypes = new Plant[1];
      plantTypes[0] = new MountainLaurel(0);
      population = new Population(plantTypes);
   }
   
   private void initTimer() {
   
      timer = new Timer(DELAY, this);
      timer.start();
   }
   
   public Timer getTimer() {
      
      return timer;
   }

   private void doDrawing(Graphics g) {
      setBackground(Color.BLACK);
      Graphics2D g2d = (Graphics2D) g;
      
      //Draw the plants
      
      ArrayList <Point> livingPoints = new ArrayList<Point>();
      ArrayList <Point> maturePoints = new ArrayList<Point>();
      ArrayList <Point> deadPoints = new ArrayList<Point>();

      livingPoints = population.getLivingPlantPoints();
      maturePoints = population.getMaturePlantPoints();
      deadPoints = population.getDeadPlantPoints();
      g2d.setPaint(Color.green);
      for (int i = 0; i < livingPoints.size(); i++) {
         
         g2d.drawOval(livingPoints.get(i).getX(), livingPoints.get(i).getY(), 1, 4);
      }
      g2d.setPaint(Color.orange);
      for (int i = 0; i < maturePoints.size(); i++) {
         
         g2d.drawOval(maturePoints.get(i).getX(), maturePoints.get(i).getY(), 1, 8);
      }
      g2d.setPaint(Color.red);
      for (int i = 0; i < deadPoints.size(); i++) {
         
         g2d.drawOval(deadPoints.get(i).getX(), deadPoints.get(i).getY(), 1, 8);
      }
   }
   
   @Override
   public void paintComponent(Graphics g) {
   
      super.paintComponent(g);
      doDrawing(g);
   }

   public void repopulate(){
      population.nextYear();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      
      if (iters == 30){
         for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(4);stop>System.nanoTime();) {
         /*
         * Hammer the JVM with junk
         */
         }
         iters = 0;
         initPop();
         repaint();
      }
      else{
         repopulate();
         repaint();
         iters++;
      }
   }
}

public class PopulationViewer extends JFrame {


   private int windowSize = 1000;
   public PopulationViewer() {
      initUI();
   }

   private void initUI() {
      
      final Surface surface = new Surface();
      add(surface);
   
      addWindowListener(
         new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               Timer timer = surface.getTimer();
               timer.stop();
            }
         });
      
      setTitle("Points");
      setSize(windowSize, windowSize);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public static void main(String[] args) {
   
      EventQueue.invokeLater(
         new Runnable() {
            @Override
            public void run() {
               PopulationViewer ex = new PopulationViewer();
               ex.setVisible(true);
            }
         });
   }
}