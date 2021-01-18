package main;

import loginsql.MySQLConnection;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        MySQLConnection mySQLConnection = MySQLConnection.getInstance();

        mySQLConnection.closeConnection();
    }
}
