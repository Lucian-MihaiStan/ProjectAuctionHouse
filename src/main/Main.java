package main;

import loginsql.clientconnection.MySQLConnectionClient;
import loginsql.product_connection.MySQLConnectionProduct;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;
import static commander.Caller.addCommand;
import static commander.Caller.executeCommands;

public class Main {

    public static final MySQLConnectionClient mySQLConnectionClient = MySQLConnectionClient.getInstance();
//    public static final MySQLConnectionProduct mySQLConnectionProduct = MySQLConnectionProduct.getInstance();

    public static void addCommandToList(String commandLine) {
        List<String> parameters = Arrays.asList(commandLine.split(" "));
        addCommand(parameters);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(in);

        String command = scanner.nextLine();
        addCommandToList(command);

        command = scanner.nextLine();
        addCommandToList(command);

        /*command = scanner.nextLine();
        addCommandToList(command);


        command = scanner.nextLine();
        addCommandToList(command);

        command = scanner.nextLine();
        addCommandToList(command);*/

        executeCommands();

//        mySQLConnectionClient.closeConnection();
//        mySQLConnectionProduct.closeConnection();
    }
}


// 6463