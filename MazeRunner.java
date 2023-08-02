package Assignment1;

import java.util.Scanner;

public class MazeRunner {
    public static int currentPlayerX;
    public static int currentPlayerY;
    public static int score = 0;
    public static int highscore = 15;

    public static void main(String[] args) {
        System.out.println("WELCOME TO MAZE RUNNER GAME");
        boolean menuCheck = true;

        while (menuCheck) {
            System.out.println("\n===== Main Menu =====");
            System.out.println("Select any option from the following to start up with the game: ");
            System.out.println("1. Play Game");
            System.out.println("2. Instructions");
            System.out.println("3. Credits");
            System.out.println("4. High Score");
            System.out.println("5. Exit");
  
            
            Scanner scan = new Scanner(System.in);
            int input = scan.nextInt();

			if (input == 1) {
                // Start the game stime represents start time of the game and etime end time of the game 
                long stime = System.currentTimeMillis();
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        char maze[][] = new char[7][7];
                        maze = initializeMaze();
                        printMaze(maze);
                        playGame(maze);
                        maze = initializeMaze();
                        long etime = System.currentTimeMillis();
                        long timeTaken = (etime - stime) / 6000;
                    }
                });
             // Wait for up to 60 seconds for the thread to finish
                thread.start();
                try {
                    thread.join(60000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (thread.isAlive()) {
                    thread.interrupt();
                    System.out.println("You did not enter a string within the given time limit.");
                }
            } else if (input == 2) {
            	
                // Display game instructions
                System.out.println("The maze will consist of walls (#), which are impassable obstacles, and open paths (.) that you can move through. Your starting position will be marked as 'P', and the exit point as 'E'.");
                System.out.println("The goal is to reach the exit point (E) in the shortest number of moves possible.");
                System.out.println("As the player navigates through the maze, the game will keep track of the number of paths the player takes to reach the exit.");
                System.out.println("W: Move up.\r\n" + "A: Move left.\r\n" + "S: Move down.\r\n" + "D: Move right.");
                System.out.println("The player's score is determined by the total number of moves (paths) taken to complete the maze.");
                System.out.println("The lower the number of moves taken to reach the exit, the higher the player's score.");
                System.out.println("After each game session, the game will display the player's score and the current high score achieved.");
                System.out.println("If the player manages to complete the maze in fewer moves than the previous high score then the high score will be updated");
            } else if (input == 3) {
            	
                // Display game development credits
                System.out.println("Rida Noor developed the game");
                System.out.println("Roll number is 241548037");
            } else if (input == 4) {
            	
                // Display high score achieved so far in the game
                highScore();
            } else if (input == 5) {
            	
                // Exit the game application
                menuCheck = false;
                System.out.println("THANK YOU! Exiting the game. Goodbye!");
            } else {
            	//Wrong Input
                System.exit(0);
            }
        }
    }

    // Method to update the high score
    public static void updateScore() {
        highscore = score;
    }
    {
        System.out.println("");
    }

    // Method to display the high score
    public static void highScore() {
        System.out.println("High score:" + highscore);
    }

    // Main game logic
    public static void playGame(char maze[][]) {
        currentPlayerX = 1;
        currentPlayerY = 1;
        maze[currentPlayerX][currentPlayerY] = 'P';
        score = 0;
        printMaze(maze);
        boolean play_game_check = true;
        while (play_game_check) {
            play_game_check = movePlayer(maze);
        }
        if (score < highscore) {
            updateScore();
            System.out.println("You scored the highest marks " + score);
        } else {
            System.out.println("The score of the player is " + score);
            System.out.println("Highest score of the game is " + highscore);
        }
    }

    // Method to handle player movement
    public static boolean movePlayer(char maze[][]) {
        boolean move = true;
        boolean userinput = true;
        char move_Input = '0';
        while (move) {
            while (userinput) {
                System.out.println("Please enter player to move ");
                System.out.println("W: Move up.\r\n" + "A: Move left.\r\n" + "S: Move down.\r\n" + "D: Move right.");
                
                Scanner scan = new Scanner(System.in);
                move_Input = scan.next().charAt(0);
                score++;
                if (move_Input == 'W' || move_Input == 'A' || move_Input == 'S' || move_Input == 'D') {
                    userinput = false;
                } else {
                    System.out.println("Invalid direction.");
                }
            }
            int oldr = currentPlayerX;
            int oldc = currentPlayerY;
            if (move_Input == 'W') {
               currentPlayerX--;
            } else if (move_Input == 'A') {
                currentPlayerY--;
            } else if (move_Input == 'S') {
                currentPlayerX++;
            } else if (move_Input == 'D') {
                currentPlayerY++;
            }
            move = _isValidMove(maze, move_Input);
            if (move == false) {
                maze = initializeMaze();
                maze[currentPlayerX][currentPlayerY] = 'P';
                System.out.println("\n.repeat(10)");
                printMaze(maze);
                System.out.println("The score of the User score = " + score);
            } else {
                currentPlayerX = oldr;
                currentPlayerY = oldc;
                maze = initializeMaze();
                maze[currentPlayerX][currentPlayerY] = 'P';
                printMaze(maze);
                System.out.println("User score = " + score);
                userinput = true;
            }
        }
        return hasPlayerwon();
    }

    // Method to check if the player has won the game
    public static boolean hasPlayerwon() {
        if (currentPlayerX == 5 && currentPlayerY == 5) {
            System.out.println("Player has Won ");
            return false;
        } else return true;
    }

    // Method to check if the move is valid
    public static boolean _isValidMove(char maze[][], char i) {
        if (maze[currentPlayerX][currentPlayerY] != '#')
            return false;
        else {
            System.out.println("\n".repeat(10));
            System.out.println("Invalid move. You hit a wall or went outside the maze.");
            return true;
        }
    }

    // Method to print the maze
    public static void printMaze(char maze[][]) {
        System.out.println("        MAZE GAME      ");
        for (int i = 0; i < 7; i++) {
            System.out.print("      ");
            for (int j = 0; j < 7; j++) {
                System.out.print(" " + maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to initialize the maze with the starting layout
    public static char[][] initializeMaze() {
    	char[][] maze = new char[][]{
    		 {'#', '#', '#', '#', '#', '#', '#'},
             {'#', 'P', '.', '.', '.', '.', '#'},
             {'#', '#', '#', '#', '.', '#', '#'},
             {'#', '.', '.', '.', '.', '.', '#'},
             {'#', '#', '#', '.', '#', '#', '#'},
             {'#', '.', '.', '.', '.', 'E', '#'},
             {'#', '#', '#', '#', '#', '#', '#'}
        };
        return maze;
    }
}
