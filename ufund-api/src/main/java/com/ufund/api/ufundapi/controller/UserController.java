package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;
 /**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 */
@RestController
@RequestMapping("/user")
public class UserController  {
    
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param userDAO The {@link UserDAO User Data Access Object} to perform CRUD
     *                operations
     *                <br>
     *                This dependency is injected by the Spring Framework
     */
    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }


     /**
     * Responds to the GET request for a {@linkplain User user} for the given id
     * 
     * @param id The name used to locate the {@link User user}
     *
     * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @CrossOrigin(origins = "http://localhost:4200")

    @GetMapping("/")
    public ResponseEntity<User[]> searchUser(@RequestParam String name) {
        LOG.info("GET /users/?user="+name);

        try {
            User[] user = userDAO.findUsers(name);
            if (user != null)
                return new ResponseEntity<User[]>(user,HttpStatus.OK);
            else
                return new ResponseEntity<User[]>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        LOG.info("GET /users/" + name);
        try {
            User user = userDAO.getUser(name);
            if (user != null)
                return new ResponseEntity<User>(user,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User user} with the provided User object
     * 
     * @param UserFileDAO - The {@link User user} to create
     * 
     * @return HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /register?id = " + user);

        try {
            User status = userDAO.createUser(user);
            if (status != null) {
                return new ResponseEntity<User>(status, HttpStatus.CREATED);
            } else { 
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String name) {
        LOG.info("DELETE " + name);

        try {
            //Username status = usernameDAO.deleteUsername(id);
            if (userDAO.deleteUser(name)) {
                return new ResponseEntity<User>(HttpStatus.OK);
            } else { 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

