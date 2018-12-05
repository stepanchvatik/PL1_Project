/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;


/**
 *
 * @author stepa
 */
public class Client extends Applet implements Runnable,KeyListener{
    
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    /**
     * @param args the command line arguments
     */
    int playerid;
    Color[] colors = {new Color(63,159,128),new Color(242,251,63),new Color(10,53,191),new Color(177,1,95),new Color(0,255,0),new Color(0,255,0),new Color(0,255,0),new Color(0,255,0),new Color(0,255,0),new Color(0,255,0),new Color(0,0,0)};
    Random rand = new Random();
    boolean left,right,ready,start = false;
    int[][] players = new int[11][3];
    double angle = 0;
 
    public void init(){
        setSize(500,500);
        setBackground(Color.BLACK);
        addKeyListener(this);
        try{
            System.out.println("Connecting...");
            socket = new Socket("localhost",7777);
            System.out.println("Connection successful.");
            in = new DataInputStream(socket.getInputStream());
            playerid=in.readInt();
            out = new DataOutputStream(socket.getOutputStream());
            Input input = new Input(in,this);
            Thread thread = new Thread(input);
            thread.start();
            Thread thread2 = new Thread(this);
            thread2.start();
        }catch(Exception e){
            System.out.println("Unable to start client");
        }
   
       
    }

    public void paint(Graphics g){
        for(int i = 0;i<10;i++){
            if(players[i][2]==10){
                
                int x = players[i][0];
                int y = players[i][1];
                g.setColor(colors[players[i][2]]);
                for(int j=-2;j<6;j++){
                    g.drawLine(x+j,y-2,x+j,y+5);
                }
                g.setColor(colors[i]);
                for(int j=0;j<4;j++){
                    g.drawLine(x+j,y,x+j,y+3);
                }
                
                
            }else{
                 g.setColor(colors[i]);
                int x = players[i][0];
                int y = players[i][1];
                for(int j=0;j<4;j++){
                    g.drawLine(x+j,y,x+j,y+3);
                }
            }
               
                
            
        }
        
    }
    public void update(Graphics g) {
      paint(g);
    }
    public void run(){
      
        while(true){
            
         
                if(right == true){
                    angle+=5;
                    if(angle==360){
                        angle=0;
                    }
                }
                if(left == true){
                    angle-=5;
                    if(angle==-5){
                        angle=355;
                    }
                }

                try{
                    out.writeInt(playerid);
                    out.writeDouble(angle);
                }catch(Exception e){
                    System.out.println("Error sending coordinates");
                }
                
                repaint();
            
            
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }
    
  
    public void keyTyped(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==37){
            left = true;
        }
    
        if(e.getKeyCode()==39){
            right = true;
        }
        if(e.getKeyCode()==32){
            ready = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==37){
            left = false;
        }
      
        if(e.getKeyCode()==39){
            right = false;
        }
        
    }
 
    

}

class Input implements Runnable{
    DataInputStream in;
    Client client;
    public Input(DataInputStream in,Client c){
        this.in = in;
        this.client = c;
        
    }
    public void run(){
        while(true){
            try{
                int t;
                do{
                    t = in.readInt();
                }while(t<0||t>=20);
                
                int x = in.readInt();
                int y = in.readInt();
                int c;
                if(t>=10){
                    t-=10;
                    c=10;
                }
                else{
                    c=t;
                }
                    //client.screen[x][y]=t;
                
                client.players[t][0]=x;
                client.players[t][1]=y;
                client.players[t][2]=c;
                
                
                
                  
            }catch(IOException e){
                e.printStackTrace();
            }
          
            
        }
    }
}

