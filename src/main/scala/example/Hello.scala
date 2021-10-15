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
        var m_price = 0.0;

        println("Loading the search queue...");
        val client: MongoClient = MongoClient();
        val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
        val collection: MongoCollection[Document] = database.getCollection("smallCars");
        var opt = 0;
        println("\nSearch options include: Find (1), average (2)\n");
        opt = scala.io.StdIn.readInt();
        
        // Search functions
        if(opt == 1) {
            println("For unwanted options: Enter 'N/A' \n");

            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            model_name = scala.io.StdIn.readLine();
            println("Max Price")
            m_price = scala.io.StdIn.readDouble();
            val out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name), lt("price_usd", m_price))).results;
            for(single_doc <- out) {
              var mnn = single_doc.get("model_name").get.asString().getValue();
              var mn = single_doc.get("manufacturer_name").get.asString().getValue();
              var price = single_doc.get("price_usd").get.asDouble().getValue();
              var od_val = single_doc.get("odometer_value").get.asInt32().getValue();
              println(s"$mnn, $mn: Price: $price with odometer value: $od_val");
            }

        }
}
