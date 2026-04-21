package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;

public class ProductDaoImpl implements ProductDao {
  
  @Override
  public int addProduct(Product product) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a product exists
      // with this description and execute it.
    
    // IF a product exists
      // Throw a ProductDaoException with the message "Product already exists."
    
    // ELSE
      // Prepare an insert statement to add this product to the database and execute it.
      // Prepare a select statement to get the newly created productID and execute it.
      // Return the productID.
    // ENDIF
    return 0;
  }
  
  @Override
  public void updateProduct(Product product) {
  
    // Get a connection to the database
    
    // Prepare a select statement to see if a product exists
      // with this description and execute it.
    
    // IF a product exists
      // Prepare an update statement to update this product in the database and execute it.
    
    // ELSE
      // Throw a ProductDaoException with the message "Product does not exist."
    // ENDIF
  
  }
  
  @Override
  public int deleteProduct(Product product) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a product exists
      // with this description and execute it.
    
    // IF a product exists
      // Store the productID in a variable
      // Prepare a delete statement to delete this product from the database and execute it.
      // Return the productID.
    
    // ELSE
      // Throw a ProductDaoException with the message "Product does not exist."
    // ENDIF
    return 0;
  }
  
  @Override
  public Product retrieve(int id) {
    
    // Get a connection to the database
    
    // Prepare a select statement to see if a product exists with the
      // passed in productID and execute it.
    
    // IF a product exists
      // Move cursor to the result
      // Create a new product object with the data from the result and return it.
    
    // ELSE
      // Throw a ProductDaoException with the message "Product does not exist."
    return null;
  }
}
