package arcade;

import java.applet.Applet;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
public class Game extends JComponent {

    private JFrame frame;
    private JButton goMenu;
    
    private  boolean setMenu;

    private int width;
    private int height;
    private String title;
    private boolean isToggled;

    private String iconPath;
    
    private boolean isExtented = false;
    private String extendedTitle = "";
    
    private int tick;
    
    public Game(int width, int height, String title,boolean setMenu){
        init(width,height,title,setMenu);
    }
    
    public Game(int width, int height, String title, String extendedTitle, boolean setMenu){
        isExtented = true;
        this.extendedTitle = extendedTitle;
        init(width,height,title,setMenu);
    }
    
    private void init(int width, int height, String title, boolean setMenu){
        this.width = width;
        this.height = height;
        this.title = title;
        this.setMenu = setMenu;
        
        log("Starting Setup");
        
        tick = 0;
        isToggled = false;
        iconPath = "assets/default.png";
    }
    
    public void setup(){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().add(this);
        frame.setResizable(false);
        
        if (setMenu){
            addMenu();
        }
    }
    
    private void addMenu(){
        goMenu = new JButton("Main Menu");
        goMenu.setSize(100,100);
        goMenu.setLocation(25, 25);
        goMenu.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {toggleGame();}});
        add(goMenu);
    }
    
    public JButton createButton(int width, int height){
        JButton output = new JButton();
        output.setSize(width, height);
        return output;
    }

    public void finish(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        repaint();
        log("Setup finished");
    }

    public void showGame(){
        frame.setVisible(true);
    }

    public void hideGame(){
        frame.setVisible(false);
    }

    public String getTitle(){
        return title;
    }

    public JFrame getFrame(){
        return frame;
    }
    
    @Override
    public int getWidth(){
        return width;
    }
    
    @Override
    public int getHeight(){
        return height;
    }

    public JButton getMenuButton(){
        return goMenu;
    }
    
    public void forceState(boolean state){
        this.isToggled = state;
    }

    public void setState(boolean toggle){
        onToggle();

        if (toggle){
            Arcade.menu.log("Switching to ["+getTitle()+"]");
            onEnable();
            showGame();
            Arcade.menu.hideGame();
        }else{
            log("Switching to [Main Menu]");
            onDisable();
            hideGame();
            Arcade.menu.showGame();
        }

        isToggled = toggle;
        Arcade.menu.forceState(!toggle);
    }

    public void toggleGame(){
        setState(!isToggled());
    }

    public boolean isToggled(){
        return isToggled;
    }
    
    public void onToggle(){}
    public void onEnable(){}
    public void onDisable(){}
    
    public void onUpdate(){
        tick++;
    }
    
    public int getTick(){
        return tick;
    }
    
    public void resetTick(){
        tick = 0;
    }
    
    public void log(Object m){
        if(isExtented)
            System.out.println(getTime()+" ["+extendedTitle+"] ["+getTitle()+"] "+m);
        else
            System.out.println(getTime()+" ["+getTitle()+"] "+m);
    }

    public String getTime(){
        Date time = new Date();
        int hour = time.getHours();
        int min = time.getMinutes();
        int sec = time.getSeconds();
        return "["+(hour<10?"0"+hour:hour)+":"+(min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec)+"]";
    }
    
    public void setIconPath(String path){
        this.iconPath = path;
    }
    
    public String getIconPath(){
        return iconPath;
    }
    
    public BufferedImage loadImage(String path){
        try{
            return ImageIO.read(getClass().getResource(path));
        }catch (IOException e) {
            return null;
        }
    }
    
    public AudioClip loadClip(String path){
        return Applet.newAudioClip(getClass().getResource(path));
    }
    
}
