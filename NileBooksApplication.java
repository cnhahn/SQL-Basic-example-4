import java.sql.*;
import java.util.*;

/**
 * The class implements methods of the NileBooks database interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the
 * Connection object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case
 * an error occurs in the database, then the method should print an
 * error message and call System.exit(-1);
 */

public class NileBooksApplication {

    private Connection connection;

    /*
     * Constructor
     */
    public NileBooksApplication(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

    /**
     * Takes as argument an integer called numberReviewedBooks.
     * Returns the authorID for each author in Authors that has at least numberReviewedBooks
     * different books that have at least one review.
     * If numberReviewedBooks is not positive, that's an error.
     */

    public List<Integer> getAuthorsWithManyReviewedBooks(int numberReviewedBooks)
    {
        List<Integer> result = new ArrayList<Integer>();
        // your code here

        String query = "SELECT  b.authorID  FROM Books b, Reviews r WHERE b.bookID = r.bookID GROUP BY b.authorID HAVING COUNT(DISTINCT b.bookID) >= ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, numberReviewedBooks);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // end of your code
        return result;
    }


    /**
     * The fixTotalOrdered method has one integer argument, aPublisherIDtoFix, which is a publisherID.
     * fixTotalOrdered should change the totalOrdered values for each “bad book” that was published
     * by aPublisherIDtoFix, updating totalOrdered so that it is correct, i.e., so that it’s equal
     * to the sum of the quantity values for the Orders of that book.
     * fixTotalOrdered should return the number of bad books whose totalOrdered values were “fixed”.
     *
     * This method is relatively easy to write if you use the BadBookTotals view from Lab3, which has
     * been provided to you in the lab4_create.sql script for this assignment.
     */

    public int fixTotalOrdered(int aPublisherIDtoFix)
    {
        // your code here; return 0 appears for now to allow this skeleton to compile.
        
        int result = 0;
        
        String query1 = "UPDATE Books SET totalOrdered = totalOrdered WHERE publisherID = ?";
 
        try {
            //Updating Books table
            PreparedStatement ps = connection.prepareStatement(query1);
            ps.setInt(1, aPublisherIDtoFix);
            result = result + ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
        // end of your code

    }


    /**
     * increasePublishersPrices has two integer parameters, thePublisherID and theCount, and invokes
     * a stored function increasePricesFunction that you will need to implement and store in the database
     * according to the description in Lab4 assignment.
     *
     * The increasePublishersPrices method must only invoke the stored function increasePricesFunction,
     * which does all of the assignment work; do not implement the increasePublishersPrices method using
     * a bunch of SQL statements through JDBC.  However, increasePublishersPrices should check to see
     * whether theCount is positive, and report an error if it’s not.
     *
     * increasePublishersPrices should return the same integer result as the increasePricesFunction
     * stored function.
     */

    public int increasePublishersPrices(int thePublisherID, int theCount)
    {
        // There's nothing special about the name storedFunctionResult
        int storedFunctionResult = 0;


        // your code here

        String query2 = "SELECT increasePricesFunction(?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query2);
            ps.setInt(1, thePublisherID);
            ps.setInt(2, theCount);
            ResultSet rs = ps.executeQuery();
            rs.next();
            storedFunctionResult = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // end of your code
        return storedFunctionResult;

    }

};
