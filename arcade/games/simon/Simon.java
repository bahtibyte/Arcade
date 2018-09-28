package arcade.games.simon;

import arcade.Arcade;
import arcade.Game;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Simon extends Game implements MouseListener{
    
    private JButton reset, menu;
    private BufferedImage simon,gi,ga,ri,ra,bi,ba,yi,ya;
    private ImageIcon resetIcon,startIcon,menuIcon;
    private AudioClip greenClip, redClip, blueClip, yellowClip,awwClip;
    
    private final Pattern pattern;
    private boolean started = false;
    
    public Simon() {
        super(800,800,"Simon Says",false);
        setup();
        loadAssets();
        init();
        finish();
        
        pattern = new Pattern();
        createIdle();
    }
    
    private void action(Type type){
        if (!pattern.isOverLimit()){
            if (type == pattern.getCurrent()){
                createImage(pattern.getCurrent());
                playSound(type);
                pattern.updateCurrent();
                resetTick();
            }else{
                awwClip.play();
                reset();
            }
            
        }
    }
    
    @Override
    public void onUpdate(){
        super.onUpdate();
        
        if (!started)
            return;
        
        if (!pattern.isAnimating()){
            if (pattern.isOverLimit() && getTick() >= 35){
                pattern.addPattern();
                pattern.animate();
            }
        }else{
            pattern.onUpdate();
            if (pattern.isBlinking())
                createIdle();
            else if (!pattern.isOverLimit()){
                if (pattern.shouldPlayClip())
                    playSound(pattern.getCurrent());
                createImage(pattern.getCurrent());
            }
        }

        if (getTick() == 25)
            createIdle();

        if (pattern.isFinished()){
            pattern.resetFinished();
            createIdle();
        }
        
    }
    
    private void start(){
        reset.setIcon(resetIcon);
        started = true;
        pattern.reset();
        pattern.addPattern();
        pattern.addPattern();
        pattern.animate();
    }
    
    private void reset(){
        started = false;
        reset.setIcon(startIcon);
        createIdle();
    }
    
    private void playSound(Type type) {
        allOff();
        if (type == Type.GREEN)
            greenClip.play();
        if (type == Type.RED)
            redClip.play();
        if (type == Type.BLUE)
            blueClip.play();
        if (type == Type.YELLOW)
            yellowClip.play();
    }
    
    private void allOff(){
        greenClip.stop();
        redClip.stop();
        blueClip.stop();
        yellowClip.stop();
    }
    
    private void init() {
        getFrame().setUndecorated(true);
        getFrame().setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
        getFrame().addMouseListener(this); 
        
        reset = createButton(180,75);
        reset.setLocation((getWidth() - 180) / 2, (getHeight() - 180) / 2);
        reset.setIcon(startIcon);
        reset.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e) {if (!started)start();else reset();}});
        add(reset);
        
        menu = createButton(180,75);
        menu.setLocation((getWidth() - 180) / 2, (getHeight() - 180) / 2 + 100);
        menu.setIcon(menuIcon);
        menu.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){toggleGame();}});
        add(menu);
    }
    
    @Override
    public void onEnable(){
        Arcade.setTicks(20);
        log("@Enable Changed ticks to 20");
    }
    
    @Override
    public void onDisable(){
        Arcade.setTicks(30);
        log("@Disable Changed ticks to 30");
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(simon,0,0,800,800,null);
    }
    
    private void createIdle(){
        BufferedImage output = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.drawImage(gi,000,000,400,400,null);
        g2.drawImage(ri,400,000,400,400,null);
        g2.drawImage(bi,400,400,400,400,null);
        g2.drawImage(yi,000,400,400,400,null);
        g2.dispose();
        
        simon = output;
        repaint();
    }
    
    private void createImage(Type type){
        BufferedImage output = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        
        g2.drawImage(gi,000,000,400,400,null);
        g2.drawImage(ri,400,000,400,400,null);
        g2.drawImage(bi,400,400,400,400,null);
        g2.drawImage(yi,000,400,400,400,null);
        
        if (type == Type.GREEN)
            g2.drawImage(ga,000,000,400,400,null);
        if (type == Type.RED)
            g2.drawImage(ra,400,000,400,400,null);
        if (type == Type.BLUE)
            g2.drawImage(ba,400,400,400,400,null);
        if (type == Type.YELLOW)
            g2.drawImage(ya,000,400,400,400,null);
        
        g2.dispose();
        
        simon = output;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (pattern.isAnimating())
            return;
        
        int rgb = simon.getRGB(e.getX(), e.getY());
        
        if (rgb ==-4194369 || rgb == -16711936)
            action(Type.GREEN);
        if (rgb == -32640 || rgb == -65536)
            action(Type.RED);
        if (rgb == -8342529 || rgb == -16750849)
            action(Type.BLUE);
        if (rgb == -65 || rgb == -256)
            action(Type.YELLOW);
    }
    
     private void loadAssets(){
        resetIcon = new ImageIcon(loadImage("assets/reset.png"));
        startIcon = new ImageIcon(loadImage("assets/start.png"));
        menuIcon = new ImageIcon(loadImage("assets/menu.png"));
        gi = loadImage("assets/greenIdle.png");
        ga = loadImage("assets/greenActive.png");
        ri = loadImage("assets/redIdle.png");
        ra = loadImage("assets/redActive.png");
        bi = loadImage("assets/blueIdle.png");
        ba = loadImage("assets/blueActive.png");
        yi = loadImage("assets/yellowIdle.png");
        ya = loadImage("assets/yellowActive.png");
        
        greenClip = loadClip("assets/sound/green.wav");
        redClip = loadClip("assets/sound/red.wav");
        blueClip = loadClip("assets/sound/blue.wav");
        yellowClip = loadClip("assets/sound/yellow.wav");
        awwClip = loadClip("assets/sound/aww.wav");
        
        setIconPath("assets/simonsays.png");
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
}
