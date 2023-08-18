import java.util.Scanner;

public class MazeRunnerGame {
    private char[][] maze;
    private int playerRow;
    private int playerCol;
    private int numberOfSteps;
    private int score;
    private int highScore;
    private boolean gameOver;
    private Scanner scanner;

    public MazeRunnerGame() {
        initializeGame();
        scanner = new Scanner(System.in);
    }

    private void initializeGame() {
        // Initialize the maze and player's starting position here
        // ...

        playerRow = 1;
        playerCol = 1;
        numberOfSteps = 0;
        score = 0;
        highScore = 0;
        gameOver = false;
    }

    public static void main(String[] args) {
        MazeRunnerGame game = new MazeRunnerGame();
        game.runMainMenu();
    }

    private void runMainMenu() {
        boolean exitGame = false;
        while (!exitGame) {
            displayMainMenu();
            int option = getUserChoice();
            switch (option) {
                case 1:
                    playGame();
                    break;
                case 2:
                    showInstructions();
                    break;
                case 3:
                    showCredits();
                    break;
                case 4:
                    showHighScore();
                    break;
                case 5:
                    exitGame();
                    exitGame = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Thanks for playing my game");
    }

    private void displayMainMenu() {
        System.out.println("===== Maze Runner Main Menu =====");
        System.out.println("1. Play Game");
        System.out.println("2. Instructions");
        System.out.println("3. Credits");
        System.out.println("4. High Score");
        System.out.println("5. Exit");
        System.out.println("===============================");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            System.out.print("Enter your choice: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private void playGame() {
        System.out.println("Welcome to Maze Runner! Find your way to the exit (E). Good luck!");
        long startTime = System.currentTimeMillis();

        while (!gameOver) {
            printMaze();
            handlePlayerMovement();

            if (hasPlayerWon()) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                score = calculateScore(elapsedTime);
                System.out.println("Congratulations! You reached the exit. You win!");
                displayResult();
                if (startNewGame()) {
                    initializeGame();
                    startTime = System.currentTimeMillis();
                } else {
                    gameOver = true;
                }
            }

            numberOfSteps++;
        }
    }

    private void printMaze() {
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void handlePlayerMovement() {
        System.out.print("Enter your move (W/A/S/D): ");
        char move = scanner.next().charAt(0);
        long startTime = System.currentTimeMillis();

        switch (move) {
            case 'W':
            case 'w':
                movePlayer(-1, 0);
                break;
            case 'A':
            case 'a':
                movePlayer(0, -1);
                break;
            case 'S':
            case 's':
                movePlayer(1, 0);
                break;
            case 'D':
            case 'd':
                movePlayer(0, 1);
                break;
            default:
                System.out.println("Invalid move. Please try again.");
                handlePlayerMovement();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (elapsedTime > 10000) {
            System.out.println("Time's up! You took more than 10 seconds for this move.");
            displayResult();
            if (startNewGame()) {
                initializeGame();
            } else {
                exitGame();
            }
        }
    }

    private void movePlayer(int rowOffset, int colOffset) {
        int newRow = playerRow + rowOffset;
        int newCol = playerCol + colOffset;

        if (maze[newRow][newCol] == 'E') {
            System.out.println("Congratulations! You reached the exit. You win!");
            displayResult();
            if (startNewGame()) {
                initializeGame();
            } else {
                gameOver = true;
            }
            return;
        }

        if (isValidMove(newRow, newCol)) {
            maze[playerRow][playerCol] = '.';
            playerRow = newRow;
            playerCol = newCol;
            maze[playerRow][playerCol] = 'P';
        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] != '#';
    }

    private boolean hasPlayerWon() {
        return maze[playerRow][playerCol] == 'E';
    }

    private int calculateScore(long elapsedTime) {
        return 1000 - (int) (elapsedTime / 100) - (numberOfSteps * 10);
    }

    private void displayResult() {
        System.out.println("Number of Steps: " + numberOfSteps);
        System.out.println("Score: " + score);
        if (numberOfSteps > highScore) {
            highScore = score;
        }
    }

    private void exitGame() {
        System.out.println("Thanks for playing Maze Runner! Goodbye!");
        gameOver = true;
    }

    private void showInstructions() {
        System.out.println("=== Instructions ===");
        System.out.println("Navigate the maze using the following keys:");
        System.out.println("W: Move up");
        System.out.println("A: Move left");
        System.out.println("S: Move down");
        System.out.println("D: Move right");
        System.out.println("Reach the exit point (E) to win!");
        System.out.println("====================");
    }

    private void showCredits() {
        System.out.println("=== Credits ===");
        System.out.println("Game developed by Salman Naqvi");
        System.out.println("Version 1.0");
        System.out.println("================");
    }

    private void showHighScore() {
        System.out.println("=== High Score ===");
        System.out.println("High Score: " + highScore);
        System.out.println("===================");
    }

    private boolean startNewGame() {
        System.out.print("Do you want to play again? (Y/N): ");
        String response = scanner.next().trim().toLowerCase();
        if (response.equals("y") || response.equals("yes")) {
            return true;
        } else if (response.equals("n") || response.equals("no")) {
            System.out.println("Thanks for playing Maze Runner! Goodbye!");
            return false;
        } else {
            System.out.println("Invalid response. Please enter 'Y' or 'N'.");
            return startNewGame();
        }
   
