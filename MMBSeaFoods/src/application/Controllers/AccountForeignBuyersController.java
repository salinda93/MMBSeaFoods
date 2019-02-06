package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import application.Models.F_Fish_Buyers_Account;
import application.Models.Local_Buyers_Account;
import application.Services.AccountServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccountForeignBuyersController implements Initializable {
	
	@FXML
	private AnchorPane Accounts;
	
	AnchorPane add;
	
	@FXML private JFXComboBox<String>  cmbBuyersNames;

	@FXML private Label  lblBuyer;
	
	AccountServices accountServices=new AccountServices();
	
	private ObservableList<String> buyersNameList = FXCollections.observableArrayList();
	private ObservableList<F_Fish_Buyers_Account> buyersDetailsList = FXCollections.observableArrayList();

	
	@FXML private TableView<F_Fish_Buyers_Account> tblvBuyersDetails;
	
	@FXML private TableColumn<?, ?> tblcDate;
	@FXML private TableColumn<?, ?> tblcReason;
	@FXML private TableColumn<?, ?> tblcTopay;
	@FXML private TableColumn<?, ?> tblcPaid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		buyersNameList=accountServices.getAllBuyersNamesForeign();
		cmbBuyersNames.setItems(buyersNameList);
		
		
		cmbBuyersNames.valueProperty().addListener(new ChangeListener<String>() {
		        @Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
		          
		            System.out.println(t1);
		            
		            String name= t1;
					
					lblBuyer.setText(name);
					
					int id=accountServices.getBuyerIDByNameForeign(name);
					
					System.out.println(id);
					
					buyersDetailsList.clear();
					
					
					ArrayList<F_Fish_Buyers_Account> boat_list = accountServices.getAllBuyersListForeign(id);
					
					for( F_Fish_Buyers_Account boat : boat_list ) {
						buyersDetailsList.add(boat);
					} 
					tblcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
					tblcReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
					tblcTopay.setCellValueFactory(new PropertyValueFactory<>("To_Pay"));
					tblcPaid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

					tblvBuyersDetails.setItems(buyersDetailsList);
					
					
					
		        }    
		    });
		
		
		
	}
	
	
	public void addRecieved(ActionEvent event) throws IOException {
		
		String name=lblBuyer.getText();
		System.out.println(name);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Accounts/FAddBuyerReceived.fxml"));
		Parent root = loader.load();
		ForeignAddBuyerRecievedController controller = loader.<ForeignAddBuyerRecievedController>getController();
		controller.getBuyerName(name); 
		//controller.getBoatDetails(name);
		setNode(root);
		
		/*add=FXMLLoader.load(getClass().getResource("../Views/Accounts/LMakePayment.fxml"));
		setNode(add);*/
		
		
		
	}


	
	void setNode(Node node) {
		
		Accounts.getChildren().clear();
		Accounts.setTopAnchor(node,0.0);
		Accounts.setRightAnchor(node, 0.0);
		Accounts.setLeftAnchor(node, 0.0);
		Accounts.setBottomAnchor(node, 0.0);
		Accounts.getChildren().addAll((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
	
    }

}