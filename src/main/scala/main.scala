import example.prints;
import login.NewLogin;
import login.Menu;

// Admin user that has extra perks like looking at the current users, delete, insert etc.
// While having users only be able to use
// To show delete and update capabilities (if not wanting to work with the car.csv)
object main extends App {
    var isRealUser = false;
    var opt = 0;

    println("Please login")

    // Sign in/ Sign up before continuing
    isRealUser = NewLogin.login;
    if(isRealUser) {
        Menu.welcome;
        opt = Menu.options;
    }
}