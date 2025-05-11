/* Name: Kevin Laszuk
Course: CNT 4714 – Fall 2024 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 1, 2024
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationServlet extends HttpServlet {
    private Connection connection;
    private ResultSet lookupResults;
    private PreparedStatement pstatement;
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inBoundUserName = request.getParameter("username");
        String inBoundPassword = request.getParameter("password");

        String credentialsSearchQuery = "SELECT * FROM usercredentials WHERE login_username = ? AND login_password = ?";
        boolean userCredentialsOK = false;

        try {
            // Establish connection to the DB
            getDBConnection();

            // Prepare the statement and execute the query
            pstatement = connection.prepareStatement(credentialsSearchQuery);
            pstatement.setString(1, inBoundUserName);
            pstatement.setString(2, inBoundPassword);
            lookupResults = pstatement.executeQuery();

            if (lookupResults.next()) {
                userCredentialsOK = true;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // If credentials are correct, redirect to the appropriate page
        if (userCredentialsOK) {
            if (inBoundUserName.equals("root")) {
                // Redirect to root user page
                response.sendRedirect("rootHome.jsp");
            } else if (inBoundUserName.equals("client")) {
                // Redirect to client home page
                response.sendRedirect("clientHome.html");
            } else if (inBoundUserName.equals("theaccountant")) {
                // Redirect to accountant page
                response.sendRedirect("accountHome.jsp");
            }
        } else {
            // If authentication failed, redirect to an error page
            response.sendRedirect("errorpage.html");
        }
    }

    // Method to get the database connection
    private void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try {
            filein = new FileInputStream("/path/to/systemapp.properties");
            properties.load(filein);

            dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

            connection = dataSource.getConnection();

        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (filein != null) {
                    filein.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
