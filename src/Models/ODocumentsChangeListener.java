/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.ConnectionSQL;
import Models.DocumentModel;
import Models.TodoModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Thonon
 */
public class ODocumentsChangeListener implements ListChangeListener{

    private long idPerson;
    
    

    public ODocumentsChangeListener(long idPerson) 
    {
        this.idPerson = idPerson;
        
      
    }

    
    @Override
    public void onChanged(Change c) 
    {
      
        while(c.next())
            {
                if(c.wasAdded()) // si c'est ajouté
                {
                    for(Object m : c.getAddedSubList())
                    {
                        try {
                            DocumentModel model = (DocumentModel)m;
                             // creation de la requete sql d'ajout
                            String sql = "insert into t_documents (nom,commentaire,fichier,ref_id_identity) values (?,?,?,?)";
                            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                            st.setString(1, model.getNom());
                            st.setString(2, model.getCommentaire());
                            st.setBlob(3, model.getFichier());
                            st.setLong(4, this.getIdPerson());
                            st.execute();
                            // fermeture
                            st.close();
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
                            DocumentModel model = (DocumentModel)m;
                             // suppression dans la base
                            String sql = "delete from t_documents where id = ?";
                            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                            st.setLong(1, model.getId());
                            st.execute();
                            // fermeture
                            st.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(OTodoChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }
    
    
}
