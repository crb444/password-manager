
import java.util.Scanner;

public class Password {
    private String username;
    private String password;
    private String  website;


    public Password(String username, String password, String website) {
        this.username = username;
        this.password = password;
        this.website = website;

    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getWebsite() {
        return this.website;
    }
}
