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
      } else {
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
    try (Connection connection = MySQLUtility.createConnection()) {
      // Update product entry
      String mySqlUpdateProduct = "UPDATE product SET Description = ?, MinutesDuration = ?, targetPersonnelCount = ? WHERE ID = ?;";
      PreparedStatement updateStatement = connection.prepareStatement(mySqlUpdateProduct);
      updateStatement.setString(1, product.getDescription());
      updateStatement.setInt(2, product.getMinutesDuration());
      updateStatement.setInt(3, product.getTargetPersonnelCount());
      updateStatement.setInt(4, product.getId());
      int effectedRows = updateStatement.executeUpdate();

      updateStatement.close();

      if (effectedRows == 0) return false;

      // Delete all existing productinventory and productEquipment for that product
      String mySqlDeleteProductInventory = "DELETE FROM productinventory WHERE ProductID = ?;";
      PreparedStatement deleteProductInventoryStatement = connection.prepareStatement(mySqlDeleteProductInventory);
      deleteProductInventoryStatement.setInt(1, product.getId());
      deleteProductInventoryStatement.executeUpdate();

      deleteProductInventoryStatement.close();

      String mySqlDeleteProductEquipment = "DELETE FROM productequipment WHERE ProductID = ?;";
      PreparedStatement deleteProductEquipmentStatement = connection.prepareStatement(mySqlDeleteProductEquipment);
      deleteProductEquipmentStatement.setInt(1, product.getId());
      deleteProductEquipmentStatement.executeUpdate();

      deleteProductEquipmentStatement.close();

      // Add new productinventory
      String mySqlInsertProductInventory = "INSERT INTO productinventory (ProductID, InventoryTypeID, RequiredInventoryCount) VALUES (?, ?, ?);";
      PreparedStatement insertProductInventoryStatement = connection.prepareStatement(mySqlInsertProductInventory);

      for (int i = 0; i < product.getRequiredInventoryIds().size(); i++) {
        insertProductInventoryStatement.setInt(1, product.getId());
        insertProductInventoryStatement.setInt(2, product.getRequiredInventoryIds().get(i));
        insertProductInventoryStatement.setInt(3, product.getRequiredInventoryCounts().get(i));
        insertProductInventoryStatement.addBatch();
      }

      insertProductInventoryStatement.executeBatch();
      insertProductInventoryStatement.close();

      // Add new productequipment
      String mySqlInsertProductEquipment = "INSERT INTO productequipment (ProductID, EquipmentTypeID, RequiredEquipmentTypeCount) VALUES (?, ?, ?);";
      PreparedStatement insertProductEquipmentStatement = connection.prepareStatement(mySqlInsertProductEquipment);

      for (int i = 0; i < product.getRequiredEquipmentIds().size(); i++) {
        insertProductEquipmentStatement.setInt(1, product.getId());
        insertProductEquipmentStatement.setInt(2, product.getRequiredEquipmentIds().get(i));
        insertProductEquipmentStatement.setInt(3, product.getRequiredEquipmentCounts().get(i));
        insertProductEquipmentStatement.addBatch();
      }

      insertProductEquipmentStatement.executeBatch();
      insertProductEquipmentStatement.close();

      JobDaoImpl jobDao = new JobDaoImpl();
      jobDao.deleteJob(product.getId());
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    return true;
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
      } else {
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

        Product product = new Product(resultSet.getInt("ID"), resultSet.getString("Description"), resultSet.getInt("MinutesDuration"), resultSet.getInt("TargetPersonnelCount"));

        populateRequirements(product, connection);

        return product;

      } else {
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
      while (resultSet.next()) {
        Product product = new Product(resultSet.getInt("ID"), resultSet.getString("Description"), resultSet.getInt("MinutesDuration"), resultSet.getInt("TargetPersonnelCount"));

        populateRequirements(product, connection);

        products.add(product);
      }

      connection.close();
      statement.close();
    } catch (SQLException | ClassNotFoundException e) {
      throw new JobDaoException(e.getMessage());
    }

    return products;
  }

  private void populateRequirements(Product product, Connection connection) throws SQLException {
    int id = product.getId();
    String mySqlSelectRequiredEquipment = """
        SELECT productequipment.EquipmentTypeId as RequiredEquipmentTypeID,
            productequipment.RequiredEquipmentTypeCount,
            equipmenttype.Description as EquipmentTypeDescription
        	FROM product
        		INNER JOIN productequipment ON product.ID = productequipment.ProductID
                LEFT JOIN equipmenttype ON productequipment.EquipmentTypeID = equipmenttype.ID
        	WHERE product.ID = ?;
        """;
    PreparedStatement equipmentStatement = connection.prepareStatement(mySqlSelectRequiredEquipment);
    equipmentStatement.setInt(1, id);
    ResultSet equipmentResult = equipmentStatement.executeQuery();

    List<Integer> requiredEquipmentTypeIds = new ArrayList<>();
    List<Integer> requiredEquipmentTypeCounts = new ArrayList<>();
    List<String> requiredEquipmentTypeDescriptions = new ArrayList<>();

    while (equipmentResult.next()) {
      requiredEquipmentTypeIds.add(equipmentResult.getInt("RequiredEquipmentTypeId"));
      requiredEquipmentTypeCounts.add(equipmentResult.getInt("RequiredEquipmentTypeCount"));
      requiredEquipmentTypeDescriptions.add(equipmentResult.getString("EquipmentTypeDescription"));
    }

    equipmentStatement.close();

    product.setRequiredEquipmentIds(requiredEquipmentTypeIds);
    product.setRequiredEquipmentCounts(requiredEquipmentTypeCounts);
    product.setRequiredEquipmentDescriptions(requiredEquipmentTypeDescriptions);

    String mySqlSelectRequiredInventory = """
        SELECT productinventory.InventoryTypeID as RequiredInventoryTypeID,
            productinventory.RequiredInventoryCount,
            inventorytype.Description as InventoryTypeDescription
        	FROM product
            INNER JOIN productinventory ON product.ID = productinventory.ProductID
            LEFT JOIN inventorytype ON productinventory.InventoryTypeID = inventorytype.ID
        	WHERE product.ID = ?;
        """;
    PreparedStatement inventoryStatement = connection.prepareStatement(mySqlSelectRequiredInventory);
    inventoryStatement.setInt(1, id);
    ResultSet inventoryResult = inventoryStatement.executeQuery();

    List<Integer> requiredInventoryTypeIds = new ArrayList<>();
    List<Integer> requiredInventoryTypeCounts = new ArrayList<>();
    List<String> requiredInventoryTypeDescriptions = new ArrayList<>();

    while (inventoryResult.next()) {
      requiredInventoryTypeIds.add(inventoryResult.getInt("RequiredInventoryTypeId"));
      requiredInventoryTypeCounts.add(inventoryResult.getInt("RequiredInventoryCount"));
      requiredInventoryTypeDescriptions.add(inventoryResult.getString("InventoryTypeDescription"));
    }

    inventoryStatement.close();

    product.setRequiredInventoryIds(requiredInventoryTypeIds);
    product.setRequiredInventoryCounts(requiredInventoryTypeCounts);
    product.setRequiredInventoryDescriptions(requiredInventoryTypeDescriptions);
  }
}
