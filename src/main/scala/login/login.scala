package login;

// Imports
import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;

case object Login {
    /* def main(args: Array[String]) {
        login();
    } */
    def login(): Unit = {

        var login_flag = true;
        var loginOption = 0;
        println("Starting up...");

        // Use method to prevent any other value other than 1/2/3
        while(login_flag) {
            println("Type either 1 (sign in) or 2 (sign up): ");
            loginOption = scala.io.StdIn.readInt();
            if(loginOption == 1 || loginOption == 2) {
                println(s"You picked: $loginOption");
                login_flag = false;
            }
            else if(loginOption == 3){
                println("Exiting...")
                login_flag = false;
            }
            else
                println("You did not pick a valid option, to exit enter 3.")
        }

        // Opening the username, pass file
        val user_file = new File("misc/users.txt");
        val user_source = Source.fromFile(user_file);
        val user_writer = new FileWriter(user_file, true);
        var username = "";
        var password = "";
        var user_tmp = "";
        var pass_tmp = "";
        var inServer = false; // Assumes the user is not in the database

        // Either signs up the user or verifies the user (simple match username and password)
        if(loginOption == 1) {
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            println("Verifying...");
            // Check every line for the combination
            for(line <- user_source.getLines()) {
                val lineSplit = line.split(",");
                if(lineSplit(0) == username) // Verifies each line of the file
                    if(lineSplit(1) == password)
                        inServer = true;
            }
        }
        else if(loginOption == 2) {
            println("Signing up")
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            user_writer.write(s"$username,$password\n");
            println("User successfully added")
        } // To be added, no repeat username

        // Simple message declaring successful login or not
        if(inServer)
            println(s"Hello $username");
        else
            println("Wrong username and password!")
            
        user_source.close();
        user_writer.close();

    }
}