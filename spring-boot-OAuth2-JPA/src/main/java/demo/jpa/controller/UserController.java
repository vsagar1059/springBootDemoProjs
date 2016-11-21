package demo.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.jpa.model.User;
import demo.jpa.model.UserDao;

/**
 * Class UserController
 */
@Controller
public class UserController {

  // Wire the UserDao used inside this controller.
  @Autowired
  private UserDao userDao;

	
  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   * 
   * http://localhost:8080/create?email=rajeswari@abc.com&username=rajeswari&password=abc
   */
  @RequestMapping(value="/create")
  @ResponseBody
  public String create(String email, String username,  String password) {
    try {
      User user = new User(email, username, password);
      userDao.create(user);
    }
    catch (Exception ex) {
      return "Error creating the user: " + ex.toString();
    }
    return "User succesfully created!";
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="/delete" )
  @ResponseBody
  public String delete( long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }
    return "User succesfully deleted!";
  }
  
  /**
   * Retrieve the id for the user with the passed email address.
   * 
   * http://localhost:8080/get-by-email?email=rajeswari@abc.com
   */
  @RequestMapping(value="/get-by-email" )
  @ResponseBody
  public String getByEmail( String email) {
    String userId;
    try {
      User user = userDao.getByEmail(email);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found: " + ex.toString();
    }
    return "The user id is: " + userId;
  }
  
  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @RequestMapping(value="/update" )
  @ResponseBody
  public String updateName(long id, String email, String name) {
    try {
      User user = userDao.getById(id);
      user.setEmail(email);
      user.setUsername(name);
      userDao.update(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  } 
  
  
  @RequestMapping(value = "/users/")
  @ResponseBody
  public List<User> getAllUsers(){
	  
	  return userDao.getAll();
  }
  
  
} // class UserController