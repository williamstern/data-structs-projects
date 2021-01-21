/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    This file does one round of a game where a player has to reach a destiination while avoiding creatures a
    this file calculates the path for the creatures


 */

import java.io.*; // For file io
//import java.lang.*;
import java.util.Arrays; // for sort and copy array function
import java.util.Scanner; // for the scanner for the user input

class HW6 {
    public static void main(String args[]) throws Exception {

        String fileName = args[0]; // takes filename from command line

        File input = new File(fileName);
        BufferedReader inputReader = new BufferedReader(new FileReader(input)); // set up reader
        String line;
        line = inputReader.readLine(); // read the first line and split it
        String[] lineArr = line.split(" ");

        // calculate the num of rows and colums
        int numRow = Integer.parseInt(lineArr[0]);
        int numCol = Integer.parseInt(lineArr[1]);

        char[][] grid = new char[numRow][numCol]; // create the grid

        char currentChar;
        int zCount = 0; // the number of zombies to make queue later
        Character player = new Character('0', 0, 0); // need to initalize it here so stuff later does not freak out
        Character goal = new Character('*', 0, 0);
        Character[] zombQ = new Character[1]; // there will be at least 1 creature
        for (int r = 0; r < numRow; r++) { // r for row
            // This goes through the input and reads all the things and counts the creatres and creates player
            line = inputReader.readLine();
            for (int c = 0; c < numCol; c++) {
                currentChar = line.charAt(c);
                grid[r][c] = currentChar;

                if (currentChar == '0') {
                    // create player class
                    player = new Character('0', r, c);
                }

                else if (currentChar == '*') {
                    goal = new Character('*', 0, 0);
                    continue;
                }

                else if (currentChar == ' ' || currentChar == '#') {
                    continue;
                }

                else {
                    zCount += 1;
                    if (zCount <= 1) {
                        // if the first creture is found ad it to the array
                        // myArray = copyOf(myArray, myNewSize);
                        Character zombie = new Character(currentChar, r, c);
                        zombQ[0] = zombie;

                    }

                    else {
                        // else make array one bigger and copy it over and sort
                        zombQ = Arrays.copyOf(zombQ, zCount);
                        Character zombie = new Character(currentChar, r, c);
                        zombQ[zCount - 1] = zombie;
                        Arrays.sort(zombQ);
                    }
                }

            }
        }
        inputReader.close();
        //char[][] gridCopy = grid; // for use in printing later nvm :sunglasses:

        // print the grid
        displayBoard(grid);

        // user input
        boolean validMove = false;
        boolean gameEnded = false;
        char move = ' ';
        Scanner reader = new Scanner(System.in);
        while (!gameEnded) {
            validMove = false;
            while (!validMove) { // while not a valid move
                System.out.print("Player 0, please enter your move [u(p), d(own), l(elf), or r(ight)]: ");
                // read in move
                
                char userInput = reader.next().charAt(0);
                // check if its valid and if it is then move there
                if (isValidMove(grid, userInput, player) == 1) {
                    move = moveUser(grid, userInput, player, goal);
                    validMove = true;
                }
                
            }
        
            // print grid again
            displayBoard(grid);

            if (move == '*') {
                gameEnded = true;
                System.out.print("Player 0 beats the hungry creatures!");
                break;
            }

            if (move >= 'A' && move <= 'Z') {
                gameEnded = true;
                System.out.print("One creature is not hungry any more!");
                break;
            }

            // move all the zombies
            //System.out.println(zombQ.length);
            for (int i = 0; i < zombQ.length; i++) {
                // calculaates all the paths for the creatures
                char[][] gridCopy = copyOf(grid);
                SpanTree tree = new SpanTree(gridCopy);
                Node end = tree.bSearch(zombQ[i], player.xCord, player.yCord);
                if (end != null) { // prints path if its not null
                    char movement = printCords(end, zombQ[i]);
                    move = moveUser(grid, movement, zombQ[i], goal);
                    displayBoard(grid);
                }

                else { // prints blank
                    System.out.println("Creature " + zombQ[i].name + ":");
                    displayBoard(grid);
                }

                if (move == '0') {
                    System.out.print("One creature is not hungry any more!");
                    gameEnded = true;
                    break;
                }

            }
        }

        //displayBoard(grid);
        reader.close();
        
    }

    // This creates a copy of a 2d array so it is not reused for each span tree instance
    public static char[][] copyOf(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }

    // prints the board
    public static void displayBoard(char[][] grid) {
        for(int x = -1; x < grid.length; x++) {
            for (int y = -1; y < grid[0].length +1; y++) {
                if (y < 0 && x >= 0) {
                    System.out.print(x);
                }

                else if (x < 0 && y == 0) {
                    System.out.print(" ");
                }
                else if (x < 0 && y > 0) {
                    System.out.print(y-1); 
                }

                else if (y >= 0 && x >= 0 && x <grid.length && y < grid[0].length) {
                    System.out.print(grid[x][y]);
                }
            }
            System.out.print("\n");
        }
    }

    // THis prints the cordnates of a creature
    public static char printCords(Node current, Character creature) {
        char direction = 'e';
        String printStr = "";
        int len = current.getDistance();
        while(current != null) {
            printStr = "(" + current.getX() +"," + current.getY() + ") " + printStr; // add new cord to start of string
            if (current.getParent() != null && current.getParent().getParent() == null) {
                direction = current.getDirection();
            }
            current = current.getParent();
        }
        printStr = "Creature" +" "+ creature.name +": "+direction +" "+len +" " + printStr; 
        System.out.println(printStr);
        return direction;
    }

    // This moves the user to the new place
    // switches cordnates in grid
    public static char moveUser(char[][] grid, char direction, Character player, Character goal) {
        int currentX = player.xCord;
        int currentY = player.yCord;

        if (direction == 'u') {
            currentX -= 1;
        }

        else if (direction == 'd') {
            currentX += 1;
        }

        else if (direction == 'l') {
            currentY -= 1;
        }

        else if (direction == 'r') {
            currentY += 1;
        }
        char result = grid[currentX][currentY];
        // swap
        if (goal.xCord == player.xCord && goal.yCord == player.yCord) {
            grid[player.xCord][player.yCord] = '*';
            grid[currentX][currentY] = player.name;
        }

        if (grid[currentX][currentY] >= 'A' && grid[currentX][currentY] <= 'Z') {
            grid[player.xCord][player.yCord] = ' ';
            grid[currentX][currentY] = grid[currentX][currentY];
        }
        
        else {
            grid[player.xCord][player.yCord] = ' ';
            grid[currentX][currentY] = player.name;
        }
        player.xCord = currentX;
        player.yCord = currentY;

        return result;

    }

    // This returns an int based on if a move for the palyer is valid or not
    // 1 if it is
    // 0 if not
    public static int isValidMove(char[][] grid, char direction, Character player) {
        int currentX = player.xCord;
        int currentY = player.yCord;
        if (direction == 'u') {
            currentX -= 1;
        }

        else if (direction == 'd') {
            currentX += 1;
        }

        else if (direction == 'l') {
            currentY -= 1;
        }

        else if (direction == 'r') {
            currentY += 1;
        }

        else {
            return 0;
        }

        if (grid[currentX][currentY] != '#') {
            return 1;
        }

        else {
            return 0;
        }

    }
}