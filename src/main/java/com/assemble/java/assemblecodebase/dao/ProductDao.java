package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;

public interface ProductDao {
  
  int addProduct(Product product);
  void updateProduct(Product product);
  int deleteProduct(Product product);
  Product retrieve(int id);
}
