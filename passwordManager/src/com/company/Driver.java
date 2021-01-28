
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private Scanner kb;
    private ArrayList<Password> passwordDatabase;
    private String fileName;

    public Driver(Scanner kb) {
        fileName = "data.bin";
        this.kb = kb;
        passwordDatabase = new ArrayList<>();
    }

    public void listTasks() {
        boolean exit = false;

        while(!exit) {
            System.out.println();
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*");
            System.out.println("What do you want to do? ");
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*");
            System.out.println("1. View Password");
            System.out.println("2. Add Password");
            System.out.println("3. View Passwords");
            System.out.println("4. Delete Password");
            System.out.println("0. Exit");

            String input = kb.next();
            taskManager(input);
        }
    }

    public void readData() {
        try {
            FileInputStream fileIs = new FileInputStream(getFileName());
            ObjectInputStream is = new ObjectInputStream(fileIs);

            int numberOfEntries = is.readInt();
            int counter = 0;
            while(counter < numberOfEntries ) {
                String data = is.readUTF();
                String[] dataArray = data.split("/");
                Password pw = new Password(dataArray[0], dataArray[1],
                        dataArray[2]);
                getPasswordDatabase().add(pw);
                counter++;
            }

            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData() {
        try {
            FileOutputStream fileOs = new FileOutputStream(getFileName());
            ObjectOutputStream os = new ObjectOutputStream(fileOs);

            os.writeInt(getPasswordDatabase().size());

            for(Password p: getPasswordDatabase()) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getUsername());
                sb.append("/");
                sb.append(p.getPassword());
                sb.append("/");
                sb.append(p.getWebsite());

                os.writeUTF(sb.toString());
            }
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void taskManager(String input) {
        switch(input) {
            case "1":
                viewPassword();
                break;
            case "2":
                addPassword();
                break;
            case "0":
                exitApp();
                break;
            case "3":
                viewAllPasswords();
                break;
            case "4":
                deletePassword();
        }
    }

    public void deletePassword() {
        System.out.println("Choose website you want to delete?");
        String website = kb.next();
        boolean found = false;
        int index=0;

        for(Password p: getPasswordDatabase()){
            if(website.equalsIgnoreCase(p.getWebsite())) {
                index = getPasswordDatabase().indexOf(p);
                found = true;
                break;
            }
        }

        if(found) {
            getPasswordDatabase().remove(index);
            System.out.println("Password deleted!");
        } else {
            System.out.println("Password not found.");
        }
    }

    public void viewAllPasswords() {
        System.out.println("Complete list of passwords.");
        for(Password pw: getPasswordDatabase()) {
            System.out.println(pw.getWebsite() + " " + pw.getUsername() + " " +
                    pw.getPassword());
        }
    }

    public void exitApp() {
        System.out.println("App is saving data...");
        writeData();
        System.out.println("GoodBye!");
        System.exit(0);
    }

    public void viewPassword() {
        boolean found = false;
        String password=null;

        System.out.println("Enter the website: ");
        String inputWebsite = kb.next();
        System.out.println("Enter the username: ");
        String inputUsername = kb.next();

        for(Password pw: getPasswordDatabase()) {
            if(pw.getWebsite().equalsIgnoreCase(inputWebsite) &&
            pw.getUsername().equalsIgnoreCase(inputUsername)) {
                found = true;
                password = pw.getPassword();
            }
        }

        if(!found) {
            System.out.println("Username or website not found!");
        } else {
            System.out.println("Password is: " + password);
        }


    }

    public void addPassword() {
        System.out.println("Enter the website: ");
        String inputWebsite = kb.next();
        System.out.println("Enter the username: ");
        String inputUsername = kb.next();
        System.out.println("Enter the password: ");
        String inputPassword = kb.next();

        Password pw = new Password(inputUsername, inputPassword, inputWebsite);
        passwordDatabase.add(pw);

        System.out.println("Successfully added!");
    }

    public ArrayList<Password> getPasswordDatabase() {
        return this.passwordDatabase;
    }

    public String getFileName() {
        return this.fileName;
    }
}
