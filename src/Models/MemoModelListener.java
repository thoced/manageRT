/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Thonon
 */
public class MemoModelListener implements ChangeListener
{ 
    private long id;

    public MemoModelListener(long id) {
        this.id = id;
    }
    
    
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) 
    {
        // si la oldValue est vide, alors on est certainement dans le cas d'un ajout, pas de modification, on ne fait rien
        if(oldValue != null && newValue != null)
        {
            try {
                // update dans la db
                String sql = "update t_memos set text = ?, date_time = ? where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, (String)newValue);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setLong(3, this.id);
                ps.executeUpdate();
                        } catch (SQLException ex) {
                Logger.getLogger(MemoModelListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
