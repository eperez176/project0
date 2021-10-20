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
import org.mongodb.scala.model.Updates._

import login.Menu;



object NewLogin {
        def login: Int = {
            // Starting the mongoclient
            val client: MongoClient = MongoClient();
            val database: MongoDatabase = client.getDatabase("Ecommerce");
            // Get a Collection.
            val collection: MongoCollection[Document] = database.getCollection("Users");

            var login_flag = true;
            var loginOption = 0;
            var username = "";
            var password = "";
            var new_email = "";
            var user_tmp = "";
            var pass_tmp = "";
            var inServer = false; // Assumes the user is not in the database
            var deleteAccount = false;
            println("Starting up...");

            do {
                login_flag = true;
                // Checks if the user is new or current user or deleting account
                while(login_flag) {
                    println("\nType either 1 (sign in), 2 (sign up), 3 (delete account), 4 (update)\n");
                    loginOption = scala.io.StdIn.readInt();
                    if(loginOption == 1 || loginOption == 2 || loginOption == 3 || loginOption == 4) {
                        println(s"You picked: $loginOption");
                        login_flag = false;
                    }
                    else
                        println("You did not pick a valid option")
                }
                // Checks if the user is in the database
                if(loginOption == 1 || loginOption == 4) { // Looks for user in the User Database
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
                    }
                    if(!inServer) { // If the combination does not exist
                        println("\nUsername and password combination not found")
                        println("Please try again\n")
                    }
                    if(loginOption == 4 && inServer == true) { // After logging in
                        println("What would you like to change? Password (1), email (2)");
                        var changeOption = Menu.initialOptions;
                        if(changeOption == 1) {
                            println("What's the new password:");
                            var new_pass = scala.io.StdIn.readLine();
                            collection.updateOne(equal("_id",username), set("pass",new_pass)).printResults();

                        }
                        else if(changeOption == 2) {
                            println("What would be the new email:")
                            var new_email = scala.io.StdIn.readLine();
                            collection.updateOne(equal("_id",username), set("email",new_email)).printResults();

                        }
                        inServer = false;
                        println("Please login again");
                    }
                }
                else if(loginOption == 2) { // Enters the new user into the database
                    var isUsernameInvalid = false;
                    println("\nSigning up...")
                    println("Enter username: ");
                    username = scala.io.StdIn.readLine();
                    val out2 = collection.find(equal("_id", username)).results();
                    if(out2.isEmpty) { // Only enter if its a unique username
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
                        collection.insertOne(doc).printResults();
                        println("\nSuccessful!")
                    }
                    else {
                        println("\nUsername has been taken. Choose another!");
                    }

                }
                else if(loginOption == 3) { // Deletes the user from the database
                    println("Enter username: ");
                    username = scala.io.StdIn.readLine();
                    println("Enter password: ");
                    password = scala.io.StdIn.readLine();
                    println("\nVerifying...\n");
                    val out = collection.find(equal("_id", username)).results();
                    if(!out.isEmpty) { // Only checks if the username exists in the database
                        val new_out = out(0).get("pass").get.asString().getValue();
                        if(new_out == password) { // Make sure if it's desired
                            println("\nAre you sure? Type: Yes (Y) or No (N)")
                            val inpDel = scala.io.StdIn.readChar();
                            if(inpDel == 'Y') { // Delete account
                                println("\nDeleting acount...");
                                collection.deleteOne(equal("_id", username)).printResults();
                                println("Account deleted!");
                                deleteAccount = true;
                            }
                        }
                    }
                    else {
                            println("\nUsername and password combination not found")
                            println("Please try again\n")
                        }
                }
                else if(loginOption == 5)
                    deleteAccount = true;
            } while(!inServer && !deleteAccount); // Escape the loop if either login or delete accured

            client.close(); // Close the client
            if(inServer) { // Return true if sign in or sign up was successful
                println(s"\nHello $username!");
                println("Loading user's information...\n");
                return 1;
            }
            else if(loginOption == 4)
                return 0;
            else // False in other cases like deleting account
                return -1;
        }
    }