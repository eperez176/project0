package  login;

import scala.io.StdIn.readLine;

// Will create the menu and their functions
case object Menu {
    def welcome: Unit = {
        println("Welcome!")
    }
    def options: Int = {
        var out = 0;

        println("What would you like to do:");
        println("Search(1), game (2)");
        out = scala.io.StdIn.readInt();
        out;
    }
  
}
