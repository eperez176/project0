package  game;

import scala.io.StdIn.readLine;

case object Game { // Object having the game engines
    def tic_tac_toe() {
        println("Starting the tic tac toe game...");
        var gameOver = (false,-1);
        var botPosition = 0;
        var position = 0;
        var board = new Array[Char](9); // Creates the the simple board
        for(i <- 0 to 8){
            board(i) = ' ';
        }
        printBoard(board);
        do {
            println("Choose a valid spot:");
            position = pickPosition(board); // Valid position function
            board(position) = 'X';
            gameOver = gameFinish(board);
            if(!(gameOver._1)) { // To prevent bot from infinite loop if there are no spaces left
                println("Bot Chose")
                botPosition = botPick(board); // Valid position function for bot
                board(botPosition) = 'O';
                printBoard(board); // Print the end board of the turn
            }
        } while(!(gameOver._1));
        

    }
    def black_jack(){
        println("Starting the black jack game...")
    }

    // Prints the board for the game tic tac toe, to reduce lines at each stage
    def printBoard(b: Array[Char]): Unit = {
        for(i <- 0 to 2) {
	        print("\n ");
	        print(b(i*3));
	        print(" | ");
	        print(b(i*3 + 1));
	        print(" | ");
	        println(b(i*3 + 2));
        }
    }

    // Function to pick position for the bot
    def botPick(b: Array[Char]): Int = {
        var rand = scala.util.Random;
        var invalidNum = true;
        var pos = 0;
        do {
            pos = rand.nextInt(8); // Limit the range of random numbers
            if(b(pos) == ' ') { // Repeat till a blank space
                invalidNum = false;
            }
        } while(invalidNum);

        return pos;
    }

    // Function to pick a valid position for the player
    def pickPosition(b: Array[Char]): Int = {
        println("Starting the picking!")
        var pos = 0;
        var invalidNum = true;
        do {
            pos = scala.io.StdIn.readInt();
            if(b(pos-1) == ' ') {
                invalidNum = false;
            }
            else
                println("Invalid position, try again!")
        } while(invalidNum);

        return pos-1; // To stay within the bounds
    }

    // Function to decide if the game is over
    def gameFinish(b: Array[Char]): (Boolean, Int) = {
        var fullBoard = 0;
        for(i <- 0 to 8) {
            if(b(i) == 'X' || b(i) == 'O')
                fullBoard = fullBoard + 1;
        }

        if(fullBoard == 9)
            return (true, 0);
        else
            return (false, -1);
    }
}
