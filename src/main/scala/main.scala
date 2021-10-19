import example.prints;
import login.NewLogin;
import login.Menu;
import game.Game;
import login.DB;

// Admin user that has extra perks like looking at the current users, delete, insert etc.
// While having users only be able to use
// To show delete and update capabilities (if not wanting to work with the car.csv)
object main extends App {
    var isRealUser = 0;
    var opt = 0;
    var exit = false;

    println("Please login")

    // Sign in/ Sign up before continuing
    do {
        isRealUser = NewLogin.login;
    } while(isRealUser == -1);

    DB.createDB; // Imports the database from csv file to mongodb
    
    if(isRealUser != 0) {
        Menu.printLogo;
        do {
            println("Search(1), game (2)");
            opt = Menu.initialOptions;
            if(opt == 1) {
                Menu.searchOptions;
            }
            else if(opt == 2) {
                Game.tic_tac_toe();
            }
            exit = Menu.finish;
        } while(!exit)
    }
    Menu.goodbye;
}