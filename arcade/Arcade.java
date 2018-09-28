package arcade;

import arcade.games.menu.Menu;
import javax.swing.SwingUtilities;

public class Arcade {

    public static Menu menu;
    public static GameManager games;
    
    private static int ticks = 30;
    
    public Arcade(){
        games = new GameManager();
        menu = new Menu();

        run();
    }
    public static void setTicks(int tick){
        ticks = tick;
    }
    private void run(){
        new Thread("Arcade"){
            @Override
            public void run(){
                while (true){
                    if (menu.isToggled()){
                        menu.onUpdate();
                    }else{
                        for (Game game : games.getGames()){
                            if (game.isToggled()){
                                game.onUpdate();
                            }
                        }
                    }
                    try{
                        Thread.sleep(ticks);
                    }catch (Exception e){
                        System.out.println("["+getName()+"] Fatal Error (unable to pause)");
                    }
                }
            }
        }.start();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Arcade arcade = new Arcade();
            }
        });
    }
}