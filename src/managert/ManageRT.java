/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managert;

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

/**
 *
 * @author Thonon
 */
public class ManageRT extends Application implements EventHandler
{
    private BorderPane rootPane;
    
    private ObservableList<PersonModel> listPerson;
    
    // class static de connection
    public static ConnectionSQL connectionSQL;
    
    public ManageRT()
    {
        listPerson = FXCollections.observableArrayList();
        try 
        {
            // connection à la base de donnée
            connectionSQL = new ConnectionSQL();
         
            
        } catch (SQLException ex) {
            Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        rootPane = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.show();
        
        // showtablepersonview
        this.ShowTablePersonView();
    }
    
    public void ShowTablePersonView() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TablePersonView.fxml"));
        // chargement de la vue
        BorderPane borderPane = loader.load();
        // récupération du controller
        TablePersonController tpc = loader.getController();
        tpc.getTable().setItems(listPerson);

        // set du borderPane dans le rootPane
        rootPane.setCenter(borderPane);
        
        // ajout de l'handler sur le tableperson
        tpc.getTable().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        
        // test
        PersonModel p = new PersonModel();
        p.setNom("Thonon");
        p.setPrenom("Cedric");
        
    
        listPerson.add(p);
      
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(Event event)
    {
        // handler du clic sur le tableview
        if(event.getSource().getClass() == TableView.class)
        {
  
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PersonViewPackage/PersonView.fxml"));
            try 
            {
                AnchorPane ap = loader.load();
                // recuperation du controller
                PersonController pc = loader.getController();
                // récupération du model
                PersonModel model = (PersonModel)((TableView)event.getSource()).getSelectionModel().getSelectedItem();
                if(model != null)
                    model.select(4);
                // set du model
                pc.setModel(model);
                // création de la scene
                Scene scene = new Scene(ap);
                // creatin du stage
                Stage stage = new Stage();
                // ajout de la scene au stage
                stage.setScene(scene);
                // affichage de la vue
                stage.showAndWait();
                
            } catch (IOException ex) {
                Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ManageRT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
