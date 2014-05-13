package com.picoteq;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game implements Runnable{
   
   final int WIDTH = 1000;
   final int HEIGHT = 700;
   private long gframe = 0;
   
   JFrame frame;
   Canvas canvas;
   BufferStrategy bufferStrategy;
   
   public Game(){
      frame = new JFrame("Basic Game");
      
      p1.x=2;
      p1.y=5;
      p1.sx=p1.sy=32;
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
	   public double x;
	   public double y;
	   
	   public double sx;
	   public double sy;
	   
	   public double velocity;
	   
	   public double theta;
	   
	   public void apply(Graphics2D g){
		   g.fillRect((int)p1.x,(int)p1.y,(int)p1.sx,(int)p1.sy);
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
      p1.x=(p1.x+p1.velocity)%(WIDTH-p1.sx);
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