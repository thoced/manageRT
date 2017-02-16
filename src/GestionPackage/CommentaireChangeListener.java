/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.ConnectionSQL;
import Models.TodoModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



/**
 *
 * @author Thonon
 */
public class CommentaireChangeListener implements ChangeListener<Boolean>{
  
    private TodoModel model;

    public TodoModel getModel() {
        return model;
    }

    public void setModel(TodoModel model) {
        this.model = model;
    }
    
    
    
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
    {
       
        if(newValue == false && model != null)
        {
            try {
                String sql = "update t_todo set text = ? where id = ?";
                PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                st.setString(1, model.getText());
                st.setLong(2, model.getId());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(CommentaireChangeListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       
    }
 
}
