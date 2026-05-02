package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoImplTest {
  
  @Test
  void addProduct() {
    
    Product product = new Product(3, "test", new Time(0), 3);
    ProductDaoImpl productDao = new ProductDaoImpl();
    assertDoesNotThrow(() -> {
      assertEquals(3, productDao.addProduct(product));
    });
  }
}