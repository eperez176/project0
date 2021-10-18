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
        var option_2 = 0;

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
        if(opt == 2) { // Next adding if-else statements. Options include avg price to car manufcturer, model name, year (?)
          // Also add a function if they want more details about their search
          // Add everything together tmrw
            println("For unwanted options: Enter '0' \n");
            println("Average Price of Car Manufacturer (1) or Car Model (2)");
            option_2 = scala.io.StdIn.readInt();// Reads the option, limits the possibilities

          if(option_2 == 1)
            out = collection.aggregate((Seq(group("$manufacturer_name", sum("num",1),avg("avg_p", "$price_usd")), sort(ascending("avg_p"))))).results;
          else if(option_2 == 2) {
            out = collection.aggregate( Seq(group("$model_name",push("manu_n", "$manufacturer_name"), sum("num",1), avg("avg_p", "$price_usd")), sort(ascending("avg_p")))).results;
            
            for(single_doc <- out) {
              var id = single_doc.get("_id").get.asString().getValue();
              var count = single_doc.get("num").get.asInt32().getValue();
              var manu = single_doc.get("manu_n").get.asArray.get(0).asString().getValue();
              var avg_p = single_doc.get("avg_p").get.asDouble().getValue();
              println(s"$manu, Model: $id, Avg Price:" +" $" + f"$avg_p%.2f ($count)");
              //println(manu);
            }
          }
            //println(out);
        }
        client.close();
}
