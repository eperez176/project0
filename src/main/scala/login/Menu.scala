package  login;

import scala.io.StdIn.readLine;

// Will create the menu and their functions
case object Menu {
    def initialOptions: Int = {
        var out = 0;
        var validOpt = false;

        do { // Request for a valid option
            println("What would you like to do:");
            println("Search(1), game (2)");
            out = scala.io.StdIn.readInt();
            if(out == 1 || out == 2)
                validOpt = true;
            else
                println("\nInvalid option. Try again!\n")
        } while(!validOpt);
        out;
    }
    def searchOptions: Unit = {
        println("Search options include: ");
    }

    def printLogo: Unit = {
        println("         000000000000000000000");
        println("        000        000      000");
        println("      0000        0000       0000");
        println(" 00000000000000000000000000000000000000000")
        println("0000000000 WELCOME  TO USED CARS! 000000000")
        println("  000000000000000000000000000000000000000");
        println("    00000000                  00000000");
        println("      0000                      0000");

    }
  
}
