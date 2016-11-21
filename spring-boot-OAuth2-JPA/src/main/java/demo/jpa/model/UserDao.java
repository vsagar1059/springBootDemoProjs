package demo.jpa.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the User entity.
 */
@Repository
@Transactional
public class UserDao {
  
 
  /**
   * Save the user in the database.
   */
  public void create(User user) {
    entityManager.persist(user);
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public void delete(User user) {
    if (entityManager.contains(user))
      entityManager.remove(user);
    else
      entityManager.remove(entityManager.merge(user));
    return;
  }
  
  /**
   * Return all the users from H2 database.
   */
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @SuppressWarnings("unchecked")
  public List<User> getAll() {
    return entityManager.createQuery("from User").getResultList();
  }
  
  /**
   * Return the user based on email.
   */
  @PostAuthorize("hasRole('ROLE_ADMIN')")
		  
  public User getByEmail(String email) {
    return (User) entityManager.createQuery(
        "from User where email = :email")
        .setParameter("email", email)
        .getSingleResult();
  }

  /**
   * Return the user based on id.
   */
  @PostAuthorize("hasRole('ROLE_ADMIN')")
  public User getById(long id) {
    return entityManager.find(User.class, id);
  }

  /**
   * Update the user in the H2 database.
   */
  public void update(User user) {
    entityManager.merge(user);
    return;
  }
  
  public User findByUsername(String username){
	 return (User) entityManager.createQuery(
		        "from User where username = :username")
		        .setParameter("username", username)
		        .getSingleResult();
  }

  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
} 
