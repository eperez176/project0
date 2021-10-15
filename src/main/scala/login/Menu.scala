package  login;

import java.io._;
import scala.io.StdIn.readLine;
import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import org.mongodb.scala.bson;

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
        var manuf_name = "";
        var model_name = "";
        var color = "";

        println("Loading the search queue...");
        val client: MongoClient = MongoClient();
        val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
        val collection: MongoCollection[Document] = database.getCollection("UsedCars");
        var opt = 0;
        println("Search options include: Find (1), average (2)");
        opt = initialOptions; // Ask for a valid option
        
        // Search functions
        if(opt == 1) {
            println("For unwanted options: Enter 'N/A' \n");

            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            manuf_name = scala.io.StdIn.readLine();
            val out = collection.find(equal("manufacturer_name", manuf_name)).results();
        }
    }

    def printLogo: Unit = { // Prints a welcome message
        println("               000000000000000000000");
        println("              000        000      000");
        println("-           0000        0000       0000");
        println("----   00000000000000000000000000000000000000000")
        println("----  0000000000 WELCOME  TO USED CARS! 000000000")
        println("----    000000000000000000000000000000000000000");
        println("-         00000000                  00000000");
        println("            0000                      0000");

    }
  
}
