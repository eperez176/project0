package login;

// Imports
import scala.io.Source;
import java.io.File;
import java.io._;
import scala.io.StdIn.readLine;

object ReadF {
    def main(args: Array[String]) {
        login();
        //readFile();
        //writeFile();
    }

    def writeFile(): Unit = {
        val filename = new File("Line.txt");
        val fsource = new FileWriter(filename, true);
        fsource.append("\nHOWDY");
        fsource.close();
    }

    def readFile(): Unit = {
        println("Howdy!");
        // Creating the file
        val filename = new File("test.txt");
        val fsource = Source.fromFile(filename);
        for(line <- fsource.getLines()){
            println(line);
        }

        fsource.close();
    }

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


        if(loginOption == 1) {
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            println("Verifying...");
            // Check every line for the combination
            for(line <- user_source.getLines()) {
                println(line);
                val lineSplit = line.split(",");
                if(lineSplit(0) == username) // Validates
                    if(lineSplit(1) == password)
                        inServer = true;
            }
            println("Ended this section")
        }
        else if(loginOption == 2) {
            println("Signing up")
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            println(s"$username, $password");
            var out = s"$username,$password\n";
            user_writer.write(out);
            println("User successfully added")
        }

        if(inServer)
            println(s"Hello $username");
        else
            println("Wrong username and password!")
        println()
        user_source.close();
        user_writer.close();

    }
}