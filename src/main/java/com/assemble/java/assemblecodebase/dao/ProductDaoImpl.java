package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;
import com.assemble.java.assemblecodebase.utility.MySQLUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
  
  @Override
  public int addProduct(Product product) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a product exists
      // with this description and execute it.
      String mySqlSelect = "SELECT * FROM product WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, product.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a product exists
      // Throw a ProductDaoException with the message "Product already exists."
      if (resultSet.isBeforeFirst()) {
        throw new ProductDaoException("Product already exists");
      }else {
        // ELSE
        // Prepare an set statement to add this product to the database and execute it.
        String mySqlInsert = "INSERT INTO product (ID, Description, MinutesDuration, TargetPersonnelCount) VALUES (?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(mySqlInsert);
        preparedStatement.setInt(1, product.getId());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getMinutesDuration());
        preparedStatement.setInt(4, product.getTargetPersonnelCount());
        preparedStatement.executeUpdate();
        // Return the productID.
        return product.getId();
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public boolean updateProduct(Product product) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see if a product exists
      // with this description and execute it.
      String mySqlSelect = "SELECT * FROM product WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, product.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      // IF a product exists
      // Prepare an update statement to update this product in the database and execute it.
      if (resultSet.isBeforeFirst()) {
        String mySqlUpdate = "UPDATE product SET Description = ?, MinutesDuration = ?, targetPersonnelCount = ? WHERE ID = ?;";
        preparedStatement = connection.prepareStatement(mySqlUpdate);
        preparedStatement.setString(1, product.getDescription());
        preparedStatement.setInt(2, product.getMinutesDuration());
        preparedStatement.setInt(3, product.getTargetPersonnelCount());
        preparedStatement.setInt(4, product.getId());
        preparedStatement.executeUpdate();
        resultSet.close();
        connection.close();
        
        return true;
      } else {
        throw new ProductDaoException("Product does not exists");
      }
      
      // ELSE
      // Throw a ProductDaoException with the message "Product does not exist."
      // ENDIF
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  
  }
  
  @Override
  public int deleteProductById(int id) {
    
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a product exists
      // with this description and execute it.
      String mySqlSelect = "SELECT * FROM product WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a product exists
      if (resultSet.isBeforeFirst()) {
        // Store the productID in a variable
        resultSet.next();
        int returnId = resultSet.getInt("ID");
        // Prepare a delete statement to delete this product from the database and execute it.
        String mySqlDelete = "DELETE FROM product WHERE ID = ?;";
        preparedStatement = connection.prepareStatement(mySqlDelete);
        preparedStatement.setInt(1, returnId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        // Return the productID.
        return returnId;
      }else {
        // ELSE
        // Throw a ProductDaoException with the message "Product does not exist."
        throw new ProductDaoException("Product does not exists");
        // ENDIF
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Product retrieve(int id) {
    try {
      // Get a connection to the database
      Connection connection = MySQLUtility.createConnection();
      
      // Prepare a select statement to see if a product exists
      // with this description and execute it.
      String mySqlSelect = "SELECT * FROM product WHERE ID = ?;";
      PreparedStatement preparedStatement = connection.prepareStatement(mySqlSelect);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      
      // IF a product exists
      if (resultSet.isBeforeFirst()) {
        
        // Move cursor to the result
        resultSet.next();
        
        // Create a new product object with the data from the result and return it.
        return new Product(
          resultSet.getInt("ID"),
          resultSet.getString("Description"),
          resultSet.getInt("MinutesDuration"),
          resultSet.getInt("TargetPersonnelCount")
        );
        
      } else  {
        throw new ProductDaoException("Product does not exists");
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Product> retrieveAll() {
    // Create a Job array
    List<Product> products = new ArrayList<>();

    // Get a connection to the database
    try {
      Connection connection = MySQLUtility.createConnection();
      // Prepare a select statement to see what jobs exist for the date
      // passed in date and execute it.
      String mySqlSelectAll = "SELECT * FROM product ORDER BY Description";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(mySqlSelectAll);

      // Use a loop to move the cursor through the results and create a new job object for each result and add it to the array.
      while(resultSet.next()) {
        Product product = new Product(resultSet.getInt("ID"),
            resultSet.getString("Description"),
            resultSet.getInt("MinutesDuration"),
            resultSet.getInt("TargetPersonnelCount"));
        products.add(product);
      }

      connection.close();
      statement.close();
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException(e.getMessage());
    }

    return products;
  }
}
