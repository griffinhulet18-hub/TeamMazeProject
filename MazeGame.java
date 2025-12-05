/*
Team Name: G2
Contributors:
- Maram Algaradi
- Amma Amma Mensah-Dwumfua
- Jada Thompson
- Griffin Hulet

Description: Console version of the maze game for user interaction.
*/

import java.util.Scanner;

public class MazeGame {
    private int level=1;
    private Scanner sc=new Scanner(System.in);

    public void start(){
        boolean playing=true;
        while(playing){
            int rows=15+level*2;
            int cols=20+level*2;
            Maze maze=new Maze(rows,cols,level);
            Player player=new Player(1,1);
            int targetRow=rows-2;
            int targetCol=cols-2;
            Enemy[] enemies=new Enemy[level];
            for(int i=0;i<level;i++){
                int r,c;
                do{
                    r=1+(int)(Math.random()*(rows-2));
                    c=1+(int)(Math.random()*(cols-2));
                }while((r==1 && c==1) || (r==targetRow && c==targetCol) || maze.getGrid()[r][c]=='#');
                enemies[i]=new Enemy(r,c);
            }

            boolean levelComplete=false;
            while(!levelComplete){
                maze.printMaze(player,enemies,targetRow,targetCol);
                System.out.println("Move with W/A/S/D: ");
                String move=sc.nextLine();
                player.move(move,maze);

                // Move enemies
                for(Enemy e: enemies) e.move(maze);

                // Check collision
                boolean hit=false;
                for(Enemy e: enemies){
                    if(player.getRow()==e.getRow() && player.getCol()==e.getCol()){
                        System.out.println("💀 You were caught! Restarting level!");
                        player=new Player(1,1);
                        hit=true; break;
                    }
                }
                if(hit) continue;

                // Check win
                if(player.getRow()==targetRow && player.getCol()==targetCol){
                    System.out.println("🎉 You reached the target! Level "+level+" complete! 🎉");
                    levelComplete=true;
                    System.out.println("Continue to next level? (Y/N)");
                    String ans=sc.nextLine();
                    if(ans.equalsIgnoreCase("Y")) level++;
                    else playing=false;
                }
            }
        }
        System.out.println("Thanks for playing!");
    }
}
