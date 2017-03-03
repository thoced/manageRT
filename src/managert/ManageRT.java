/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

import LoginPackage.LoginController;
import Models.ConnectionSQL;
import Models.PersonModel;
import PersonViewPackage.PersonController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Thonon
 */
public class ManageRT extends Application 
{
    private BorderPane rootPane;
    
    // class static de connection
    public static ConnectionSQL connectionSQL;
    
    public ManageRT()
    {
       
        try 
        {
            // creation de l'objet static
            connectionSQL = new ConnectionSQL();
             
        } catch (SQLException ex) {
            Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) 
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur I/O");
            alert.setContentText("Erreur de chargement du fichier de configuration managert.ini (" + ex.getMessage());
            alert.showAndWait();
                    
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        // ouverture de la vue de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPackage/LoginView.fxml"));
        AnchorPane pane = loader.load();
        LoginController controller = loader.getController();
        Scene s = new Scene(pane);
        Stage st = new Stage();
        st.setScene(s);
        st.setResizable(false);
        st.setMaximized(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.showAndWait();
        
        
        if(controller.isIsLogin()) // si la connexion est établie et acceptée
        {
             try 
          {
            // suppression des evenemnt rappel
            String sql = "update t_identity set evenement_rappel = false";
            Statement stat = ConnectionSQL.getCon().createStatement();
            stat.executeUpdate(sql);
              
            // recherche des todo de rappel
            sql = "update t_identity set evenement_rappel = true "
                    + "where id IN (select ref_id_identity from t_todo "
                    + "where DATE(NOW()) >= date_rappel AND rappel = true)";
            
            stat.executeUpdate(sql);
            stat.close();
            
           } catch (SQLException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

            rootPane = FXMLLoader.load(getClass().getResource("MainView.fxml"));
            Scene scene = new Scene(rootPane);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Manager Radicalisme / Terrorisme");
            stage.show();
            // showtablepersonview
           
        }
        
         
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
