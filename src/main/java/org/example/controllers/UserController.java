package org.example.controllers;

import org.example.DAOs.UserDAO;
import org.example.models.User;
import io.javalin.http.Handler;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {

    //We need an EmployeeDAO to use its employee data methods
    UserDAO uDAO = new UserDAO();

    //This Handler will handle GET requests to /employees
    public Handler getUserHandler = ctx -> {

        //Get an ArrayList of employees, populated by the getEmployees DAO method
        ArrayList<User> users = uDAO.getUser();

        //PROBLEM: we can't send plain Java in an HTTP Response - it only takes JSON

        //We can use the .json() method to convert this ArrayList to JSON
        //NOTE: This method also returns the object once the code block is done
        ctx.json(users);

        //We can also set the HTTP Response status code with ctx.status()
        ctx.status(200);

    };

    //This Handler will handle POST requests to /employees
    public Handler insertUserHandler = ctx -> {

        //We have JSON data coming in, which we need to convert into a Java object before the DAO can use it
        //We're going to use ctx.bodyAsClass(), to convert the incoming JSON into a Java Employee object
        User newUser = ctx.bodyAsClass(User.class);

        //Send this employee to the DAO to be inserted into the DB
        User insertedUser = uDAO.insertUser(newUser);

        //If something goes wrong in the DAO, it will return null.
        //We can send back an error code/message if so
        if (insertedUser == null) {
            ctx.status(400); //400 stands for bad request
            //TODO: we could make a custom exception like "ManagerAlreadyExistsException"
            ctx.result("Failed to insert User! Check your JSON!");
        } else{
            //if the insert is successful, return 201 and the new Employee
            ctx.status(201); //201 stands for "created", as in some new data was created
            ctx.json(insertedUser); //send the new inserted Employee back to the user
        }

        //NOTE: we can have the json() and status() methods in either order

    };


    //This Handler will handle POST requests to /employees
    public Handler updateUserDetailsHandler = ctx -> {

        //We have JSON data coming in, which we need to convert into a Java object before the DAO can use it
        //We're going to use ctx.bodyAsClass(), to convert the incoming JSON into a Java Employee object
        User newUser = ctx.bodyAsClass(User.class);

        //Send this employee to the DAO to be inserted into the DB
        User insertedUser = uDAO.updateUserDetails(newUser);

        //If something goes wrong in the DAO, it will return null.
        //We can send back an error code/message if so
        if (insertedUser == null) {
            ctx.status(400); //400 stands for bad request
            //TODO: we could make a custom exception like "ManagerAlreadyExistsException"
            ctx.result("Failed to update User! Check your JSON!");
        } else{
            //if the insert is successful, return 201 and the new Employee
            ctx.status(201); //201 stands for "created", as in some new data was created
            ctx.json(insertedUser); //send the new inserted Employee back to the user
        }

        //NOTE: we can have the json() and status() methods in either order

    };



    //TODO: we could get the role title for a more descriptive output



    //TODO: (for you) figure out how to make a DELETE handler


}