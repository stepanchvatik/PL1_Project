/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author stepa
 */
public class ConfigSql {
    public int WIDTH;
    public int HEIGHT;
    public int PlayerCount;
    public ConfigSql() throws Exception{
        
        String type="basic";
        ArrayList<Integer>config = getConfig(type);
        System.out.println(config);
        this.WIDTH = config.get(0);
        this.HEIGHT = config.get(1);
        this.PlayerCount = config.get(2);
    }
 
    public static ArrayList<Integer> getConfig(String type) throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * from config where game_type='"+type+"'");
            ResultSet result = statement.executeQuery();
            ArrayList<Integer> array=new ArrayList<Integer>();
            
            while(result.next()){
                               
                array.add(result.getInt("width"));
                array.add(result.getInt("height"));
                array.add(result.getInt("players"));
            }
            return array;
        
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/gameconfig";
            String username = "root";
            String password = "";
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
