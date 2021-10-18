package example;

// Imports
import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;
import game.Game;
// Tests certain functions seperately 
object prints {
    def howdy: Unit = println("Howdy Yall");
    def no_howdy: Unit = println("No");
    Game.tic_tac_toe();
    /* 
    val filename = new File("Check.txt"); // This allows the creation of the file.
    val user_writer = new FileWriter(filename, true); 
    user_writer.write("I eat burgers.")
    user_writer.close(); */
}