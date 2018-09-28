package arcade.games.simon;

import java.util.ArrayList;

public class Pattern{
    
    private final ArrayList<Type> patterns;
    
    private boolean animate;
    private boolean last;
    private boolean blink;
    private boolean finish;
    private boolean playSound;
    private int current;
    private int tick;
    
    public Pattern(){
        patterns = new ArrayList<>();
        reset();
    }
    
    public final void reset(){
        patterns.clear();
        animate = false;
        last = false;
        blink = false;
        finish = false;
        playSound = false;
        tick = 0;
        current = 0;
    }
    
    public void addPattern(){
        playSound = true;
        switch ((int)(Math.random() * 4)){
            case 0:
                patterns.add(Type.GREEN);
                break;
            case 1:
                patterns.add(Type.RED);
                break;
            case 2:
                patterns.add(Type.BLUE);
                break;
            case 3:
                patterns.add(Type.YELLOW);
                break;
        }
    }
    
    public void onUpdate(){
        tick++;
        
        if (tick == 25){
            blink = true;
        }
        
        if (tick == 30 && !last){
            current++;
            playSound = true;
            blink = false;
            if (current == patterns.size() - 1)
                last = true;
            else
                tick = 0;
        }
        
        if (last){
            if (tick == 55){
                animate = false;
                last = false;
                finish = true;
                tick = 0;
                current = 0;
            }
        }
    }
    
    public void animate(){
        animate = true;
        current = 0;
        tick = 0;
    }
    public void resetFinished(){
        finish = false;
    }
    public void updateCurrent(){
        playSound = true;
        current++;
    }
    public Type getCurrent(){
        playSound = false;
        return patterns.get(current);
    }
    public boolean isBlinking(){
        return blink;
    }
    public boolean isAnimating(){
        return animate;
    }
    public boolean isFinished(){
        return !animate && finish;
    }
    public boolean isOverLimit(){
        return current == patterns.size();
    }
    public boolean shouldPlayClip(){
        return playSound;
    }
}
