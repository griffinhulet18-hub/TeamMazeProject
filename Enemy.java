/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mazeprojectg2;

/**
 *
 * @Jada Thompson
 */
/*
 Team Name: G2
 Contributors:
 - Maram Algaradi
 - Amma Amma Mensah-Dwumfua
 - Jada Thompson
 - Griffin Hulet

 Description: Represents a hazard in the maze.
*/

public class Enemy {

    // The enemy's current row position.
    private int row;

    // The enemy's current column position.
    private int col;


    // Creates an enemy at a specific row and column.
    public Enemy(int row, int col) {
        this.row = row;
        this.col = col;
    }


    // Returns the enemy's row position.
    public int getRow() {
        return row;
    }


    // Returns the enemy's column position.
    public int getCol() {
        return col;
    }


    // Attempts to move the enemy one step in a random direction.
    public void move(Maze maze) {
        int dir = (int) (Math.random() * 4);
        int newRow = row;
        int newCol = col;

        switch (dir) {
            case 0 -> newRow = row - 1;
            // Up
            case 1 -> newRow = row + 1;
            // Down
            case 2 -> newCol = col - 1;
            // Left
            case 3 -> newCol = col + 1;
            // Right
        }

        // Checks the new position is inside bounds and not a wall.
        if (newRow > 0 && newRow < maze.getGrid().length - 1 &&
            newCol > 0 && newCol < maze.getGrid()[0].length - 1 &&
            maze.getGrid()[newRow][newCol] != '#') {

            row = newRow;
            col = newCol;
        }
    }
}
