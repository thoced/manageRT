/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Thonon
 */
public class oMemoChangeListener implements ListChangeListener
{
    private long id;

    public oMemoChangeListener(long id) {
        this.id = id;
    }
    
    
    
    @Override
    public void onChanged(Change c) 
    {
        if(c!=null)
        {
           while(c.next())
           {
            
            if(c.wasAdded())
            {
                for(Object o : c.getAddedSubList())
                {
                    MemoModel model = (MemoModel) o;
                    if(model != null)
                    {
                        try {
                            // ajout dans la db
                            String sql = "insert into t_memos (text,date_time,ref_id_identity) values (?,?,?)";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getText());
                            ps.setTimestamp(2, Timestamp.valueOf(model.getDateTime()));
                            ps.setLong(3, id);
                            ps.executeUpdate();
                            ResultSet result = ps.getGeneratedKeys();
                            if(result != null && result.first())
                                model.setId(result.getLong(1)); // on récupère l'id et on le place (pour les modif par la suite)
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(oMemoChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }
            
            if(c.wasRemoved())
            {
                for(Object o : c.getRemoved())
                {
                    MemoModel model = (MemoModel) o;
                    if(model != null)
                    {
                        try {
                            // ajout dans la db
                            String sql = "delete from t_memos where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.executeUpdate();
                           
                        } catch (SQLException ex) {
                            Logger.getLogger(oMemoChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }
            
           }
           
           
        }
    }
    
}
