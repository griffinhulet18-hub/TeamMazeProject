/*
Team Name: G2
Contributors:
-Maram Algaradi

Description: Stores player position and allows movement inside the maze.
*/

public class Player {
    private int row;
    private int col;

    public Player(int startRow,int startCol){this.row=startRow;this.col=startCol;}

    public int getRow(){return row;}
    public int getCol(){return col;}

    // GUI
    public void moveUp(Maze maze){if(row>0 && maze.getGrid()[row-1][col]!='#') row--;}
    public void moveDown(Maze maze){if(row<maze.getGrid().length-1 && maze.getGrid()[row+1][col]!='#') row++;}
    public void moveLeft(Maze maze){if(col>0 && maze.getGrid()[row][col-1]!='#') col--;}
    public void moveRight(Maze maze){if(col<maze.getGrid()[0].length-1 && maze.getGrid()[row][col+1]!='#') col++;}

    // Console
    public void move(String dir, Maze maze){
        switch(dir.toUpperCase()){
            case "W": moveUp(maze); break;
            case "S": moveDown(maze); break;
            case "A": moveLeft(maze); break;
            case "D": moveRight(maze); break;
        }
    }
}
