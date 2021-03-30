/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author landr
 */
public class GamePanel extends JPanel implements ActionListener{
    
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 700;
    static final int BALL_SIZE = 20;
    static final float BALL_SPEED_MAX = 3.3f;
    static final float BALL_SPEED_INI = 1;
    static final int delay = 1;
    static final int PLAYER_BORDER_OFFSET = 30;
    static final int SCORE_WAIT = 1000;
    
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    
    Color ballColor;
    
    int ballX;
    int ballY;
    float ballSpeedY;
    float ballSpeedX;
    
    boolean running;
    boolean paused;
    
    int players;
    
    Random random;
    Timer timer;
    
    ArrayList<Explosion> explosions = new ArrayList<>();
    
    GamePanel(GameSettings settings){
        frameSettings();

        players = settings.getPlayers();
        
        random = new Random();
        
        createPlayers(settings);
        
        startGame();
    }
    
    public void frameSettings(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
    }
    
    public void createPlayers(GameSettings settings){
        player1 = new Player(0);
        player2 = new Player(SCREEN_WIDTH-Player.width);
        player3 = new Player(50);
        player4 = new Player(SCREEN_WIDTH-Player.width-50);
        player1.setColor(settings.getColor1());
        player2.setColor(settings.getColor2());
    }
    
    public void startGame(){
        running = true;
        paused = false;
        timer = new Timer(delay, this);
        newBall(player1);
        timer.start();
    }
    
    public void newBall(Player player){
        ballX = (SCREEN_WIDTH-BALL_SIZE)/2;
        ballY = (SCREEN_HEIGHT-BALL_SIZE)/2;
        ballSpeedY = (float)((random.nextFloat()-0.5)*BALL_SPEED_MAX);
        ballSpeedX = -(Math.abs((SCREEN_WIDTH/2)-player.getX())/((SCREEN_WIDTH/2)-player.getX()))*BALL_SPEED_INI;
        ballColor = Color.white;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        //Draw score
        drawScore(g);
        
        //Draw explosions
        drawExplosions(g);
        
        //Draw players
        drawPlayers(g);
        
        //Draw ball
        drawBall(g);
    }
    
    public void drawPlayers(Graphics g){
        g.setColor(player1.getColor());
        g.fillRect(player1.getX(), player1.getY(), Player.width, Player.height);
        g.setColor(player2.getColor());
        g.fillRect(player2.getX(), player2.getY(), Player.width, Player.height);
        if(players == 4){
            g.setColor(player1.getColor());
            g.fillRect(player3.getX(), player3.getY(), Player.width, Player.height);
            g.setColor(player2.getColor());
            g.fillRect(player4.getX(), player4.getY(), Player.width, Player.height);
        }
    }
    
    void drawExplosions(Graphics g){
        for(Explosion explosion: explosions)
            if(explosion.particlesMoving())
                explosion.draw(g);
    }
    
    public void drawBall(Graphics g){
        g.setColor(ballColor);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }
    
    public void drawScore(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(player1.getScore() + "    " + player2.getScore(), (SCREEN_WIDTH-metrics.stringWidth(player1.getScore() + "    " + player2.getScore()))/2, g.getFont().getSize());
    }
    
    void move(){
        player1.move();
        player2.move();
        player3.move();
        player4.move();
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        for(Explosion explosion: explosions)
            explosion.moveParticles();
    }
    
    void checkCollisions(){
        player1.checkCollision();
        player2.checkCollision();
        player3.checkCollision();
        player4.checkCollision();

        if(ballY >= SCREEN_HEIGHT-BALL_SIZE){
            ballSpeedY = Integer.max((int)ballSpeedY, 1)*(-1);
        }
        if(ballY <= 0){
            ballSpeedY = Integer.min((int)ballSpeedY, -1)*(-1);
        }
        
        checkBallCollision(player1);
        checkBallCollision(player2);
        if(players == 4){
            checkBallCollision(player3);
            checkBallCollision(player4);
        }
    }
    
    void checkPointScored(){
        if(ballX <= -BALL_SIZE/2){
            pointScored(player2);
        }
        if(ballX >= SCREEN_WIDTH-(BALL_SIZE/2)){
            pointScored(player1);
        }
    }
    
    public void pointScored(Player player){
        player.addScore(1);
        explosions.add(new Explosion(new Point(ballX, ballY)));
        player1.posIni();
        player2.posIni();
        player3.posIni();
        player4.posIni();
        newBall(player);
    }
    
    public void checkBallCollision(Player player){
        if(!((ballX > (player.getX()+Player.width)) || (ballX < player.getX()-BALL_SIZE) || (ballY > (player.getY()+Player.height)) || (ballY < (player.getY()-BALL_SIZE)))){
            ballCollision(player);
        }
    }
    
    public void ballCollision(Player player){
        if(player.getX() < SCREEN_WIDTH/2){
            ballSpeedX = BALL_SPEED_MAX;
        }
        else{
            ballSpeedX = -BALL_SPEED_MAX;
        }
        ballColor = player.getColor();
        ballSpeedY = (float)((2*(float)((float)(ballY+BALL_SIZE/2)-player.getY())/Player.height)-1)*BALL_SPEED_MAX;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(!paused){
            move();
            checkCollisions();
        }
        repaint();
        checkPointScored();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W:
                    player1.setDir(-1);
                    break;
                case KeyEvent.VK_S:
                    player1.setDir(1);
                    break;
                case KeyEvent.VK_UP:
                    player2.setDir(-1);
                    break;
                case KeyEvent.VK_DOWN:
                    player2.setDir(1);
                    break;
                case KeyEvent.VK_J:
                    player3.setDir(-1);
                    break;
                case KeyEvent.VK_M:
                    player3.setDir(1);
                    break;
                case KeyEvent.VK_NUMPAD9:
                    player4.setDir(-1);
                    break;
                case KeyEvent.VK_NUMPAD6:
                    player4.setDir(1);
                    break;
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W: case KeyEvent.VK_S:
                    player1.setDir(0);
                    break;
                case KeyEvent.VK_UP: case KeyEvent.VK_DOWN:
                    player2.setDir(0);
                    break;
                case KeyEvent.VK_J: case KeyEvent.VK_M:
                    player3.setDir(0);
                    break;
                case KeyEvent.VK_NUMPAD9: case KeyEvent.VK_NUMPAD6:
                    player4.setDir(0);
                    break;
            }
        }
        
    }
    
}
