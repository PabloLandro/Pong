/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.*;

/**
 *
 * @author landr
 */
public class GameSettings {
    
    Color color1, color2;
    int players;
    
    GameSettings(){
        color1 = Color.white;
        color2 = Color.white;
        
        players = 2;
    }
    
    public void setPlayers(int players){
        this.players = players;
    }
    public int getPlayers(){
        return players;
    }
    public Color getColor1(){
        return color1;
    }
    public Color getColor2(){
        return color2;
    }
    public void setColor1(Color color1) {
        this.color1 = color1;
    }
    public void setColor2(Color color2) {
        this.color2 = color2;
    }
    
    
}
