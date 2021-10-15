package example

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

object zip extends App {
    val client: MongoClient = MongoClient();
    val database: MongoDatabase = client.getDatabase("Ecommerce");
        // Get a Collection.
    val collection: MongoCollection[Document] = database.getCollection("zipcodes");

    // How to read from json file to mongodb
    val stringDoc = Source.fromFile("misc/zipcodes.json").getLines.toList;
    val bsonDoc = stringDoc.map(doc => Document(doc));
    collection.insertMany(bsonDoc).printResults();
}