package login;

import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;

import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)
import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import java.io._;
import scala.io.StdIn.readLine;

import org.mongodb.scala.model.Sorts._

object DB { // DB functions
    def createDB: Unit = {
        var count = 0;
        var first_line = new Array[String](30);

        println("Would you like to import the database? Yes (Y), No (N)");
        var input = scala.io.StdIn.readChar();

        if(input == 'Y') {

            println("\nCreating the DB in Mongo...\n");

            // Openning the file, connecting to the db
            val user_file = new File("misc/cars.csv");
            val user_source = Source.fromFile(user_file);
            val client: MongoClient = MongoClient();
            val database: MongoDatabase = client.getDatabase("Ecommerce");
            val collection: MongoCollection[Document] = database.getCollection("usedCars");

            // Going to each line in the csv to parse through the data and sending it to the db
            for(line <- user_source.getLines()) {
                if(count == 0) { // Ignore the first line of the csv
                    count = count + 1;
                    first_line = line.split(",");
                }
                else { // Insert the data into the document
                    var out = line.split(",");
                    for(i <- 0 to 29) {
                        if(out(i).isEmpty()) // In case of any blank spaces in the data sets
                            out(i) = "0";
                    }
                    val doc: Document = Document( // Putting each elements to it's corresponding component
                        "_id" -> count,
                        first_line(0) -> out(0),
                        first_line(1)-> out(1),
                        first_line(2) -> out(2),
                        first_line(3) -> out(3),
                        first_line(4) -> out(4).toInt,
                        first_line(5) -> out(5).toInt,
                        first_line(6) -> out(6),
                        first_line(7) -> out(7).toBoolean,
                        first_line(8) -> out(8),
                        first_line(9) -> out(9).toDouble,
                        first_line(10) -> out(10),
                        first_line(11) -> out(11).toBoolean,
                        first_line(12) -> out(12),
                        first_line(13) -> out(13),
                        first_line(14) -> out(14).toDouble,
                        first_line(15) -> out(15).toBoolean,
                        first_line(16) -> out(16),
                        first_line(17) -> out(17).toInt,
                        first_line(18) -> out(18).toInt,
                        first_line(19) -> out(19).toBoolean,
                        first_line(20) -> out(20).toBoolean,
                        first_line(21) -> out(21).toBoolean,
                        first_line(22) -> out(22).toBoolean,
                        first_line(23) -> out(23).toBoolean,
                        first_line(24) -> out(24).toBoolean,
                        first_line(25) -> out(25).toBoolean,
                        first_line(26) -> out(26).toBoolean,
                        first_line(27) -> out(27).toBoolean,
                        first_line(28) -> out(28).toBoolean,
                        first_line(29) -> out(29).toInt,
                    )
                    count = count + 1; // the _id
                    collection.insertOne(doc).results();
                }
            }
            // Close all clients
            user_source.close();
            client.close();
            println("\n Finish creating the database \n");
        }
    }
}
