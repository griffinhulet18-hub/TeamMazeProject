/*
Team Name: G2
Contributors:
- Maram Algaradi
- Amma Amma Mensah-Dwumfua
- Jada Thompson
- Griffin Hulet

Description: Displays the maze in a GUI and allows player movement with keyboard controls,
confetti celebration, and multiple levels.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MazeGUI extends JPanel {

    private Maze maze;
    private Player player;
    private int targetRow;
    private int targetCol;
    private final int CELL_SIZE=30;
    private boolean gameWon=false;
    private int level=1;
    private List<Enemy> enemies=new ArrayList<>();

    public MazeGUI(){
        generateLevel(level);

        setPreferredSize(new Dimension(maze.getGrid()[0].length*CELL_SIZE,
                maze.getGrid().length*CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        // Timer for enemy movement and animation
        Timer timer = new Timer(200,e->{
            if(gameWon) repaint();
            for(Enemy enemy: enemies) enemy.move(maze);
            checkCollision();
            repaint();
        });
        timer.start();

        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(gameWon) return;
                switch(e.getKeyCode()){
                    case KeyEvent.VK_W: case KeyEvent.VK_UP: player.moveUp(maze); break;
                    case KeyEvent.VK_S: case KeyEvent.VK_DOWN: player.moveDown(maze); break;
                    case KeyEvent.VK_A: case KeyEvent.VK_LEFT: player.moveLeft(maze); break;
                    case KeyEvent.VK_D: case KeyEvent.VK_RIGHT: player.moveRight(maze); break;
                }
                checkCollision();
                checkWin();
                repaint();
            }
        });
    }

    private void generateLevel(int lvl){
        maze = new Maze(15+lvl*2,20+lvl*2,lvl);
        player = new Player(1,1);
        targetRow = maze.getGrid().length-2;
        targetCol = maze.getGrid()[0].length-2;
        gameWon=false;
        generateEnemies(lvl);
    }

    private void generateEnemies(int lvl){
        enemies.clear();
        int numEnemies=lvl;
        int rows=maze.getGrid().length;
        int cols=maze.getGrid()[0].length;
        for(int i=0;i<numEnemies;i++){
            int r,c;
            do{
                r=1+(int)(Math.random()*(rows-2));
                c=1+(int)(Math.random()*(cols-2));
            } while((r==1 && c==1) || (r==targetRow && c==targetCol) || maze.getGrid()[r][c]=='#');
            enemies.add(new Enemy(r,c));
        }
    }

    private void checkCollision(){
        for(Enemy e: enemies){
            if(player.getRow()==e.getRow() && player.getCol()==e.getCol()){
                // Player hit enemy → restart level
                JOptionPane.showMessageDialog(null,"💀 You touched an enemy! Restarting level...");
                generateLevel(level);
                return;
            }
        }
    }

    private void checkWin(){
        if(player.getRow()==targetRow && player.getCol()==targetCol && !gameWon){
            gameWon=true;
            JOptionPane.showMessageDialog(null,"🎉 Congratulations! You reached the target! 🎉");
            int option=JOptionPane.showConfirmDialog(null,"Do you want to play the next level?","Next Level",JOptionPane.YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION){
                level++;
                generateLevel(level);
                JFrame topFrame=(JFrame) SwingUtilities.getWindowAncestor(this);
                if(topFrame!=null) topFrame.setTitle("Maze Game - Level "+level);
                revalidate();
            }
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        char[][] grid = maze.getGrid();

        for(int r=0;r<grid.length;r++){
            for(int c=0;c<grid[r].length;c++){
                if(grid[r][c]=='#') g.setColor(Color.DARK_GRAY);
                else g.setColor(Color.WHITE);
                g.fillRect(c*CELL_SIZE,r*CELL_SIZE,CELL_SIZE,CELL_SIZE);

                if(r==player.getRow() && c==player.getCol()){
                    g.setColor(Color.BLUE);
                    g.fillOval(c*CELL_SIZE,r*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                }

                if(r==targetRow && c==targetCol){
                    g.setColor(Color.RED);
                    g.fillOval(c*CELL_SIZE,r*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                }
            }
        }

        // Draw enemies as creatures
        for(Enemy e: enemies){
            int x = e.getCol()*CELL_SIZE;
            int y = e.getRow()*CELL_SIZE;

            // Body
            g.setColor(Color.GREEN.darker());
            g.fillOval(x+5, y+5, CELL_SIZE-10, CELL_SIZE-10);

            // Eyes
            g.setColor(Color.RED);
            g.fillOval(x+8, y+8, 5, 5);
            g.fillOval(x+CELL_SIZE-13, y+8, 5, 5);

            // Mouth/frown
            g.setColor(Color.BLACK);
            g.drawArc(x+7, y+12, CELL_SIZE-14, 8, 0, -180);
        }

        // Confetti on win only
        if(gameWon){
            for(int i=0;i<50;i++){
                int x=(int)(Math.random()*getWidth());
                int y=(int)(Math.random()*getHeight());
                g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
                g.fillOval(x,y,10,10);
            }
        }
    }

    public static void launchGUI(){
        JFrame frame=new JFrame("Maze Game - Level 1");
        MazeGUI panel=new MazeGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
