package arcade;

import arcade.games.simon.Simon;
import arcade.games.tictactoe.TicTacToe;

import java.util.ArrayList;

public class GameManager {

    private final ArrayList<Game> games;

    public GameManager(){
        games = new ArrayList<>();
        setGames();
    }

    public ArrayList<Game> getGames(){
        return games;
    }

    private void setGames(){
        games.add(new TicTacToe());
        games.add(new Simon());
    }

}
