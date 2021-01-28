

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        Driver driver = new Driver(kb);
        driver.readData();
        driver.listTasks();

    }
}
