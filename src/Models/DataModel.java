/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thonon
 */
public class DataModel implements IDataModel
{
    // liste des personnes
    private ObservableList<PersonModel> oPersons;
    
    private OPersonChangeListener listenerPersons; 

    public DataModel() 
    {
        oPersons = FXCollections.observableArrayList();
    }

    
    public ObservableList<PersonModel> getoPersons() {
        return oPersons;
    }

    public void setoPersons(ObservableList<PersonModel> oPersons) {
        this.oPersons = oPersons;
    }
    

    @Override
    public void loadData() 
    {
        if(listenerPersons != null)
            this.oPersons.removeListener(listenerPersons);
        
        try 
        {
            String sql = "select * from t_identity";
            Statement st = ConnectionSQL.getCon().createStatement();
            ResultSet result = st.executeQuery(sql);
            this.oPersons.clear();
            while(result.next())
            {
                PersonModel model = new PersonModel();
                model.setId(result.getInt("id"));
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setPriorite(result.getString("priorite"));
                model.setCategorie(result.getString("categorie"));
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setNumNational(result.getString("num_national"));
                java.sql.Date d = result.getDate("date_naissance");
                model.setDateNaissance(d.toLocalDate());
                model.setAdresse(result.getString("adresse"));
                model.setNumero(result.getString("numero"));
                model.setBoite(result.getString("boite"));
                model.setVille(result.getString("ville"));
                model.setCodePostal(result.getString("code_postal"));
                model.setCategorie(result.getString("categorie"));
                model.setPriorite(result.getString("priorite"));
                model.setEvenementRappel(result.getBoolean("evenement_rappel"));
                model.setPhoto(result.getBlob("photo"));
                // ajout dans le tableview
                this.oPersons.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(listenerPersons == null)
            listenerPersons = new OPersonChangeListener();
        
        // ajout du listener 
        this.oPersons.addListener(listenerPersons);
        
        
    }

    @Override
    public void writeData()
    {
        
    }
    
}
