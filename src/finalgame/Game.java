/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

import java.util.Random;

/**
 *
 * @author stepa
 */
class Game{
    ConfigSql config;
    double[][] position; 
    double[] angle; 
    int[] delay; 
    int[] dead;
    int[] ready; 
    int[][] screen;
    int[][] directions = new int[][]{
        {1,0},
        {1,1},
        {0,1},
        {-1,1},
        {-1,0},
        {-1,-1},
        {0,-1},
        {1,-1}
    };
    Random rand = new Random();
    public Game() throws Exception{
        position = new double[10][2];
        angle = new double[10];
        delay = new int[10];
        dead = new int[10];
        ready = new int[10];
        screen = new int[500][500];
        for(int i = 0; i < 10;i++){
            position[i][0] = rand.nextInt(500 - 150) + 75;
            position[i][1] = rand.nextInt(500 - 150) + 75;
            angle[i] = rand.nextInt(72)*5;
            delay[i]=0;
            ready[i]=0;
            dead[i]=0;
            
        }
        for(int i= 0;i<500;i++){
                for(int j = 0;j<500;j++){
                    screen[i][j]=0;
                }
            }
    }
    public void move(){
        for(int i = 0; i < 10;i++){
            if(dead[i]==1)
                continue;
            int d = rand.nextInt(100);
            if((d>95&&delay[i]==0) || delay[i]!=0){
                delay[i]++;
            }
            if(delay[i] ==20){
                delay[i]=0;
            }
            int dir = (int)Math.round(angle[i]/45)%8;
            if(delay[i]==0)
            for(int x = 1;x<3;x++){
                for(int y = 1;y<3;y++){
                    int tmpx = (int)position[i][0] + x*directions[dir][0];
                    int tmpy = (int)position[i][1] + y*directions[dir][1];
                    if(tmpx < 0 || tmpx > 495 ||tmpy<0||tmpy>495){
                         dead[i]=1;
                         System.out.println("Konec mapy");
                    }
                    else if(screen[tmpx][tmpy]==1){
                        dead[i]=1;
                        System.out.println("Naraz");
                    }
                }
            }
            double a = angle[i];
            position[i][0] +=2*Math.cos(Math.toRadians(a));
            position[i][1] +=2*Math.sin(Math.toRadians(a));
            if(dead[i]==1){
                position[i][0]= -10;
                position[i][1]= -10;
            }
            else{
                
                if(delay[i]!=0){
                       screen[(int)position[i][0]][(int)position[i][1]]=0;
                }
                else{
                       screen[(int)position[i][0]][(int)position[i][1]]=1;
                }
            }
         
        
            angle[i] = rand.nextInt(72)*5;
            
        }
    }
    
}
