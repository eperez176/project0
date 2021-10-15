package login;

// Imports
import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;

import org.mongodb.scala._;
import org.mongodb.scala.model.Filters._
import helper.Helpers._;
import org.mongodb.scala.bson;

object NewLogin {
        def login: Boolean = {
            // Starting the mongoclient
            val client: MongoClient = MongoClient();
            val database: MongoDatabase = client.getDatabase("Ecommerce");
            // Get a Collection.
            val collection: MongoCollection[Document] = database.getCollection("Users");

            var login_flag = true;
            var loginOption = 0;
            println("Starting up...");

            // Checks if the user is new or current user
            while(login_flag) {
                println("\nType either 1 (sign in), 2 (sign up), 3 (delete account): \n");
                loginOption = scala.io.StdIn.readInt();
                if(loginOption == 1 || loginOption == 2 || loginOption == 3) {
                    println(s"You picked: $loginOption");
                    login_flag = false;
                }
                else if(loginOption == 4){
                    println("Exiting...")
                    login_flag = false;
                }
                else
                    println("You did not pick a valid option, to exit enter 4.")
            }

            var username = "";
            var password = "";
            var new_email = "";
            var user_tmp = "";
            var pass_tmp = "";
            var inServer = false; // Assumes the user is not in the database
            var deleteAccount = false;

            // Checks if the user is in the database
            do {
                if(loginOption == 1) {
                    println("Enter username: ");
                    username = scala.io.StdIn.readLine();
                    println("Enter password: ");
                    password = scala.io.StdIn.readLine();
                    println("\nVerifying...\n");
                    // Check every line for the combination
                    val out = collection.find(equal("_id", username)).results();
                    if(!out.isEmpty) { // Prevents error if the username does not exist
                        val new_out = out(0).get("pass").get.asString().getValue();
                        if(new_out == password)
                            inServer = true;
                        if(!inServer) {
                            println("\nUsername and password combination not found")
                            println("Please try again\n")
                        }
                    }
                }
                else if(loginOption == 2) {
                    var isUsernameInvalid = false;
                    println("\nSigning up...")
                    println("Enter username: ");
                    username = scala.io.StdIn.readLine();
                    val out2 = collection.find(equal("_id", username)).results();
                    if(out2.isEmpty) {
                        println("Enter password: ");
                        password = scala.io.StdIn.readLine();
                        println("Enter email:")
                        new_email = scala.io.StdIn.readLine();
                        inServer = true;

                        // New user will not be admin
                        val doc: Document = Document (
                            "_id" -> username,
                            "pass" -> password,
                            "email" -> new_email,
                            "admin" -> false
                        )
                        collection.insertOne(doc).results();
                        println("\nSuccessful!")
                    }
                    else {
                        println("\nUsername has been taken. Choose another!");
                    }

                }
                else if(loginOption == 3) {
                    println("Enter username: ");
                    username = scala.io.StdIn.readLine();
                    println("Enter password: ");
                    password = scala.io.StdIn.readLine();
                    println("\nVerifying...\n");
                    val out = collection.find(equal("_id", username)).results();
                    if(!out.isEmpty) {
                        val new_out = out(0).get("pass").get.asString().getValue();
                        if(new_out == password) {
                            println("\nAre you sure? Type: Yes (Y) or No (N)")
                            val inpDel = scala.io.StdIn.readChar();
                            if(inpDel == 'Y') {
                                println("\nDeleting acount...");
                                collection.deleteOne(equal("_id", username)).printResults();
                                println("Account deleted!");
                                deleteAccount = true;
                            }
                            else {
                                println("\nUsername and password combination not found")
                                println("Please try again\n")
                            }
                        }
                    }
                    else {
                            println("\nUsername and password combination not found")
                            println("Please try again\n")
                        }

                }
            } while(!inServer && !deleteAccount);

            if(inServer) {
                println(s"\nHello $username!");
                println("Loading user's information...\n");
                return true;
            }
            else
                return false;
            //val out = collection.find(equal("_id", "anon")).results();
            //val new_out = out(0).get("_id").get.asString().getValue; // This is how you get data from it
        }
    }