package example;

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

case object jsontomongo{
    var count = 0;
    val user_file = new File("misc/cars.csv");
    val user_source = Source.fromFile(user_file);
    val client: MongoClient = MongoClient();
    val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
    val collection: MongoCollection[Document] = database.getCollection("smallCars");
    for(line <- user_source.getLines()) {
        if(count == 0) {
            count = count + 1;
        }
        else if (count < 100) {
            count = count + 1;
            val out = line.split(",");
            val doc: Document = Document(
                "_id" -> count,
                "name" -> out(0),
                "type" -> out(1),
                "count" -> out(3),
            )
            collection.insertOne(doc).results();
        }
    }
}