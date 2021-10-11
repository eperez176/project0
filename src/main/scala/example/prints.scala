package example;

// Imports
import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;

object prints extends App {
    def howdy: Unit = println("Howdy Yall");
    def no_howdy: Unit = println("No");

    val filename = new File("Check.txt"); // This allows the creation of the file.
    val user_writer = new FileWriter(filename, true); 
    user_writer.close();
}