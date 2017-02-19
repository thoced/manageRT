/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DocumentViewPackage.DocumentController;
import DocumentViewPackage.ODocumentsChangeListener;
import GestionPackage.GestionController;
import GestionPackage.OTodoChangeListener;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thonon
 */
public class PersonModel extends Model implements IDataModel
{
   // public static  ObservableList<PersonModel> listPerson = FXCollections.observableArrayList();
    
    private ObservableList<DocumentModel> oDocuments;
    
    private ObservableList<TodoModel> oTodos;
    
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    //private LocalDate dateNaissance;
   
    private final StringProperty numNational = new SimpleStringProperty();
    private final StringProperty adresse = new SimpleStringProperty();
    private final StringProperty numero = new SimpleStringProperty();
    private final StringProperty boite = new SimpleStringProperty();
    private final StringProperty ville = new SimpleStringProperty();
    private final StringProperty codePostal = new SimpleStringProperty();
    private final StringProperty categorie = new SimpleStringProperty();
    private final StringProperty priorite = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateNaissance = new SimpleObjectProperty<>();
    private Blob photo;

    
    
    public ObservableList<DocumentModel> getoDocuments() {
        return oDocuments;
    }

    public void setoDocuments(ObservableList<DocumentModel> oDocuments) {
        this.oDocuments = oDocuments;
    }

    public ObservableList<TodoModel> getoTodos() {
        return oTodos;
    }

    public void setoTodos(ObservableList<TodoModel> oTodos) {
        this.oTodos = oTodos;
    }

    
    
    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
    
        public LocalDate getDateNaissance() {
        return dateNaissance.get();
    }

    public void setDateNaissance(LocalDate value) {
        dateNaissance.set(value);
    }

    public ObjectProperty dateNaissanceProperty() {
        return dateNaissance;
    }

    
    
    public String getPriorite() {
        return priorite.get();
    }

    public void setPriorite(String value) {
        priorite.set(value);
    }

    public StringProperty prioriteProperty() {
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

   
  
   public PersonModel()
   {
      oDocuments = FXCollections.observableArrayList();
      
      oTodos = FXCollections.observableArrayList();
   }
     
   
    /*@Override
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
            st.setString(11, ""); // priorite
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
*/
  /*  @Override
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
               + "priorite = ?,"
               + "photo = ?"
               + " where id = ?";
       
        try 
        {
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
            st.setString(1, this.getNom()); // nom
            st.setString(2, this.getPrenom()); // prenom
            st.setDate(3, java.sql.Date.valueOf(this.getDateNaissance())); // date_naissance
            st.setString(4, this.getNumNational()); // num_national
            st.setString(5, this.getAdresse()); // adresse
            st.setString(6, this.getNumero()); // numero
            st.setString(7, this.getBoite()); // boite
            st.setString(8, this.getVille()); // ville
            st.setString(9, this.getCodePostal()); // code_postal
            st.setString(10, this.getCategorie()); // categorie
            st.setString(11, this.getPriorite()); // priorite
            st.setBlob(12, this.getPhoto());
            st.setLong(13, this.getId());
            
            // update
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
  /*  @Override
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

 */

    @Override
    public void loadData() 
    {
        // Chargement de la liste des documents
         String sql = "select * from t_documents where ref_id_identity = ?";
         try 
         {
             // statement
             PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
             st.setLong(1, this.getId());
             ResultSet result = st.executeQuery();
             oDocuments.clear();
             while(result.next())
             {
                 DocumentModel model = new DocumentModel();
                 model.setId(result.getLong("id"));
                 model.setNom(result.getString("nom"));
                 model.setCommentaire(result.getString("commentaire"));
                 model.setFichier(result.getBlob("fichier"));
                 oDocuments.add(model);
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         // Chargement de la liste des ToDo
         try{
            
            // chargement des valeurs depuis le model db
            String sql2 = "select * from t_todo where ref_id_identity = ?";
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql2);
            st.setLong(1,this.getId());
            ResultSet result = st.executeQuery();
            oTodos.clear();
            while(result.next())
            {
                TodoModel model = new TodoModel();
                model.setId(result.getLong("id"));
                model.setTitre(result.getString("titre"));
                model.setText(result.getString("text"));
                model.setDateCreation(result.getDate("date_creation").toLocalDate());
                model.setDateRappel(result.getDate("date_rappel").toLocalDate());
                model.setRappel(result.getBoolean("rappel"));
                oTodos.add(model);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // listener
       // OTodoChangeListener listener = new OTodoChangeListener(this.getIdPerson());
        //oTodo.addListener(listener);
       
         // listener
         //ODocumentsChangeListener listener = new ODocumentsChangeListener(this.personId);
         //oDocuments.addListener(listener);
    }

    @Override
    public void writeData() 
    {
       
    }
   
    
}
