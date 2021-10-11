import example.prints;
import login.Login;
import login.Menu;

object main extends App {
    var isRealUser = false;

    println("Please login")

    // Sign in/ Sign up before continuing
    isRealUser = Login.login();
    if(isRealUser)
        Menu.welcome;
}