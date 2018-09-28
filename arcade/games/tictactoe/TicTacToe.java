package arcade.games.tictactoe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import arcade.Game;

public class TicTacToe extends Game implements ActionListener{
    
    private final JButton[][] tiles = new JButton[3][3];
    private JButton vsPlayer;
    private JButton vsAi;
    private JButton menu;
    private JButton reset;
    
    private JLabel label;

    private final int width = 520;
    private final int height = 780;
    
    private final int x = 1;
    private final int o = 2;
    private int currentPlayer = 0;
    private int winner = 0;
    
    private final int[][] tile = new int[3][3];
    private int tilesLeft = 9;
    
    private int aiPlayer = -1;
    private int aiMaxTick = 0;
    private int aiTick = 0;
    
    private int currentMode = 0;
    private boolean gameOver = false;

    private final ImageIcon idleMode1 = new ImageIcon(loadImage("assets/buttons/idleMode1.png"));
    private final ImageIcon selectedMode1 = new ImageIcon(loadImage("assets/buttons/selectedMode1.png"));
    private final ImageIcon idleMode2 = new ImageIcon(loadImage("assets/buttons/idleMode2.png"));
    private final ImageIcon selectedMode2 = new ImageIcon(loadImage("assets/buttons/selectedMode2.png"));
    private final ImageIcon resetImg = new ImageIcon(loadImage("assets/buttons/reset.png"));
    private final ImageIcon mainmenuImg = new ImageIcon(loadImage("assets/buttons/mainmenu.png"));
    private final ImageIcon selectMode = new ImageIcon(loadImage("assets/labels/selectMode.png"));
    private final ImageIcon xTurn = new ImageIcon(loadImage("assets/labels/xTurn.png"));
    private final ImageIcon oTurn = new ImageIcon(loadImage("assets/labels/oTurn.png"));
    private final ImageIcon xWinLabel = new ImageIcon(loadImage("assets/labels/xWin.png"));
    private final ImageIcon oWinLabel = new ImageIcon(loadImage("assets/labels/oWin.png"));
    private final ImageIcon tieGame = new ImageIcon(loadImage("assets/labels/tieGame.png"));
    private final ImageIcon aiTurn = new ImageIcon(loadImage("assets/labels/aiTurn.png"));
    private final ImageIcon aiLoss = new ImageIcon(loadImage("assets/labels/aiLoss.png"));
    private final ImageIcon empty = new ImageIcon(loadImage("assets/tiles/empty.png"));
    private final ImageIcon oImg = new ImageIcon(loadImage("assets/tiles/o.png"));
    private final ImageIcon xImg = new ImageIcon(loadImage("assets/tiles/x.png"));
    private final ImageIcon oWin = new ImageIcon(loadImage("assets/tiles/oWin.png"));
    private final ImageIcon xWin = new ImageIcon(loadImage("assets/tiles/xWin.png"));
    private final BufferedImage background = loadImage("assets/tiles/background.png");

    public TicTacToe(){
        super(520, 800, "Tic Tac Toe",false);

        setup();
        init();
        finish();
    }

    @Override
    public void onUpdate(){
        
        if (currentMode == 2 && currentPlayer == aiPlayer){
            
            if (aiMaxTick == 0){
                aiMaxTick = (int) (Math.random() * 30) + 15;
                return;
            }else{
                aiTick++;
            }

            if (aiTick > aiMaxTick && tilesLeft != 0 && !gameOver){

                aiMaxTick = 0;
                aiTick = 0;
                
                aiMakeMove();
            }
        }
        
        updateLabel();
    }
    
    private void aiMakeMove(){
        int player = currentPlayer == aiPlayer && aiPlayer == x ? o : x;
        
        Ai ai = new Ai(tile,aiPlayer,player);
        
        int r = ai.getRow();
        int c = ai.getCol();
        tile[r][c] = aiPlayer;
        
        if (aiPlayer == x){
            tiles[r][c].setIcon(xImg);
        }else if (aiPlayer == o){
            tiles[r][c].setIcon(oImg);
        }
        tilesLeft--;
        checkGame();
        updatePlayerTurn();
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.drawImage(background,10, 180, 500, 500,null); 
    }
    
    private void init(){
        setIconPath("assets/tictactoe.png");
        setComponents();
        
        for (int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                tiles[r][c] = createButton(150,150);
                tiles[r][c].setLocation(25 + (c * (160 )), 195 + (r * (160)));
                tiles[r][c].setBorderPainted(false);
                tiles[r][c].setContentAreaFilled(false);
                tiles[r][c].setIcon(empty);
                tiles[r][c].addActionListener(this);
                add(tiles[r][c]);
                
                tile[r][c] = 0;
            }
        }
        
    }
    
    private void setComponents(){
        vsPlayer = new JButton();
        vsPlayer.setBorderPainted(false);
        vsPlayer.setContentAreaFilled(false);
        vsPlayer.setSize(245,75);
        vsPlayer.setLocation(10,10);
        vsPlayer.setIcon(idleMode1);
        vsPlayer.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {modeChange(1);}});
        add(vsPlayer);
        
        vsAi = new JButton();
        vsAi.setBorderPainted(false);
        vsAi.setContentAreaFilled(false);
        vsAi.setSize(245,75);
        vsAi.setLocation(265,10);
        vsAi.setIcon(idleMode2);
        vsAi.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {modeChange(2);}});
        add(vsAi);
        
        label = new JLabel();
        label.setSize(500, 75);
        label.setLocation(10,95);
        add(label);
        
        reset = new JButton();
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setSize(245,75);
        reset.setLocation(10,690);
        reset.setIcon(resetImg);
        reset.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {reset();}});
        add(reset);
        
        menu = new JButton();
        menu.setBorderPainted(false);
        menu.setContentAreaFilled(false);
        menu.setSize(245,75);
        menu.setLocation(265,690);
        menu.setIcon(mainmenuImg);
        menu.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {toggleGame();}});
        add(menu);
    }
    
    private void action(int r, int c){
        
        if (!gameOver && tilesLeft != 0){

            if (tile[r][c] == 0 ){

                if (currentMode == 1) {
                
                    if (currentPlayer == x){

                        tile[r][c] = x;
                        tiles[r][c].setIcon(xImg);

                        tilesLeft--;

                        updatePlayerTurn();
                    }

                    else if (currentPlayer == o){

                        tile[r][c] = o;
                        tiles[r][c].setIcon(oImg);

                        tilesLeft--;

                        updatePlayerTurn();
                    }
                    
                }else if (currentMode == 2){
                    
                    if (currentPlayer != aiPlayer){
                        
                        tile[r][c] = currentPlayer;
                        
                        if (currentPlayer == x){
                            tiles[r][c].setIcon(xImg);
                        }else if (currentPlayer == o){
                            tiles[r][c].setIcon(oImg);
                        }
                        
                        tilesLeft--;
                        updatePlayerTurn();
                        
                    }
                    
                }
            }
        }
        checkGame();
    }
    
    private void checkGame(){
        
        for (int r = 0; r < 3; r++){
            if (tile[r][0] == x && tile[r][0] == tile[r][1] && tile[r][1] == tile[r][2]){
                setWinner(tile[r][0],r,0,r,1,r,2);
            }else if (tile[r][0] == o && tile[r][0] == tile[r][1] && tile[r][1] == tile[r][2]){
                setWinner(tile[r][0],r,0,r,1,r,2);
            }
        }
        for (int c = 0; c < 3; c++){
            if (tile[0][c] == x && tile[0][c] == tile[1][c] && tile[1][c] == tile[2][c]){
                setWinner(tile[0][c],0,c,1,c,2,c);
            }else if (tile[0][c] == o && tile[0][c] == tile[1][c] && tile[1][c] == tile[2][c]){
                setWinner(tile[0][c],0,c,1,c,2,c);
            }
        }
        
        if (tile[0][0] == x && tile[0][0] == tile[1][1] && tile[1][1] == tile[2][2]){
            setWinner(tile[0][0],0,0,1,1,2,2);
        } if (tile[0][0] == o && tile[0][0] == tile[1][1] && tile[1][1] == tile[2][2]){
            setWinner(tile[0][0],0,0,1,1,2,2);
        } if (tile[0][2] == x && tile[0][2] == tile[1][1] && tile[1][1] == tile[2][0]){
            setWinner(tile[0][2],0,2,1,1,2,0);
        } if (tile[0][2] == o && tile[0][2] == tile[1][1] && tile[1][1] == tile[2][0]){
            setWinner(tile[0][2],0,2,1,1,2,0);
        }
        
        if (tilesLeft == 0){
            gameOver = true;
        }
    }
    
    private void setWinner(int winner, int r1, int c1, int r2, int c2, int r3, int c3){
        gameOver = true;
        this.winner = winner;
        
        if (winner == x){
            tiles[r1][c1].setIcon(xWin);
            tiles[r2][c2].setIcon(xWin);
            tiles[r3][c3].setIcon(xWin);
        }if (winner == o){
            tiles[r1][c1].setIcon(oWin);
            tiles[r2][c2].setIcon(oWin);
            tiles[r3][c3].setIcon(oWin);
        }
    }
    
    private void modeChange(int mode){
        if (currentMode == 0){
            currentMode = mode;
            randomPlayer();
            if (mode == 1){
                vsPlayer.setIcon(selectedMode1);
            }else if (mode == 2){
                vsAi.setIcon(selectedMode2);
                
                if (currentPlayer == x){
                    aiPlayer = o;
                }else{
                    aiPlayer = x;
                }
            }
        }
        
        else if (currentMode == 1 && mode == 2){
            currentMode = mode;
            
            vsPlayer.setIcon(idleMode1);
            vsAi.setIcon(selectedMode2);
            
            reset();
            
            if (currentPlayer == x){
                aiPlayer = o;
            }else{
                aiPlayer = x;
            }
        }
        
        else if (currentMode == 2 && mode == 1){
            currentMode = mode;
            
            vsPlayer.setIcon(selectedMode1);
            vsAi.setIcon(idleMode2);
            
            reset();
        }
    }
    
    private void reset(){
        tilesLeft = 9;
        gameOver = false;
        winner = 0;
        
        aiTick = 0;
        aiMaxTick = 0;
        
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                tiles[r][c].setIcon(empty);
                tile[r][c] = 0;
            }
        }
        randomPlayer();
    }
    
    private void updateLabel(){
        
        if (currentMode == 0){
            label.setIcon(selectMode);
        }
        else if (gameOver && currentMode == 1){
            
            if (winner == x){
                label.setIcon(xWinLabel);
            }else if (winner == o){
                label.setIcon(oWinLabel);
            }else if (tilesLeft == 0){
                label.setIcon(tieGame);
            }
            
        }else if (currentMode == 1){
            
            if (currentPlayer == x){
                label.setIcon(xTurn);
            }else {
                label.setIcon(oTurn);
            }
            
        }
        
        else if (currentMode == 2){
            if (winner == aiPlayer){
                label.setIcon(aiLoss);
            }else if (gameOver && tilesLeft == 0){
                label.setIcon(tieGame);
            }
            
            else if (currentPlayer == aiPlayer){
                label.setIcon(aiTurn);
            }else{
                if (currentPlayer == x){
                    label.setIcon(xTurn);
                }else{
                    label.setIcon(oTurn);
                }
            }
            
        }
        
    }
    
    private void updatePlayerTurn(){
        if (currentPlayer == x){
            currentPlayer = o;
        }else if (currentPlayer == o){
            currentPlayer = x;
        }
    }
    
    private void randomPlayer(){
        
        int random = (int)(Math.random() * 2) + 1;
        if (random == x){
            currentPlayer = x;
        }else if (random == o){
            currentPlayer = o;
        }
    }

    public void actionPerformed(ActionEvent e) {
        
        Object obj = e.getSource();
    
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                
                if (tiles[r][c].equals(obj)){
                    action(r,c);
                }
                
            }
        }
    
    
    }
}
