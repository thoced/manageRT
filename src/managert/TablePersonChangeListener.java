/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import Models.ConnectionSQL;
import Models.PersonModel;
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
public class TablePersonChangeListener implements ListChangeListener
{

    @Override
    public void onChanged(Change c)
    {
       while(c.next())
       {
           if(c.wasAdded())
           {
               for(Object o : c.getAddedSubList())
               {
                   PersonModel model = (PersonModel) o;
                    String sql = "insert into t_identity (nom,"
                  + "prenom,"
                  + "date_naissance,"
                  + "num_national,"
                  + "adresse,"
                  + "numero,"
                  + "boite,"
                  + "ville,"
                  + "code_postal,"
                  + "categorie,"
                  + "priorite) values (?,?,?,?,?,?,?,?,?,?,?)";
       
                    try 
                    {
                        PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                        // tronk
                        st.setString(1, "Nouvelle personne"); // nom
                        st.setString(2, "Nouvelle personne"); // prenom
                        st.setDate(3, new java.sql.Date(0)); // date_naissance
                        st.setString(4, ""); // num_national
                        st.setString(5, ""); // adresse
                        st.setString(6, ""); // numero
                        st.setString(7, ""); // boite
                        st.setString(8, ""); // ville
                        st.setString(9, ""); // code_postal
                        st.setString(10, ""); // categorie
                        st.setString(11, ""); // priorite
                        st.executeUpdate();
                        ResultSet result = st.getGeneratedKeys();
                        if(result != null && result.first())
                        {
                            model.setId(result.getLong(1));
                        }


                    } catch (SQLException ex) {
                        Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
           }
       }
    }
    
}
