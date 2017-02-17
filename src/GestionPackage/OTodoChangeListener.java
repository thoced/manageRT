/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionPackage;

import Models.ConnectionSQL;
import Models.TodoModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;



/**
 *
 * @author Thonon
 */
public class OTodoChangeListener implements ListChangeListener{

    private long idPerson;
    
    public OTodoChangeListener(long idPerson) 
    {
        this.idPerson = idPerson;
    }

    
    
    @Override
    public void onChanged(Change c) 
    {
      
            while(c.next())
            {
                if(c.wasAdded()) // si c'et ajouté
                {
                    for(Object m : c.getAddedSubList())
                    {
                        try {
                            TodoModel model = (TodoModel)m;
                            // enregistrement sql
                            String sql = "insert into t_todo (ref_id_identity,titre,text,date_creation,date_rappel,rappel) values (?,?,?,?,?,?)";
                            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                            st.setLong(1, this.idPerson);
                            st.setString(2, model.getTitre());
                            st.setString(3, model.getText());
                            st.setDate(4, java.sql.Date.valueOf(model.getDateCreation()));
                            st.setDate(5,java.sql.Date.valueOf(model.getDateRappel()));
                            st.setBoolean(6, model.isRappel());
                            st.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(OTodoChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(c.wasRemoved())
                {
                    for(Object m : c.getRemoved())
                    {
                        try {
                            TodoModel model = (TodoModel)m;
                            // enregistrement sql
                            String sql = "delete from t_todo where id = ?";
                            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                            st.setLong(1, model.getId());
                            st.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(OTodoChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
               
              
            }
        }  
    }

        

