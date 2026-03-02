package com.gabriel.userms;
import com.gabriel.userms.model.User;
import com.gabriel.userms.service.UserService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import javafx.util.StringConverter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;

public class GenericUserController implements Initializable{
	@Setter
	CreateUserController createUserController;

	@Setter
	DeleteUserController deleteUserController ;

	@Setter
	EditUserController editUserController;

	@Setter
	ManageUserController manageUserController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<User> lvUsers;

	@Setter
	public static User selectedItem;
	public TextField txtId;
	public ComboBox<User> cmbUser;
	public TextField txtUsername;
	public TextField txtUserName;
	public TextField txtDisplayName;
	public TextField txtEmail;
	public TextField txtAvatarUrl;
	public TextField txtOnline;
	public DatePicker dtLastSeen;
	public TextField txtStatusMessage;
	public TextField txtDeviceToken;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		User[] users =  (User[]) UserService.getService().getAll();
		cmbUser.getItems().addAll(users);
		StringConverter<User> userConverter = new StringConverter<User>() {
			@Override
			public String toString(User user) {
			if(user==null)
				return "";
			else
				return user.toString();
			}
			@Override
			public User fromString(String s) {
				if(!s.isEmpty()){
					for (User user : users) {
						if (s.equals(user.getUsername())){
							return user;
						}
					}
				}
				return null;
			}
		};
		cmbUser.setConverter(userConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected User toObject(boolean isEdit){
		User user= new User();
		try {
			if(isEdit) {
				user.setId(Integer.parseInt(txtId.getText()));
			}
			User selectedUser = cmbUser.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				user.setUserId(selectedUser.getUserId());
			}
			user.setUsername(txtUsername.getText());
			user.setEmail(txtEmail.getText());
			user.setAvatarUrl(txtAvatarUrl.getText());
			user.setOnline(Boolean.parseBoolean(txtOnline.getText()));
			user.setLastSeen(toDate(dtLastSeen.getValue()));
			user.setStatusMessage(txtStatusMessage.getText());
			user.setDeviceToken(txtDeviceToken.getText());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return user;
	}
	protected void setFields(String action){
		User user = GenericUserController.selectedItem;
		txtId.setText(Integer.toString(user.getId()));
		User fetchedUser = UserService.getService().get(user.getUserId());
		cmbUser.getSelectionModel().select(fetchedUser);
		if(action.equals("Create") || action.equals("Edit")){
			cmbUser.setVisible(true);
			txtUserName.setVisible(false);
			cmbUser.getSelectionModel().select(fetchedUser);
		}
		else{
			cmbUser.setVisible(false);
			txtUserName.setVisible(true);
			txtUserName.setText(fetchedUser.getUsername());
		}
		txtUsername.setText(fetchedUser.getUsername());
		txtDisplayName.setText(fetchedUser.getDisplayName());
		txtEmail.setText(fetchedUser.getEmail());
		txtAvatarUrl.setText(fetchedUser.getAvatarUrl());
		txtOnline.setText(Boolean.toString(fetchedUser.isOnline()));
		dtLastSeen.setValue(toLocalDate(fetchedUser.getLastSeen()));
		txtStatusMessage.setText(fetchedUser.getStatusMessage());
		txtDeviceToken.setText(fetchedUser.getDeviceToken());
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbUser.getSelectionModel().clearSelection();
		txtUserName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbUser.setVisible(true);
			txtUserName.setVisible(false);
		}
		else{
			cmbUser.setVisible(false);
			txtUserName.setVisible(true);
		}
		//txtUsername.setText("");
		//txtDisplayName.setText("");
		//txtEmail.setText("");
		//txtAvatarUrl.setText("");
		//txtOnline.setText("");
		//dtLastSeen.setText("");
		//txtStatusMessage.setText("");
		//txtDeviceToken.setText("");
	}

	protected void enableFields(boolean enable){
		cmbUser.editableProperty().set(enable);
		txtUserName.editableProperty().set(enable);
		txtUsername.editableProperty().set(enable);
		txtDisplayName.editableProperty().set(enable);
		txtEmail.editableProperty().set(enable);
		txtAvatarUrl.editableProperty().set(enable);
		txtOnline.editableProperty().set(enable);
		dtLastSeen.editableProperty().set(enable);
		txtStatusMessage.editableProperty().set(enable);
		txtDeviceToken.editableProperty().set(enable);
	}

	public int getId(){
		return Integer.parseInt(txtId.getText());
	}

	protected void showErrorDialog(String message, String expandedMessage){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(expandedMessage)));
		alert.showAndWait();
	}
	public void onBack(ActionEvent actionEvent) {
		Node node = ((Node) (actionEvent.getSource()));
		Window window = node.getScene().getWindow();
		window.hide();
		stage.setScene(manageScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
	}
	LocalDate toLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = instant.atZone( z );
		return zdt.toLocalDate();
	}
	protected Date toDate(LocalDate ld){
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = ld.atStartOfDay(z);
		Instant instant  = zdt.toInstant();
		return Date.from(instant);
	}
}

