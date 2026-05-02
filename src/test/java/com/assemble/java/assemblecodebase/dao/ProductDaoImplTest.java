package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoImplTest {
  
  @Test
  void addProduct() {
    
    Product product = new Product(5, "test", new Time(1, 0, 0), 3);
    ProductDaoImpl productDao = new ProductDaoImpl();
    assertDoesNotThrow(() -> {
      assertEquals(5, productDao.addProduct(product));
      
      assertThrows(ProductDaoException.class, () -> productDao.addProduct(product));
    });
  }
  
  @Test
  void updateProduct() {
    
    Product product = new Product(5, "test2", new Time(2, 0, 0), 5);
    ProductDaoImpl productDao = new ProductDaoImpl();
    assertDoesNotThrow(() -> {
      assertTrue(productDao.updateProduct(product));
      
      product.setId(6);
      assertThrows(ProductDaoException.class, () -> productDao.updateProduct(product));
    });
  }
  
  @Test
  void deleteProductById() {
    
    ProductDaoImpl productDao = new ProductDaoImpl();
    
    assertDoesNotThrow(() -> {
      productDao.deleteProductById(5);
      
      assertThrows(ProductDaoException.class, () -> productDao.deleteProductById(5));
    });
    
  }
}