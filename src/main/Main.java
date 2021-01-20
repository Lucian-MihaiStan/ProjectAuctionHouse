package main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;
import static commander.Caller.addCommand;
import static commander.Caller.executeCommands;

public class Main {


    public static void addCommandToList(String commandLine) {
        List<String> parameters = Arrays.asList(commandLine.split(" "));
        addCommand(parameters);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(in);

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if(command.equals("EXIT")) break;
            addCommandToList(command);
        }

        executeCommands();

    }
}


// 6463