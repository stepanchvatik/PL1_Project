/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
/**

 * @author stepa
 */
public class Server {
    
    static ServerSocket serverSocket;
    static Socket socket;
   
    static Users[] user = new Users[10];
    
     public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");
        serverSocket = new ServerSocket(7777);
        System.out.println("Server started...");
        Game game = new Game();
        while(true){
            socket = serverSocket.accept();
            for(int i = 0; i <10;i++){
                if(user[i]==null){
                    System.out.println("Connection from:"+socket.getInetAddress());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    user[i] = new Users(out,in,user,i,game);
                    Thread th = new Thread(user[i]);
                    th.start();
                    break;

                }
            }
        }
        
    }
}










class Users implements Runnable{
    DataOutputStream out;
    DataInputStream in;
    Users[] user = new Users[10];
    int playerid;
    int pidin;
    double angle;
    Random rand = new Random();
    Game game;
    public Users(DataOutputStream out, DataInputStream in,Users[] user,int pid,Game g){
        this.out = out;
        this.in = in;
        this.user = user;
        this.playerid=pid;
        this.game = g;
    }
    public void run(){
        
        try {
            System.out.println("Sending player id " + playerid);
          out.writeInt(playerid);
        } catch (IOException ex) {
            System.out.println("Failed to send PlayerID");
        }
            /* while(true){
            try{
            System.out.println("Iam reading");
            int player = in.readInt();
            int ready = in.readInt();
            System.out.println("I hav evalues");
            if(ready!=1)
            ready=0;
            game.ready[player]=ready;
            }catch(IOException e){
                
            }
            int count = 0;
            int readycount = 0;
            for(int i = 0; i<10;i++){
            if(user[i]!=null){
            count++;
            if(game.ready[i]==1)
            readycount++;
            }
            }
            System.out.println(count + " , " + readycount);
            if(count==readycount)
            break;
            }
            for(int j = 0;j<10;j++){
            if(user[j]!=null){
            try {
            user[j].out.writeBoolean(true);
            } catch (IOException e) {
            }
            }
            }*/
       
        while(true){
            try{
                pidin = in.readInt();
                if(pidin==0){
                    game.move();
                }
                angle = in.readDouble();
                game.angle[pidin]=angle;
                for(int i = 0; i < 10;i++){
                    if(user[i]!=null){
                        
                        for(int j = 0;j<10;j++){
                            if(user[j]!=null){
                                int x = (int)game.position[i][0];
                                int y = (int)game.position[i][1];
                                System.out.println(j+" " + x + " " + y);
                                if(game.delay[i]>0){
                                    user[j].out.writeInt(i+10);
                                }
                                else{
                                    user[j].out.writeInt(i);
                                }
                                user[j].out.writeInt(x);
                                user[j].out.writeInt(y);
                                
                            }
                               
                        }
                    }
                }
            } catch(IOException e){
                user[playerid]=null;
            }
            try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
        }
    }
}
