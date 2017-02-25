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
    private static ObservableList<PersonModel> oPersons  = FXCollections.observableArrayList();;
    private OPersonChangeListener listenerPersons; 
    
    // liste des types de priorité
    private static ObservableList<PrioriteModel> oPriorites = FXCollections.observableArrayList();;
    // liste des types de catégorie
    private static ObservableList<CategorieModel> oCategories = FXCollections.observableArrayList();;

    public DataModel() 
    {
        /*oPersons = FXCollections.observableArrayList();
        oPriorites = FXCollections.observableArrayList();
        oCategories = FXCollections.observableArrayList();*/
        
    }

    public static ObservableList<PrioriteModel> getoPriorites() {
        return oPriorites;
    }

    public static void setoPriorites(ObservableList<PrioriteModel> oPriorites) {
        oPriorites = oPriorites;
    }

    public static ObservableList<CategorieModel> getoCategories() {
        return oCategories;
    }

    public static void setoCategories(ObservableList<CategorieModel> oCategories) {
        oCategories = oCategories;
    }

    
    
    public static ObservableList<PersonModel> getoPersons() {
        return oPersons;
    }

    public static void setoPersons(ObservableList<PersonModel> oPersons) {
        oPersons = oPersons;
    }
    

    @Override
    public void loadData() 
    {
        try 
        {
            if(listenerPersons != null)
                this.oPersons.removeListener(listenerPersons);
            
            // chargement de la liste des priorité
            String sql_priorite = "select * from t_type_priorite";
            Statement st_priorite = ConnectionSQL.getCon().createStatement();
            ResultSet result_priorite = st_priorite.executeQuery(sql_priorite);
            oPriorites.clear();
            while(result_priorite.next())
            {
                PrioriteModel model = new PrioriteModel();
                model.setId(result_priorite.getLong("id"));
                model.setType(result_priorite.getString("type"));
                oPriorites.add(model);
            }
            st_priorite.close();
            
            
            // chargement de la liste des catégorie
            String sql_categorie = "select * from t_type_categorie";
            Statement st_categorie = ConnectionSQL.getCon().createStatement();
            ResultSet result_categorie = st_categorie.executeQuery(sql_categorie);
            oCategories.clear();
            while(result_categorie.next())
            {
                CategorieModel model = new CategorieModel();
                model.setId(result_categorie.getLong("id"));
                model.setType(result_categorie.getString("type"));
                oCategories.add(model);
            }
            st_categorie.close();
            
            
            
           
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
                    model.setPriorite(new PrioriteModel(result.getString("priorite")));
                    model.setCategorie(new CategorieModel(result.getString("categorie")));
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
                   // model.setCategorie(result.getString("categorie"));
                   // model.setPriorite(result.getString("priorite"));
                    model.setEvenementRappel(result.getBoolean("evenement_rappel"));
                    model.setPhoto(result.getBlob("photo"));
                    // ajout dans le tableview
                    this.oPersons.add(model);
                }
           
            
            if(listenerPersons == null)
                listenerPersons = new OPersonChangeListener();
            
            // ajout du listener
            this.oPersons.addListener(listenerPersons);
            
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(DataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void writeData()
    {
        
    }
    
}
