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

    def finish: Boolean = {
        println("Are you finish? Yes (Y) or No (N)");
        var input = scala.io.StdIn.readChar();
        if(input == 'Y')
            return true;
        else if(input == 'N')
            return false;
        else {
            println("Incorrect input. Assuming yes, exiting...");
            return true;
        }
    }

    def searchOptions: Unit = {
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
        var out = collection.find(and(equal("manufacturer_name", "Subaru"), equal("price_usd", 2000))).results; // Pre-load to have variable set, small set
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
                    out = collection.find(lte("price_usd", m_price)).sort(ascending("price_usd")).results;
                  }
                  else { // All resuts
                    out = collection.find().sort(ascending("price_usd")).results;
                  }
                }
              }
        }
        else if(opt == 2) {
            println("For unwanted options: Enter '0' \n");
            println("Average Price of Car Manufacturer (1) or Car Model (2)");
            option_2 = scala.io.StdIn.readInt();// Reads the option, limits the possibilities
            println("Any specific brand? ('0' for none)")
            var opt_3 = scala.io.StdIn.readLine();


            if(option_2 == 1 && opt_3 == "0")
                out = collection.aggregate((Seq(group("$manufacturer_name", sum("num",1),avg("avg_p", "$price_usd")), sort(ascending("avg_p"))))).results;
            else if(option_2 == 2 && opt_3 == "0") 
                out = collection.aggregate( Seq(group("$model_name", push("manu_n", "$manufacturer_name"), sum("num",1), avg("avg_p", "$price_usd")), sort(ascending("avg_p")))).results;
            else if(option_2 == 1) // Filtering at the beginning to decrease the query size
                out = collection.aggregate((Seq(filter(equal("manufacturer_name",opt_3)),group("$manufacturer_name", sum("num",1),avg("avg_p", "$price_usd")), sort(ascending("avg_p"))))).results;
            else if(option_2 == 2)
                out = collection.aggregate( Seq(filter(equal("model_name", opt_3)),group("$model_name", push("manu_n", "$manufacturer_name"), sum("num",1), avg("avg_p", "$price_usd")), sort(ascending("avg_p")))).results;
            
        }

        // Printing the results to the command prompt
        if(opt == 1) {
            
            for(single_doc <- out) {
                var id = single_doc.get("_id").get.asInt32().getValue();
                var manuf_n = single_doc.get("manufacturer_name").get.asString().getValue();
                var model_n = single_doc.get("model_name").get.asString().getValue();
                var price = single_doc.get("price_usd").get.asDouble().getValue();
                var year = single_doc.get("year_produced").get.asInt32().getValue();
                var od_value = single_doc.get("odometer_value").get.asInt32().getValue();
                println(s"ID: $id, $manuf_n $model_n: Price:" +"$" +f"$price%.2f, Year: $year, Odometer: $od_value");
            }
        }
        else if(opt == 2) {
            println("The number of results: " + out.length);
            if(option_2 == 2) {
                for(single_doc <- out) {
                var id = single_doc.get("_id").get.asString().getValue();
                var count = single_doc.get("num").get.asInt32().getValue();
                var manu = single_doc.get("manu_n").get.asArray.get(0).asString().getValue();
                var avg_p = single_doc.get("avg_p").get.asDouble().getValue();
                println(s"$manu $id, Avg Price:" +" $" + f"$avg_p%.2f ($count)");
                //println(manu);
                }
            }
            else if(option_2 == 1) {
                for(single_doc <- out) {
                    var id = single_doc.get("_id").get.asString().getValue();
                    var count = single_doc.get("num").get.asInt32().getValue();
                    var avg_p = single_doc.get("avg_p").get.asDouble().getValue();
                    println(s"$id, Avg Price:" +" $" + f"$avg_p%.2f ($count)");
                }
            }
        }
        println("The number of results: " + out.length);
    }

    def printLogo: Unit = { // Prints a welcome message
        println("               000000000000000000000");
        println("              000        000      000");
        println("-           0000        0000       0000");
        println("----   00000000000000000000000000000000000000000")
        println("----  0000000000 WELCOME TO USED CARS! 0000000000")
        println("----    000000000000000000000000000000000000000");
        println("-         00000000                 00000000");
        println("            0000                     0000");

    }

    def goodbye: Unit = {
      println("       0000000000000000");
      println("      0000   0000   0000");
      println("     00      0000     000");
      println("   0000000000000000000000000");
      println("   0(   )000000000000(    )0");
      println("    000000 GOOD BYE 00000000");
      println("    -----------------------");
      println("     00000          00000");
    }
  
}
