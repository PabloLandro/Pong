/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javax.swing.*;
/**
 *
 * @author landr
 */
public class GameFrame extends JFrame{
    
    GameFrame(GameSettings settings){
        this.setTitle("Pong");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(new GamePanel(settings));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
}
