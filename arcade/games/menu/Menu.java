package arcade.games.menu;

import arcade.Arcade;
import arcade.Game;
import java.awt.Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Menu extends Game{

    private final int width = 700;
    private final int height = 900;
    
    public Menu(){
        super(700,900,"Main Menu",false);

        setup();
        init();
        finish();
        forceState(true);
        showGame();
        repaint();
    }
    
    private void init(){
        int i = 1;

        for (final Game game : Arcade.games.getGames()){

            JButton button = new JButton();
            button.setSize(400,100);
            button.setLocation(width / 2 - (button.getWidth() / 2),60 + (125 * i));
            button.setIcon(new ImageIcon(loadImage(game.getIconPath())));
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.toggleGame();
                }
            });
            add(button);

            i++;
        }
        
        JButton quit = createButton(400,100);
        quit.setIcon(new ImageIcon(loadImage("assets/quit.png")));
        quit.setLocation(width / 2 - (quit.getWidth() / 2), height - 150);
        quit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {System.exit(0);}});
        add(quit);
    }

    @Override
    public void paintComponent(Graphics g){
        BufferedImage title = loadImage("assets/title.png");
        g.drawImage(loadImage("assets/background.jpg"), 0, 0, width,height,null);
        g.drawImage(title, width / 2 - (title.getWidth() / 2), 10, title.getWidth(), title.getHeight(), null);
    }


}
