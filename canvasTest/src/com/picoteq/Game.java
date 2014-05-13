package com.picoteq;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game implements Runnable{

   final int WIDTH = 100;
   final int HEIGHT = 100;


   private long gframe = 100;
   
   JFrame frame;
   Canvas canvas;
   BufferStrategy bufferStrategy;
   
   public Game(){
      frame = new JFrame("Basic Game");
      
      p1.pos.x=2;
      p1.pos.y=5;
      p1.size.x=p1.size.y=1;
      p1.velocity=10;
      
      JPanel panel = (JPanel) frame.getContentPane();
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      panel.setLayout(null);
      
      canvas = new Canvas();
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
      
      panel.add(canvas);
      
      canvas.addMouseListener(new MouseControl());
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      
      canvas.requestFocus();
   }
   
   private class Ship{
	   public Point pos = new Point();
	   
	   public Point size = new Point();
	   
	   public int velocity;
	   
	   public double theta;
	   
	   public void apply(Graphics2D g){
		   System.out.print("\n" + pos.x + " " + pos.y);
		   
		   g.fillRect(pos.x,pos.y,size.x,size.y);
		   
	   }
   }
        
   private class MouseControl extends MouseAdapter{
      
   }
   
   long desiredFPS = 60;
   long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    
   boolean running = true;
   
   public void run(){
      
      long beginLoopTime;
      long endLoopTime;
      long currentUpdateTime = System.nanoTime();
      long lastUpdateTime;
      long deltaLoop;
      
      while(running){
         beginLoopTime = System.nanoTime();
         
         render();
         
         lastUpdateTime = currentUpdateTime;
         currentUpdateTime = System.nanoTime();
         update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
         
         gframe++;
         endLoopTime = System.nanoTime();
         deltaLoop = endLoopTime - beginLoopTime;
           
           if(deltaLoop > desiredDeltaLoop){
               //Do nothing. We are already late.
           }else{
               try{
                   Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
               }catch(InterruptedException e){
                   //Do nothing
               }
           }
      }
   }
   
   private void render() {
      Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
      g.clearRect(0, 0, WIDTH, HEIGHT);
      render(g);
      g.dispose();
      bufferStrategy.show();
   }
   
   Ship p1 = new Ship();
   
   
   /**
    * Rewrite this method for your game
    */
   protected void update(int deltaTime){
      p1.pos.x++;
      if (p1.pos.x > 500)
    	  p1.pos.x = 0;
   }
   
   /**
    * Rewrite this method for your game
    */
   protected void render(Graphics2D g){
      p1.apply(g);
   }
   
   public static void main(String [] args){
      Game ex = new Game();
      new Thread(ex).start();
   }

}
