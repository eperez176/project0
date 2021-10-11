package p0;

// Imports
import scala.io.Source;
import java.io.File;
import java.io.PrintWriter;
import scala.io.StdIn.readLine

object ReadF {
    def main(args: Array[String]) {
        login();
        //readFile();
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
        val user_file = new File("users.txt");
        val user_source = Source.fromFile(user_file);
        val user_writer = new PrintWriter(user_file);
        var username = "";
        var password = "";
        var user_tmp = "";
        var pass_tmp = "";


        if(loginOption == 1) {
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            println(s"$username, $password");
            // Check every line for the combination
            for(line <- user_source.getLine()){
                val lineSplit = line.split(",");
                println("")
            }
        }
        else if(loginOption == 2) {
            println("Signing up")
            println("Enter username: ");
            username = scala.io.StdIn.readLine();
            println("Enter password: ");
            password = scala.io.StdIn.readLine();
            println(s"$username, $password");
            user_writer.write(s"$username,$password");
            println("User successfully added")
        }

        user_source.close();
        user_writer.close();

    }
}
