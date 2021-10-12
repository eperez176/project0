package  game;

case object Game { // Object having the game engines
    def tic_tac_toe() {
        println("Starting the tic tac toe game...");
        var board = Array.ofDim[Char](3,3); // Creates the the simple board
        for(i <- 0 to 2){
            for(j <- 0 to 2)
                board(i)(j) = 'O';
        }
        printBoard(board);

    }
    def black_jack(){
        println("Starting the black jack game...")
    }

    // Prints the board for the game tic tac toe, to reduce lines at each stage
    def printBoard(b: Array[Array[Char]]): Unit = {
        println("");
        print(" " + b(0)(0));
        print(" | ");
        print(b(0)(1));
        print(" | ");
        println(b(0)(2));
        println("-----------")
        print(" " + b(1)(0));
        print(" | ");
        print(b(1)(1));
        print(" | ");
        println(b(1)(2));
        println("-----------")
        print(" " + b(2)(0));
        print(" | ");
        print(b(2)(1));
        print(" | ");
        println(b(2)(2));
        println();
    }
}
