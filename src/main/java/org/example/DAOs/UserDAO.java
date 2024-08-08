package org.example.DAOs;

import org.example.models.User;

import org.example.models.Item;
import org.example.utils.ConnectionUtil;
import org.postgresql.util.PSQLException;

import java.sql.*; //import everything from java.sql
import java.util.ArrayList;


public class UserDAO implements UserDAOInterface{

    @Override
    public ArrayList<User> getUser() {

        //instantiate a Connection object so we can talk to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //A String that will represent the SQL we send to the DB
            String sql = "SELECT * FROM users";

            //We need to create a Statement object to execute our query
            //NOTE: The query above has no variables, so we'll use Statement, not PreparedStatement
            Statement s = conn.createStatement();

            //We execute the query, and save the results into a ResultSet
            ResultSet rs = s.executeQuery(sql);

            //We need an ArrayList to hold the SELECTed Users
            ArrayList<User> users = new ArrayList<>();

            //rs.next() will iterate through the ResultSet...
            //and return false when there are no more records
            while(rs.next()){

                //For every User found, add it to the ArrayList
                //We will need to instantiate a new User object for each record
                //We can get each column from the ResultSet with rs.getXYZ()
                User e = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );




                users.add(e); //add the populated Employee to the ArrayList
            } //end of while loop - no more employees to see!

            return users; //return the ArrayList of Employees

        } catch(SQLException e){
            e.printStackTrace(); //Tell us what went wrong
        }

        return null;
        //Why return null at the end? we need to satisfy the return type.
        //Something needs to be returned no matter what

    }

    @Override
    public User insertUser(User user) {

        //We need a Connection object to interact with the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL statement String
            String sql = "INSERT INTO users (user_id, first_name, last_name) VALUES (?,?,?)";

            //Instantiate a PreparedStatement to hold our SQL command and fill in the wildcards "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            //fill in each wildcard with the Employee object and ps.setXYZ() methods
            ps.setInt(1, user.getUser_id());
            ps.setString(2, user.getFirst_name());
            ps.setString(3, user.getLast_name());

            //Now that our SQL command is complete, we can execute it
            ps.executeUpdate();
            //NOTE: executeUpdate() is used for INSERT, UPDATE, DELETE commands
            //...while executeQuery() is used for SELECT (querying the DB)

            //Now we can return the Employee to the user, assuming nothing went wrong
            return user;

        } catch(SQLException e){
            e.printStackTrace(); //Tell us what went wrong
            System.out.println("Failed to insert user!");
        }

        return null;
    }


    @Override
    public User getUserById(int id) {

        //Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //String representing our SQL query
            String sql = "SELECT * FROM users WHERE user_id = ?";

            //We need a prepared statement to fill in the wildcard "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            //now we can use the id parameter to fill in the variable
            ps.setInt(1, id);

            //Execute the query, save the results in a ResultSet
            ResultSet rs = ps.executeQuery();

            //while loop to extract the data (even though there's only one record)
            while(rs.next()){

                //Instantiate the Role found in the ResultSet using the all args constructor
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );

                return user; //return the Role object

            }

        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't get User by ID!");
        }

        return null;
    }


    public User updateUserDetails(User a)
    {
        try(Connection conn = ConnectionUtil.getConnection()){

            // Create our SQL statement String
            String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE user_id = ?";

            // Instantiate a PreparedStatement to hold our SQL command and fill in the wildcards "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            // Fill in each wildcard with the User object and ps.setXYZ() methods
            ps.setString(1, a.getFirst_name());
            ps.setString(2, a.getLast_name());
            ps.setInt(3, a.getUser_id()); // Ensure this line is present to set the user_id

            //Now that our SQL command is complete, we can execute it
            ps.executeUpdate();
            //NOTE: executeUpdate() is used for INSERT, UPDATE, DELETE commands
            //...while executeQuery() is used for SELECT (querying the DB)

            //Now we can return the Employee to the user, assuming nothing went wrong
            return a;

        } catch(SQLException e){
            e.printStackTrace(); //Tell us what went wrong
            System.out.println("Failed to insert user!");
        }

        return null;

    }


}
