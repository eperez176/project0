package example;

import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import java.io._;
import scala.io.StdIn.readLine;

object Hello extends App {
        var manuf_name = "";
        var model_name = "";
        var color = "";

        println("Loading the search queue...");
        val client: MongoClient = MongoClient();
        val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
        val collection: MongoCollection[Document] = database.getCollection("UsedCars");
        var opt = 0;
        println("\nSearch options include: Find (1), average (2)");
        opt = scala.io.StdIn.readInt();
        
        // Search functions
        if(opt == 1) {
            println("For unwanted options: Enter 'N/A' \n");

            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            model_name = scala.io.StdIn.readLine();
            val out = collection.find(equal("manufacturer_name", manuf_name)).results();
            println(out);
            println(manuf_name);
        }
}
