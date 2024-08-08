package org.example.controllers;

import org.example.DAOs.ItemDAO;
import org.example.models.Item;
import io.javalin.http.Handler;
import org.example.models.User;
import java.util.*;

public class ItemController {

    //We need a RoleDAO to use it's methods
    ItemDAO iDAO = new ItemDAO();

    //This Handler will accept GET requests to /items/{id}
    public Handler getItemByHandler = ctx -> {

        //Extract the path parameter from the HTTP Request
        //We had to use parseInt because pathParam returns a String
        int item_id = Integer.parseInt(ctx.pathParam("id"));

        //Instantiate a Role object using the DAO method and the role_id we got above
        Item item = iDAO.getItemById(item_id);

        if(item == null){
            ctx.status(400); //bad request - role not found
            ctx.result("Role not found!");
        } else {
            ctx.status(200); //200 ok
            ctx.json(item); //send the Role back as JSON
        }

    };



    //This Handler will accept GET requests to /items
    public Handler getItemByUserIdHandler = ctx -> {

        //Extract the path parameter from the HTTP Request
        //We had to use parseInt because pathParam returns a String
        int item_id = Integer.parseInt(ctx.pathParam("id"));



        //Instantiate a Role object using the DAO method and the role_id we got above
        List<Item> a = iDAO.getItemByUserId(item_id);

        if(a == null){
            ctx.status(400); //bad request - role not found
            ctx.result("No items found");
        } else {
            ctx.status(200); //200 ok
            ctx.json(a); //send the Role back as JSON
        }

    };




    //This Handler will accept GET requests to /items
    public Handler getAllItemsHandler = ctx -> {

        //Instantiate a Role object using the DAO method and the role_id we got above
        List<Item> a = iDAO.getAllItems();

        if(a == null){
            ctx.status(400); //bad request - role not found
            ctx.result("No items found");
        } else {
            ctx.status(200); //200 ok
            ctx.json(a); //send the Role back as JSON
        }

    };

    //This Handler will handle POST requests to /items
    public Handler insertItemHandler = ctx -> {

        //We have JSON data coming in, which we need to convert into a Java object before the DAO can use it
        //We're going to use ctx.bodyAsClass(), to convert the incoming JSON into a Java Employee object
        Item newItem = ctx.bodyAsClass(Item.class);

        //Send this employee to the DAO to be inserted into the DB
        Item insertedItem = iDAO.insertItem(newItem);

        //If something goes wrong in the DAO, it will return null.
        //We can send back an error code/message if so
        if (insertedItem == null) {
            ctx.status(400); //400 stands for bad request
            //TODO: we could make a custom exception like "ManagerAlreadyExistsException"
            ctx.result("Failed to insert Item! Check your JSON!");
        } else{
            //if the insert is successful, return 201 and the new Employee
            ctx.status(201); //201 stands for "created", as in some new data was created
            ctx.json(insertedItem); //send the new inserted Employee back to the user
        }

        //NOTE: we can have the json() and status() methods in either order

    };






    //This Handler will handle PATCH requests to /roles/{id}
    public Handler updateDescriptionHandler = ctx -> {

        //The user will include the role id in the path parameter...
        //...and include the new salary in the request body
        int item_id = Integer.parseInt(ctx.pathParam("id"));

        //NOTE: salary is coming in as a single value, so we'll use .body(), not .bodyAsClass()
        String itemDescription = (ctx.body());

        //Call the DAO method to update the salary, giving it the ID and the Salary
        if(!iDAO.updateItemDescription(item_id, itemDescription).equals(""))
        {
            ctx.status(202); //202 stands for accepted - update accepted
            ctx.result("Item description " + item_id + " has been updated to: " + itemDescription);
        } else {
            ctx.status(400); //bad request - something went wrong
            ctx.result("Update failed! Make sure the Item Id and description are valid.");
        }



        //TODO: we could get the role title for a more descriptive output

    };


    //This Handler will handle PATCH requests to /roles/{id}
    public Handler deleteItemHandler = ctx -> {

        //The user will include the role id in the path parameter...
        //...and include the new salary in the request body
        int item_id = Integer.parseInt(ctx.pathParam("id"));



        //Call the DAO method to update the salary, giving it the ID and the Salary
        if(iDAO.deleteItem(item_id) != -1)
        {
            ctx.status(202); //202 stands for accepted - update accepted
            ctx.result("Item " + item_id + " has been deleted");
        } else {
            ctx.status(400); //bad request - something went wrong
            ctx.result("Delete failed! Make sure the Item Id is valid.");
        }



        //TODO: we could get the role title for a more descriptive output

    };

}
