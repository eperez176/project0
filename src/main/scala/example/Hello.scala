package example;

import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;

object Hello extends App {
  println("Starting MongoDB - Scala Demo...");
  val client: MongoClient = MongoClient();
  val database: MongoDatabase = client.getDatabase("Ecommerce");
    // Get a Collection.
  val collection: MongoCollection[Document] = database.getCollection("Users");
  val out = collection.find(equal("_id", "anon")).results();
  val new_out = out(0).get("pass").get.asString().getValue();
  println(new_out);
}
