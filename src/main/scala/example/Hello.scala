package example;

import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import java.io._;
import scala.io.StdIn.readLine;
import org.mongodb.scala.model.Sorts._;
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Accumulators._

object Hello extends App {
        var manuf_name = "";
        var model_name = "";
        var color = "";
        var m_price = 0.0;
        var year_p = 0;

        println("Loading the search queue...");
        val client: MongoClient = MongoClient();
        val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
        val collection: MongoCollection[Document] = database.getCollection("smallCars");
        var opt = 0;
        println("\nSearch options include: Find (1), average (2)\n");
        opt = scala.io.StdIn.readInt();
        var out = collection.find().results; // Pre-load to have variable set
        // Search functions
        if(opt == 2) {
            println("For unwanted options: Enter '0' \n");
            println("What average?")
            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            model_name = scala.io.StdIn.readLine();
            println("Max Price")
            m_price = scala.io.StdIn.readDouble();

            out = collection.aggregate( Seq(group("$model_name",avg("avg_p", "$price_usd")))).results;
            
            for(single_doc <- out) {
              var id = single_doc.get("_id").get.asString().getValue();
              var avg_p = single_doc.get("avg_p").get.asDouble().getValue();
              println(s"Model: $id, Avg Price:" +" $" +s"$avg_p ");
            } 

            //println(out);
        }
        client.close();
}
