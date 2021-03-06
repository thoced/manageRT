/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DocumentViewPackage.DocumentController;
import GestionPackage.GestionController;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private ObservableList<ApostilleModel> oApostilles;
    private ObservableList<TodoModel> oTodos;
    private ObservableList<PersonModel> oLinks;
    private ObservableList<MemoModel> oMemos;
    
    // listener
    private ODocumentsChangeListener listenerDocuments;
    private oApostilleChangeListener listenerApostilles;
    private OTodoChangeListener listenerTodos;
    private OLinkChangeListener listenerLinks;
    private oMemoChangeListener listenerMemos;
    
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    //private LocalDate dateNaissance;
   
    private final StringProperty numNational = new SimpleStringProperty();
    private final StringProperty adresse = new SimpleStringProperty();
    private final StringProperty numero = new SimpleStringProperty();
    private final StringProperty boite = new SimpleStringProperty();
    private final StringProperty ville = new SimpleStringProperty();
    private final StringProperty codePostal = new SimpleStringProperty();
    private final ObjectProperty<CategorieModel> categorie = new SimpleObjectProperty<>();
    private final ObjectProperty<PrioriteModel> priorite = new SimpleObjectProperty<>();

    public ObservableList<PersonModel> getoLinks() {
        return oLinks;
    }

    public void setoLinks(ObservableList<PersonModel> oLinks) {
        this.oLinks = oLinks;
    }

    
    
    public CategorieModel getCategorie() {
        return categorie.get();
    }

    public void setCategorie(CategorieModel value) {
        categorie.set(value);
    }

    public ObjectProperty categorieProperty() {
        return categorie;
    }
    

    public PrioriteModel getPriorite() {
        return priorite.get();
    }

    public void setPriorite(PrioriteModel value) {
        priorite.set(value);
    }

    public ObjectProperty prioriteProperty() {
        return priorite;
    }
    
    
    private final ObjectProperty<LocalDate> dateNaissance = new SimpleObjectProperty<>();
    private Blob photo;
    private final BooleanProperty evenementRappel = new SimpleBooleanProperty();

    public boolean isEvenementRappel() {
        return evenementRappel.get();
    }

    public void setEvenementRappel(boolean value) {
        evenementRappel.set(value);
    }

    public BooleanProperty evenementRappelProperty() {
        return evenementRappel;
    }
    
    public ObservableList<DocumentModel> getoDocuments() {
        return oDocuments;
    }

    public void setoDocuments(ObservableList<DocumentModel> oDocuments) {
        this.oDocuments = oDocuments;
    }

    public ObservableList<ApostilleModel> getoApostilles() {
        return oApostilles;
    }

    public void setoApostilles(ObservableList<ApostilleModel> oApostilles) {
        this.oApostilles = oApostilles;
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

    public ObservableList<MemoModel> getoMemos() {
        return oMemos;
    }
    
    

    @Override
    public String toString() 
    {
        return nom.getValue() + " " + prenom.getValue() + " (" + dateNaissance.getValue().format(DateTimeFormatter.ofPattern("d MMM uuuu")) + ")";
    }

   
  
   public PersonModel()
   {
      oDocuments = FXCollections.observableArrayList();
      
      oApostilles = FXCollections.observableArrayList();
      
      oTodos = FXCollections.observableArrayList();
      
      oLinks = FXCollections.observableArrayList();
      
      oMemos = FXCollections.observableArrayList();

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
   public void undo() throws SQLException, NullPointerException
   {
       String sql = "select * from t_identity where id = ?";
       
       
            PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
            st.setLong(1, this.getId());
            ResultSet result = st.executeQuery();
         
            while(result.next())
            {
                PersonModel model = new PersonModel();
                this.setId(result.getInt("id"));
                this.setNom(result.getString("nom"));
                this.setPrenom(result.getString("prenom"));
                this.setPriorite(new PrioriteModel(result.getString("priorite")));
                this.setCategorie(new CategorieModel(result.getString("categorie")));
                this.setNom(result.getString("nom"));
                this.setPrenom(result.getString("prenom"));
                this.setNumNational(result.getString("num_national"));
                java.sql.Date d = result.getDate("date_naissance");
                this.setDateNaissance(d.toLocalDate());
                this.setAdresse(result.getString("adresse"));
                this.setNumero(result.getString("numero"));
                this.setBoite(result.getString("boite"));
                this.setVille(result.getString("ville"));
                this.setCodePostal(result.getString("code_postal"));
               // this.setCategorie(result.getString("categorie"));
               // this.setPriorite(result.getString("priorite"));
                this.setEvenementRappel(result.getBoolean("evenement_rappel"));
                this.setPhoto(result.getBlob("photo"));
                
           
        }
   }
   
    public void update() throws SQLException,NullPointerException 
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
               + "photo = ?,"
               + "evenement_rappel = ?"
               
               + " where id = ?";
       
       
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
            st.setString(10, this.getCategorie().getType()); // categorie
            st.setString(11, this.getPriorite().getType()); // priorite
            st.setBlob(12, this.getPhoto());
            st.setBoolean(13, this.isEvenementRappel());
            st.setLong(14, this.getId());
            
            // update
            st.execute();
      
    }
/*
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
         try 
         {
             if(listenerDocuments != null)
                 this.oDocuments.removeListener(listenerDocuments);
              if(listenerApostilles != null)
                 this.oApostilles.removeListener(listenerApostilles);
             if(listenerTodos != null)
                 this.oTodos.removeListener(listenerTodos);
             if(listenerLinks != null)
                 this.oLinks.removeListener(listenerLinks);
             if(listenerMemos != null)
                 this.oMemos.removeListener(listenerMemos);
             
             // Chargement de la liste des documents
             String sql = "select * from t_documents where ref_id_identity = ?";
             try
             {
                 // statement
                 PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql);
                 st.setLong(1, this.getId());
                 ResultSet result = st.executeQuery();
                 oDocuments.clear();
                 oApostilles.clear();
                 while(result.next())
                 {
                      
                     if(result.getString("type_document").equals("doc"))
                     {
                         DocumentModel model = new DocumentModel();
                         model.setId(result.getLong("id"));
                         model.setNom(result.getString("nom"));
                         model.setCommentaire(result.getString("commentaire"));
                         model.setFichier(result.getBlob("fichier"));
                         oDocuments.add(model);
                     }
                     
                     if(result.getString("type_document").equals("apo"))
                     {
                         ApostilleModel model = new ApostilleModel();
                         model.setId(result.getLong("id"));
                         model.setNom(result.getString("nom"));
                         model.setCommentaire(result.getString("commentaire"));
                         model.setFichier(result.getBlob("fichier"));
                         oApostilles.add(model);
                     }
                 }
                 // fermeture
                 st.close();
                 
             } catch (SQLException ex)
             {
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
                 // fermeture
                 st.close();
                 
             } catch (SQLException ex) {
                 Logger.getLogger(GestionController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
             // Chargement de la list links
             String sql_l = "select distinct id,nom,prenom,date_naissance from t_identity where t_identity.id <> ? AND t_identity.id IN "
                     + "(select ref_id_identity01 from t_link_identity where ref_id_identity01 = ? "
                     + "OR "
                     + "ref_id_identity02 = ? "
                     + "UNION "
                     + "select ref_id_identity02 from t_link_identity where ref_id_identity01 = ? "
                     + "OR ref_id_identity02 = ?)";
             
             PreparedStatement st = ConnectionSQL.getCon().prepareStatement(sql_l);
             st.setLong(1, this.getId());
             st.setLong(2, this.getId());
             st.setLong(3, this.getId());
             st.setLong(4, this.getId());
             st.setLong(5, this.getId());
             ResultSet result = st.executeQuery();
             this.oLinks.clear();
             while(result.next())
             {
                 PersonModel model = new PersonModel();
                    model.setId(result.getInt("id"));
                    model.setNom(result.getString("nom"));
                    model.setPrenom(result.getString("prenom"));
                    java.sql.Date d = result.getDate("date_naissance");
                    if(d != null)
                        model.setDateNaissance(d.toLocalDate());
                    else
                        model.setDateNaissance(LocalDate.now());
                   /* model.setPriorite(new PrioriteModel(result.getString("priorite")));
                    model.setCategorie(new CategorieModel(result.getString("categorie")));
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
                    model.setPhoto(result.getBlob("photo"));*/
                    // ajout dans le tableview
                    this.oLinks.add(model);
             }
             //fermeture 
             st.close();
             
             // Chargement de la liste des mémos
             this.oMemos.clear();
             String sql_memo = "select * from t_memos where ref_id_identity = ?";
             PreparedStatement pst = ConnectionSQL.getCon().prepareStatement(sql_memo);
             pst.setLong(1, this.getId());
             ResultSet result_memo = pst.executeQuery();
             while(result_memo.next())
             {
                 MemoModel model = new MemoModel(result_memo.getLong("id"));
                 model.setText(result_memo.getString("text"));
                 model.setDateTime(result_memo.getTimestamp("date_time").toLocalDateTime());
                 this.oMemos.add(model);  
             }
             
                 
             
             
             
             // listener
             // OTodoChangeListener listener = new OTodoChangeListener(this.getIdPerson());
             //oTodo.addListener(listener);
             
             //listener
             if(listenerDocuments == null)
                 listenerDocuments = new ODocumentsChangeListener(this.getId());
             
              if(listenerApostilles == null)
                 listenerApostilles = new oApostilleChangeListener(this.getId());
             
             if(listenerTodos == null)
                 listenerTodos = new OTodoChangeListener(this.getId());
             
             if(listenerLinks == null)
                 listenerLinks = new OLinkChangeListener(this.getId());
             
             if(listenerMemos == null)
                 listenerMemos = new oMemoChangeListener(this.getId());
             
             oDocuments.addListener(listenerDocuments);
             oApostilles.addListener(listenerApostilles);
             oTodos.addListener(listenerTodos);
             oLinks.addListener(listenerLinks);
             oMemos.addListener(listenerMemos);
             
         } catch (SQLException ex) 
         {
             Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
            
         }
         
    }

    @Override
    public void writeData() 
    {
       
    }
   
    
}
