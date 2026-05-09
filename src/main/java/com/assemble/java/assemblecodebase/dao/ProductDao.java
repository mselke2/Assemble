package com.assemble.java.assemblecodebase.dao;

import com.assemble.java.assemblecodebase.model.Product;

import java.util.List;

public interface ProductDao {
  
  int addProduct(Product product);
  boolean updateProduct(Product product);
  int deleteProductById(int id);
  Product retrieve(int id);
  List<Product> retrieveAll();
}
