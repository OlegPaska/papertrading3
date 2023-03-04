package Login;


import GUI.GUIhandler;
import GUI.MainScreen;
import Market.Portfolio;

public class LoginHandler {
    Database db = new Database("src/data/login.txt", 96);
    public LoginHandler() {
        // test the database works
//        System.out.println("Database");
//
//        db.appendRecord("1.ABCDEFGH");
//        db.appendRecord("2.ABCDEFGH");
//        db.appendRecord("3.ABCDEF");
//        db.appendRecord("4.ABCDEFGH");
//        System.out.println("record number 2: " + db.getRecord(2));	 // should be "3.ABCDEFGH"
//        System.out.println(db.findRecord("EBCDEFGHIJ")); // false
//        System.out.println(db.findRecord("2.ABCDEFGH")); // true
//        System.out.println("count = " + db.getRecordCount()); // 4
//        db.deleteRecord(1);
        //login("oleg", "paska");
        }

    public void login(String username, String password){
        int usernameIndex = db.findUsername(username);
        if(usernameIndex != -1){
            if(db.validifyPassword(usernameIndex, password)){
                GUIhandler guIhandler = new GUIhandler();
                guIhandler.mainScreen(new Portfolio(username, db.getBalance(usernameIndex)));
                System.out.println("login verified");
            }
        }
    }

    public void signup(String username, String password, double balance){
        db.signup(username, password, balance);
        GUIhandler guIhandler = new GUIhandler();
        guIhandler.mainScreen(new Portfolio(balance, username));
    }
}
