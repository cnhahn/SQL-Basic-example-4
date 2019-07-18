import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * A class that connects to PostgreSQL and disconnects.
 * You will need to change your credentials below, to match the usename and password of your account
 * in the PostgreSQL server.
 * The name of your database in the server is the same as your username.
 * You are asked to include code that tests the methods of the NileBooksApplication class
 * in a similar manner to the sample RunStoresApplication.java program.
*/


public class RunNileBooksApplication
{
    public static void main(String[] args) {
    	
    	Connection connection = null;
    	try {
    	    //Register the driver
    		Class.forName("org.postgresql.Driver"); 
    	    // Make the connection.
            // You will need to fill in your real username
            // and password for your Postgres account in the arguments of the
            // getConnection method below.
            connection = DriverManager.getConnection(
                                                     "jdbc:postgresql://cmps180-db.lt.ucsc.edu/cnhahn",
                                                     "cnhahn",
                                                     "monko1904");
            
            if (connection != null)
                System.out.println("Connected to the database!");

            /* Include your code below to test the methods of the NileBooksApplication class
             * The sample code in RunStoresApplication.java should be useful.
             * That code tests other methods for a different database schema.
             * Your code below: */
            
            
            NileBooksApplication app = new NileBooksApplication(connection);

            int numberReviewedBooks = 3;
            List<Integer> titles = app.getAuthorsWithManyReviewedBooks(numberReviewedBooks);
            for (int title : titles)
                    System.out.println(title);
            
            /*     
             * Output of getAuthorsWithManyReviewedBooks
             * when the parameter numberReviewedBooks = 3
             * 111
             * 2192
            */

            int aPublisherIDtoFix = 94519;
            int result = app.fixTotalOrdered(aPublisherIDtoFix);
            System.out.println(result);

            /*      
             * Output of fixTotalOrdered
             * when the parameter aPublisherIDtoFix = 94519
             * 9
            */


            int result3 = app.increasePublishersPrices(98035, 2);
            System.out.println(result3);

            /* Test 1    
             * Output of increasePublishersPrices
             * when the parameter thePublisherID = 98035
             * when the parameter theCount = 2
             * 2
            */

            int result4 = app.increasePublishersPrices(98035,4);
            System.out.println(result4);

            /* Test 2    
             * Output of increasePublishersPrices
             * when the parameter thePublisherID = 98035
             * when the parameter theCount = 4
             * 4
            */

            /*******************
            * Your code ends here */
            
    	}
    	catch (SQLException | ClassNotFoundException e) {
    		System.out.println("Error while connecting to database: " + e);
    		e.printStackTrace();
    	}
    	finally {
    		if (connection != null) {
    			// Closing Connection
    			try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Failed to close connection: " + e);
					e.printStackTrace();
				}
    		}
    	}
    }
}
