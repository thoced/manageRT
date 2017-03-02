/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Thonon
 */
public class OLinkChangeListener implements ListChangeListener
{

    private long id;
      
    public OLinkChangeListener(long id) 
    {
        this.id = id;
    }
    
    
    @Override
    public void onChanged(Change c) 
    {
        if(c != null)
        {
            while(c.next())
            {
                if(c.wasAdded())
                {
                    for(Object o : c.getAddedSubList())
                    {
                        try {
                            PersonModel m = (PersonModel) o;
                            
                            // ajout dans la db
                            String sql = "insert into t_link_identity (ref_id_identity01,ref_id_identity02) values "
                                    + "(?,?)";
                            
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, id);
                            ps.setLong(2, m.getId());
                            ps.execute();
                            ps.close();
                            
                        } catch (SQLException ex) 
                        {
                            Logger.getLogger(OLinkChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            PersonModel m = (PersonModel) o;
                            
                            // ajout dans la db
                            String sql = "delete from t_link_identity where (ref_id_identity01 = ? AND ref_id_identity02 = ?) OR "
                                    + "(ref_id_identity01 = ? AND ref_id_identity02 = ?)";
                                                         
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, id);
                            ps.setLong(2, m.getId());
                            ps.setLong(3, m.getId());
                            ps.setLong(4, id);
                            ps.execute();
                            ps.close();
                            
                        } catch (SQLException ex) 
                        {
                            Logger.getLogger(OLinkChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }
        }
    }



   
    
}
