/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author landr
 */
public class MenuFrame extends JFrame{
    
    final int WIDTH = 500;
    final int HEIGHT = 150;
    
    
    JButton playButton = new JButton("Play");
    
    JButton colorLeft = new JButton("Left Color");
    JButton colorRight = new JButton("Right Color");
    
    JRadioButton opcion2Jugadores = new JRadioButton("2 Jugadores");
    JRadioButton opcion4Jugadores = new JRadioButton("4 Jugadores");
    ButtonGroup group = new ButtonGroup();
    
    GameSettings settings = new GameSettings();
    
    MenuFrame(){
        frameSettings();
        
        initializeButtons();
        
        addComponents();
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    void startGame(){
        new GameFrame(settings);
    }
    
    void frameSettings(){
        this.setTitle("Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
    }
    
    void initializeButtons(){
        playButton.setFocusable(false);
        
        colorLeft.setFocusable(false);
            
        colorRight.setFocusable(false);

        group.add(opcion2Jugadores);
        group.add(opcion4Jugadores);
        opcion2Jugadores.setFocusable(false);
        opcion4Jugadores.setFocusable(false);
        lambdaExpressions();
        buttonBounds();
    }
    
    void lambdaExpressions(){
        playButton.addActionListener((e)->startGame());
        colorLeft.addActionListener((e)->{
            JColorChooser chooserLeft = new JColorChooser();
            settings.setColor1(chooserLeft.showDialog(null, "Pick left color", Color.white));
        });
        colorRight.addActionListener((e)->{
            JColorChooser chooserLeft = new JColorChooser();
            settings.setColor2(chooserLeft.showDialog(null, "Pick right color", Color.white));
        });
        opcion2Jugadores.addActionListener((e)->settings.setPlayers(2));
        opcion4Jugadores.addActionListener((e)->settings.setPlayers(4));
    }
    
    void buttonBounds(){
        playButton.setBounds((WIDTH-100)/2, (HEIGHT-100)/2, 100, 50);
        opcion2Jugadores.setBounds(30, 20, 150, 20);
        opcion4Jugadores.setBounds(30, 60, 150, 20);
        colorRight.setBounds(WIDTH-130, 20, 100, 20);
        colorLeft.setBounds(WIDTH-130, 60, 100, 20);
    }

    void addComponents(){
        this.add(playButton);
        this.add(opcion2Jugadores);
        this.add(opcion4Jugadores);
        this.add(colorLeft);
        this.add(colorRight);
    }
    
}
