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
public class Player {
    
    public final static int width = 20;
    public final static int height = 200;
    public final static int speed = 4;
    int x;
    int y;
    int dir;
    Color color;
    int score;
    
    Player(int x){
        this.x = x;
        posIni();
        dir = 0;
        score = 0;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public int getDir(){
        return dir;
    }
    
    public void setDir(int dir){
        this.dir = dir;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void addScore(int score){
        this.score += score;
    }
    
    public void move(){
        y += dir*speed;
    }
    
    public void checkCollision(){
        if(y < 0)
            y = 0;
        if(y > GamePanel.SCREEN_HEIGHT-height)
            y = GamePanel.SCREEN_HEIGHT-height;
    }
    
    public void posIni(){
        y = (GamePanel.SCREEN_HEIGHT-height)/2;
    }
    
}
