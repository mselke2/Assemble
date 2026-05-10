package com.assemble.java.assemblecodebase.dao;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelDaoImplTest {
  
  @Test
  void set() {
    
    PersonnelDaoImpl personnelDao = new PersonnelDaoImpl();
    
    assertDoesNotThrow(() -> {
      
      personnelDao.set(new Date(2026-1900, 5, 4), 10);
      personnelDao.set(new Date(2026-1900, 5, 5), 15);
      personnelDao.set(new Date(2026-1900, 5, 6), 20);
      personnelDao.set(new Date(2026-1900, 5, 7), 9);
      
      
    });
  }
  
  @Test
  void retrieve() {
    PersonnelDaoImpl personnelDao = new PersonnelDaoImpl();
    
    assertDoesNotThrow(() -> {
      assertTrue(personnelDao.retrieveCount(new Date(2026-1900, 5, 4)) == 10);
      assertTrue(personnelDao.retrieveCount(new Date(2026-1900, 5, 5)) == 15);
      assertTrue(personnelDao.retrieveCount(new Date(2026-1900, 5, 6)) == 20);
      assertTrue(personnelDao.retrieveCount(new Date(2026-1900, 5, 7)) == 9);
    });
  }
  
  @Test
  void delete() {
    PersonnelDaoImpl personnelDao = new PersonnelDaoImpl();
    
    assertDoesNotThrow(() -> {
      personnelDao.delete(new Date(2026-1900, 5, 4));
      personnelDao.delete(new Date(2026-1900, 5, 5));
      personnelDao.delete(new Date(2026-1900, 5, 6));
      personnelDao.delete(new Date(2026-1900, 5, 7));
    });
    
    assertThrows(Exception.class, () -> personnelDao.retrieveCount(new Date(2026-1900, 5, 4)));
    assertThrows(Exception.class, () -> personnelDao.retrieveCount(new Date(2026-1900, 5, 5)));
    assertThrows(Exception.class, () -> personnelDao.retrieveCount(new Date(2026-1900, 5, 6)));
    assertThrows(Exception.class, () -> personnelDao.retrieveCount(new Date(2026-1900, 5, 7)));
    
  }
}