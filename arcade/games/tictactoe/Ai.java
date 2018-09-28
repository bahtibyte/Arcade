package arcade.games.tictactoe;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;

public class Ai {

    private final ArrayList<Integer> bestRows = new ArrayList<>();
    private final ArrayList<Integer> bestCols = new ArrayList<>();
    
    private int ROW;
    private int COL;
    
    private final int empty = 0;
    private final int player;
    private final int opponent;
    
    
    private void setMove(){
        int ran = (int)(Math.random() * bestRows.size());
        
        ROW = bestRows.get(ran);
        COL = bestCols.get(ran);
        
    }
    
    public Ai(int[][] board, int aiPlayer,int human){
        player = aiPlayer;
        opponent = human;
        
        findBestMove(board);
        setMove();
    }
    
    public int getRow(){
        return ROW;
    }
    
    public int getCol(){
        return COL;
    }
    
    private boolean isMovesLeft(int[][] board){
        
        for (int  row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (board[row][col] == empty) return true;
            
        return false;
    }
    
    private int evaluate(int[][] board){
        
        for (int i = 0; i < 3; i++){
            
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]){
                
                if (board[i][0] == player) return +10;
                else if (board[i][0] == opponent) return -10;
                
            }
            
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]){
                
                if (board[0][i] == player) return +10;
                else if (board[0][i] == opponent) return -10;
                
            }
            
        }
        
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]){
            
            if (board[0][0] == player)return +10; 
            else if (board[0][0] == opponent) return -10;
            
        }
        
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]){
            
            if (board[0][2] == player) return +10;
            else if (board[2][0] == opponent) return -10;
        }
        
        return 0;
    }
    
    private int minimax(int[][] board, int depth, boolean isMax){
        
        int score = evaluate(board);
        
        if (score == 10){
            return score - depth;
        }
        
        if (score == -10){
            return score + depth;
        }
        
        if (isMovesLeft(board) == false){
            return 0;
        }
        
        if (isMax){
            
            int best = -1000;
            
            for (int row = 0; row < 3; row++){
                for (int col = 0; col < 3; col++){
                    
                    if (board[row][col] == empty){
                        
                        board[row][col] = player;
                        
                        best = max(best, minimax(board, depth+1, !isMax) );
                        
                        board[row][col] = empty;
                    }
                    
                }
            }
            
            return best;
            
        }
        
        else {
            int best = 1000;
            
            for (int row = 0; row < 3; row++){
                for (int col = 0; col < 3; col++){
                    
                    if (board[row][col] == empty){
                        
                        board[row][col] = opponent;
                        
                        best = min(best,minimax(board, depth+1, !isMax));
                        
                        board[row][col] = empty;
                        
                    }
                }
            }
            return best;
        }
    }
    
    private void findBestMove(int[][] board){
        
        int bestVal = -1000;
        
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                
                if (board[row][col] == empty){
                    
                    board[row][col] = player;
                    
                    int moveVal = minimax(board, 0, false);
                    
                    board[row][col] = empty;
                    
                    if (moveVal > bestVal){
                        
                        bestVal = moveVal;
                        bestRows.clear();
                        bestCols.clear();
                    }
                    if (moveVal == bestVal){
                        
                        bestRows.add(row);
                        bestCols.add(col);
                                
                    }
                }
                
            }
            
        }
    }
}
    
    
    
