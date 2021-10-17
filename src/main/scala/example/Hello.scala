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
        if(opt == 1) {
            println("For unwanted options: Enter '0' \n");

            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            model_name = scala.io.StdIn.readLine();
            println("Max Price")
            m_price = scala.io.StdIn.readDouble();

            /*val out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name), lt("price_usd", m_price))).results;
            for(single_doc <- out) {
              var manuf_n = single_doc.get("manufacturer_name").get.asString().getValue();
              var model_n = single_doc.get("model_name").get.asString().getValue();
              var price = single_doc.get("price_usd").get.asDouble().getValue();
              var year = single_doc.get("year_produced").get.asInt32().getValue();
            }
            */

              if(manuf_name != "0") {
                if (model_name != "0") {
                  if(m_price != 0) {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name), lte("price_usd",m_price))).results;
                  }
                  else {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name))).results;
                  }
                }
                else {
                  if(m_price != 0) {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), lte("price_usd",m_price))).results;
                  }
                  else {
                    out = collection.find(equal("manufacturer_name", manuf_name)).results;
                  }
                }
              }
              else {
                if(model_name != "0") {
                  if(m_price != 0) {
                    out = collection.find(and(equal("model_name", model_name), lte("price_usd",m_price))).results;
                  }
                  else {
                    out = collection.find(equal("model_name", model_name)).results;
                  }

                }
                else {
                  if(m_price != 0) {
                    out = collection.find(equal("price_usd", m_price)).results;
                  }
                  else { // All resuts
                    out = collection.find().results;
                  }
                }
              }

            // Prints the outcome of the search query  
            for(single_doc <- out) {
              var manuf_n = single_doc.get("manufacturer_name").get.asString().getValue();
              var model_n = single_doc.get("model_name").get.asString().getValue();
              var price = single_doc.get("price_usd").get.asDouble().getValue();
              var year = single_doc.get("year_produced").get.asInt32().getValue();
              println(s"$model_n, $manuf_n, $price, $year");
            }
        }
        client.close();
}
