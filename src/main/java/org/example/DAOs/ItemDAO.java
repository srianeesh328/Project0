package org.example.DAOs;

import org.example.models.Item;
import org.example.models.User;
import org.example.utils.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class ItemDAO implements ItemDAOInterface{

    @Override
    public Item getItemById(int id) {

        //Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //String representing our SQL query
            String sql = "SELECT * FROM items WHERE item_id = ?";

            //We need a prepared statement to fill in the wildcard "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            //now we can use the id parameter to fill in the variable
            ps.setInt(1, id);

            //Execute the query, save the results in a ResultSet
            ResultSet rs = ps.executeQuery();

            //while loop to extract the data (even though there's only one record)
            while(rs.next()){

                //Instantiate the Role found in the ResultSet using the all args constructor
                Item item = new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_title"),
                        rs.getString("item_description"),
                        null
                );

                UserDAO uDAO = new UserDAO();
                int a = rs.getInt("user_id_fk");
                User user = uDAO.getUserById(a);

                item.setUser(user);
                item.setUser_id_fk(a);

                return item; //return the Role object

            }

        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't get Item by ID!");
        }

        return null;
    }



    @Override
    public List<Item> getItemByUserId(int userid) {
        List<Item> itemList = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM items INNER JOIN users ON users.user_id = items.user_id_fk WHERE users.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getString("item_title"),
                    rs.getString("item_description"),
                    null
            );

                UserDAO uDAO = new UserDAO();
                int a = rs.getInt("user_id_fk");
                User user = uDAO.getUserById(a);

                item.setUser(user);
                item.setUser_id_fk(a);
                itemList.add(item);
            }

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't get Items!");
        }

        return itemList;
    }




    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM items";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Item item = new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_title"),
                        rs.getString("item_description"),
                        null
                );

                UserDAO uDAO = new UserDAO();
                int a = rs.getInt("user_id_fk");
                User user = uDAO.getUserById(a);

                item.setUser(user);
                item.setUser_id_fk(a);
                itemList.add(item);
            }

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't get Items!");
        }

        return itemList;
    }


    @Override
public String updateItemDescription(int itemId, String newDescription) {

    //open a connection
    try(Connection conn = ConnectionUtil.getConnection()){

        //Create our SQL String
        String sql = "UPDATE items SET item_description = ? WHERE item_id = ?";

        //Create a PreparedStatement to fill in the wildcards
        PreparedStatement ps = conn.prepareStatement(sql);

        //ps.setXYZ methods to fill in the wildcards
        ps.setString(1, newDescription);
        ps.setInt(2, itemId);

        //execute the update!
        ps.executeUpdate();

        //if successful, return the new salary!
        return newDescription;

    } catch (SQLException e){
        e.printStackTrace();
        System.out.println("Couldn't update Item Description!!!");
    }

    return "";
}



    @Override
    public Item insertItem(Item item) {

        //We need a Connection object to interact with the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL statement String
            String sql = "INSERT INTO items (item_id, item_title, item_description, user_id_fk) VALUES (?, ?,?,?)";

            //Instantiate a PreparedStatement to hold our SQL command and fill in the wildcards "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            //fill in each wildcard with the Employee object and ps.setXYZ() methods
            ps.setInt(1, item.getItem_id());
            ps.setString(2, item.getItem_title());
            ps.setString(3, item.getItem_description());
            ps.setInt(4, item.getUser_id_fk());


            //Now that our SQL command is complete, we can execute it
            ps.executeUpdate();
            //NOTE: executeUpdate() is used for INSERT, UPDATE, DELETE commands
            //...while executeQuery() is used for SELECT (querying the DB)

            //Now we can return the Employee to the user, assuming nothing went wrong
            return item;

        } catch(SQLException e){
            e.printStackTrace(); //Tell us what went wrong
            System.out.println("Failed to insert item!");
        }

        return null;
    }

    public Integer deleteItem(Integer item_id)
    {

        //We need a Connection object to interact with the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL statement String
            String sql = "DELETE FROM ITEMS WHERE item_id = ?";

            //Instantiate a PreparedStatement to hold our SQL command and fill in the wildcards "?"
            PreparedStatement ps = conn.prepareStatement(sql);

            //fill in each wildcard with the Employee object and ps.setXYZ() methods
            ps.setInt(1, item_id);

            //Now that our SQL command is complete, we can execute it
            ps.executeUpdate();
            //NOTE: executeUpdate() is used for INSERT, UPDATE, DELETE commands
            //...while executeQuery() is used for SELECT (querying the DB)

            //Now we can return the Employee to the user, assuming nothing went wrong
            return item_id;

        } catch(SQLException e){
            e.printStackTrace(); //Tell us what went wrong
            System.out.println("Failed to delete item!");
        }

        return -1;


    }




}




