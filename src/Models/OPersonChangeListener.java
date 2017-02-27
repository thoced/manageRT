/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import managert.TablePersonChangeListener;

/**
 *
 * @author Thonon
 */
public class OPersonChangeListener  implements ListChangeListener{

    @Override
    public void onChanged(Change c) 
    {
       if(c != null)
       {
             while(c.next())
            {
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            String sql = "delete from t_identity where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, ((PersonModel)o).getId());
                            ps.execute();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(TablePersonChangeListener.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                
                if(c.wasAdded()) // si c'est une personne ajoutée
                {
                  
                    for(Object m : c.getAddedSubList())
                    {
                        PersonModel p = (PersonModel) m; 
                         //String sql = "insert into t_identity (nom,prenom,date_naissance,sexe,adresse,num,commune,zipcode,categorie,priorite) values (?,?,?,?,?,?,?,?,?,?)";
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
                            st.setString(1, "super test"); // nom
                            st.setString(2, ""); // prenom
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
                                p.setId(result.getLong(1));
                            }
                            // fermeture
                            st.close();


                        } catch (SQLException ex) {
                            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(c.wasUpdated())
                {
                    
                }
            }
       }
    }
    
}
