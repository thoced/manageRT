/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Thonon
 */
public interface ISQL 
{
      
   
    public  void select(long id);
    public  void insert();
    public  void update();
    public  void delete();
    
}
