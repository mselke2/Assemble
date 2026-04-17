package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;

public interface ProductDao {
  
  void addProduct(Product product);
  void updateProduct(Product product);
  void deleteProduct(Product product);
  Product retrieve(int id);
}
