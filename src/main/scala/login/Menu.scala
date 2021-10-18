package  login;

import java.io._;
import scala.io.StdIn.readLine;
import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import org.mongodb.scala.bson;
 import org.mongodb.scala.model.Aggregates._
 import org.mongodb.scala.model.Accumulators._
 import org.mongodb.scala.model.Filters._
 import org.mongodb.scala.model.Projections._
 import org.mongodb.scala.model.Sorts._;

// Will create the menu and their functions
case object Menu {
    def initialOptions: Int = {
        var out = 0;
        var validOpt = false;

        do { // Request for a valid option
            println("What would you like to do:");
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
        var m_price = 0.0;
        var year_p = 0;

        println("Loading the search queue...");
        
        val client: MongoClient = MongoClient();
        val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
        val collection: MongoCollection[Document] = database.getCollection("UsedCars");
        var out = collection.find().results; // Pre-load to have variable set
        var opt = 0;
        println("\nSearch options include: Find (1), average (2)\n");
        opt = initialOptions; // Ask for a valid option
        
        // Search functions
        if(opt == 1) {

            println("For unwanted options: Enter '0' \n");

            println("Car Manufacturer");
            manuf_name = scala.io.StdIn.readLine();
            println("Car Model")
            model_name = scala.io.StdIn.readLine();
            println("Max Price")
            m_price = scala.io.StdIn.readDouble();
            // Orders by asc by price
              if(manuf_name != "0") {
                if (model_name != "0") {
                  if(m_price != 0) {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name), lte("price_usd",m_price))).sort(ascending("price_usd")).results;
                  }
                  else {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), equal("model_name", model_name))).sort(ascending("price_usd")).results;
                  }
                }
                else {
                  if(m_price != 0) {
                    out = collection.find(and(equal("manufacturer_name", manuf_name), lte("price_usd",m_price))).sort(ascending("price_usd")).results;
                  }
                  else {
                    out = collection.find(equal("manufacturer_name", manuf_name)).sort(ascending("price_usd")).results;
                  }
                }
              }
              else {
                if(model_name != "0") {
                  if(m_price != 0) {
                    out = collection.find(and(equal("model_name", model_name), lte("price_usd",m_price))).sort(ascending("price_usd")).results;
                  }
                  else {
                    out = collection.find(equal("model_name", model_name)).sort(ascending("price_usd")).results;
                  }

                }
                else {
                  if(m_price != 0) {
                    out = collection.find(equal("price_usd", m_price)).sort(ascending("price_usd")).results;
                  }
                  else { // All resuts
                    out = collection.find().sort(ascending("price_usd")).results;
                  }
                }
              }
        }
        else if(opt == 2) {
            println("What would you like to find average of:")
            println("Model:");
            manuf_name = scala.io.StdIn.readLine();
            val out = collection.aggregate( Seq(group("$model_name", avg("price_usd", 1)))).printResults();
            println(out);
        }

        for(single_doc <- out) {
            var id = single_doc.get("_id").get.asInt32().getValue();
            var manuf_n = single_doc.get("manufacturer_name").get.asString().getValue();
            var model_n = single_doc.get("model_name").get.asString().getValue();
            var price = single_doc.get("price_usd").get.asDouble().getValue();
            var year = single_doc.get("year_produced").get.asInt32().getValue();
            var od_value = single_doc.get("odometer_value").get.asInt32().getValue();
            println(s"$id, Model: $model_n, Manufacturer: $manuf_n, Price:" +"$" +s"$price, Year: $year, Odometer: $od_value");
        }
    }

    def printLogo: Unit = { // Prints a welcome message
        println("               000000000000000000000");
        println("              000        000      000");
        println("-           0000        0000       0000");
        println("----   00000000000000000000000000000000000000000")
        println("----  0000000000 WELCOME TO USED CARS! 0000000000")
        println("----    000000000000000000000000000000000000000");
        println("-         00000000                  00000000");
        println("            0000                      0000");

    }
  
}
