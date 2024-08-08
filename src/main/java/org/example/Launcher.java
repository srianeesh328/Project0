package org.example;

import org.example.controllers.UserController;
import org.example.controllers.ItemController;
import org.example.models.User;
import org.example.utils.ConnectionUtil;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.SQLException;

public class Launcher {

    public static void main(String[] args) {

        /* This is a "try with resources" block
        A resource is opened up within the parentheses of the try block
        If the resource is successfully created, the rest of the try block runs
        A big benefit of this is that the resource will close after the try block finishes
        This is helpful for code cleanup and preventing memory leaks */
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("CONNECTION SUCCESSFUL :)");
        } catch(SQLException e){
            e.printStackTrace(); //tell us what went wrong
            System.out.println("connection failed :(");
        }

        //Typical Javalin setup syntax
        var app = Javalin.create(/*any extra configs would go here*/)
                .start( 5000)
                .get("/", ctx -> ctx.result("Hello Postman!")); //callable resource just for fun

        /*We need .start() to start our Javalin server on port 3000
         You can choose any port, I chose 3000 because probably nothing is running on it
         a port is like a parking space for an application, where messages, etc. can find it*/


        //ENDPOINT HANDLERS----------------------

        //TODO: a bunch of notes


        //instantiate Controllers so we can access their Handlers
        UserController ec = new UserController();
        ItemController rc = new ItemController();

        /*app.get() is the Javalin method that takes GET requests
         In this case, it's calling to the getEmployeesHandler in the EmployeeController
         SO, when we send a GET request to localhost:3000/employees, it goes here.*/
        app.get("/users", ec.getUserHandler);

        //app.post() is the Javalin method that takes in POST requests
        //Why are we allowed to have two handlers that end in /employees? It's a different resource since it's a POST, not a GET
        app.post("/users", ec.insertUserHandler);

        //app.post() is the Javalin method that takes in POST requests
        //Why are we allowed to have two handlers that end in /employees? It's a different resource since it's a POST, not a GET
        app.post("/items", rc.insertItemHandler);

        //this endpoint lets us get a Role by ID
        //what is {id}? this is a PATH PARAMETER. The id we search for is variable.
        app.get("/items/{id}", rc.getItemByHandler);

        app.get("/items", rc.getAllItemsHandler);

        app.get("items/user/{id}", rc.getItemByUserIdHandler)     ;

        //this endpoint lets us update a role salary
        app.patch("/items/{id}", rc.updateDescriptionHandler);

        app.patch("/users", ec.updateUserDetailsHandler);

        app.delete("/items/{id}", rc.deleteItemHandler);



    }

}