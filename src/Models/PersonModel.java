/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class PersonModel extends Model implements ISQL
{
    

    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    private Date dateNaissance;
    private final StringProperty numNational = new SimpleStringProperty();
    private final StringProperty adresse = new SimpleStringProperty();
    private final StringProperty numero = new SimpleStringProperty();
    private final StringProperty boite = new SimpleStringProperty();
    private final StringProperty ville = new SimpleStringProperty();
    private final StringProperty codePostal = new SimpleStringProperty();
    private final StringProperty categorie = new SimpleStringProperty();
    private final IntegerProperty priorite = new SimpleIntegerProperty();

    public int getPriorite() {
        return priorite.get();
    }

    public void setPriorite(int value) {
        priorite.set(value);
    }

    public IntegerProperty prioriteProperty() {
        return priorite;
    }
    

    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String value) {
        categorie.set(value);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }
    

    public String getCodePostal() {
        return codePostal.get();
    }

    public void setCodePostal(String value) {
        codePostal.set(value);
    }

    public StringProperty codePostalProperty() {
        return codePostal;
    }
    

    public String getVille() {
        return ville.get();
    }

    public void setVille(String value) {
        ville.set(value);
    }

    public StringProperty villeProperty() {
        return ville;
    }
    

    public String getBoite() {
        return boite.get();
    }

    public void setBoite(String value) {
        boite.set(value);
    }

    public StringProperty boiteProperty() {
        return boite;
    }
    

    public String getNumero() {
        return numero.get();
    }

    public void setNumero(String value) {
        numero.set(value);
    }

    public StringProperty numeroProperty() {
        return numero;
    }
    

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String value) {
        adresse.set(value);
    }

    public StringProperty adresseProperty() {
        return adresse;
    }
    

    public String getNumNational() {
        return numNational.get();
    }

    public void setNumNational(String value) {
        numNational.set(value);
    }

    public StringProperty numNationalProperty() {
        return numNational;
    }
    
    
    
    

    public String getNom() {
        return nom.get();
    }

    public void setNom(String value) {
        nom.set(value);
    }

    public StringProperty nomProperty() {
        return nom;
    }
    
    
  
    

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String value) {
        prenom.set(value);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setDate(Date d)
    {
        this.dateNaissance = d;
    }
  
   public PersonModel()
   {
       dateNaissance = new Date();
   }

    @Override
    public void init() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

      /*
    "insert into t_identity (nom,"
                  + "prenom,"
                  + "date_naissance,"
                  + "num_national,"
                  + "adresse,"
                  + "numero,"
                  + "boite,"
                  + "ville,"
                  + "code_postal,"
                  + "categorie,"
                  + "priorite) values (?,?,?,?,?,?,?,?,?,?,?)";*/
    
    @Override
    public void select(int id) 
    {
        String sql = "select * from t_identity where id = ?";
        
        PreparedStatement st;
        try 
        {
            st = ConnectionSQL.getCon().prepareStatement(sql);
            
            st.setInt(1, id);
            
            ResultSet result = st.executeQuery();
            result.first();
            if(result != null)
            {
                this.setNom(result.getString("nom"));
                this.setPrenom(result.getString("prenom"));
                this.setDate(result.getDate("date_naissance"));
                this.setAdresse(result.getString("adresse"));
                this.setNumero(result.getString("numero"));
                this.setBoite(result.getString("boite"));
                this.setVille(result.getString("ville"));
                this.setCodePostal(result.getString("code_postal"));
                this.setCategorie(result.getString("categorie"));
                this.setPriorite(result.getInt("priorite"));
            }
        }
        catch(SQLException ex) 
        {
            
        }
       
        
    }

  
    @Override
    public void insert() 
    {
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
            st.setInt(11, 0); // priorite
            st.executeUpdate();
            ResultSet result = st.getGeneratedKeys();
            if(result != null && result.first())
            {
                this.setId(result.getLong(1));
            }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public void update() 
    {
       String sql = "update t_identity set nom = ?,"
               + "prenom = ?,"
               + "date_naissance = ?, "
               + "num_national = ?,"
               + "adresse = ?,"
               + "numero = ?,"
               + "boite = ?,"
               + "ville = ?,"
               + "code_postal = ?,"
               + "categorie = ?,"
               + "priorite = ?"
               + " where id = ?";
       
        try 
        {
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
            // update
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete() 
    {
        try {
            String sql = "delete from t_identity where id = ?";
            
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
            
            try {
                // suppression
                st.setLong(1, this.getId());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
   
    
}
