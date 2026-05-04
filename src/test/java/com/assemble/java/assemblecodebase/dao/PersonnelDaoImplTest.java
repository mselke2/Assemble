package com.assemble.java.assemblecodebase.dao;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelDaoImplTest {
  
  @Test
  void set() {
    
    PersonnelDaoImpl personnelDao = new PersonnelDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      personnelDao.set(new Date(2026-1900, 5, 4), 13);
      personnelDao.set(new Date(2026-1900, 5, 5), 10);
      personnelDao.set(new Date(2026-1900, 5, 6), 16);
      personnelDao.set(new Date(2026-1900, 5, 7), 20);
      
      
    });
  }
}