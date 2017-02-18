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
import java.sql.SQLException;
import java.util.ArrayList;
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
    
   private TablePersonController tpc;
  
    
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
            rootPane = FXMLLoader.load(getClass().getResource("MainView.fxml"));
            Scene scene = new Scene(rootPane);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            // showtablepersonview
            this.ShowTablePersonView();
        }
    }
    
    public void ShowTablePersonView() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TablePersonView.fxml"));
        // chargement de la vue
        BorderPane borderPane = loader.load();
        // récupération du controller
        TablePersonController tpc = loader.getController();

        // set du borderPane dans le rootPane
        rootPane.setCenter(borderPane);
       
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
