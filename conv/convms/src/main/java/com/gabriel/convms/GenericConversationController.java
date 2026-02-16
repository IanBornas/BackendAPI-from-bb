package com.gabriel.convms;
import com.gabriel.convms.model.Conversation;
import com.gabriel.convms.model.Conversation;
import com.gabriel.convms.service.ConversationService;
import com.gabriel.convms.model.Creator;
import com.gabriel.convms.service.CreatorService;
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
import javafx.stage.Window;
import lombok.Setter;
import javafx.util.StringConverter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale;

public class GenericConversationController implements Initializable{
	@Setter
	CreateConversationController createConversationController;

	@Setter
	DeleteConversationController deleteConversationController ;

	@Setter
	EditConversationController editConversationController;

	@Setter
	ManageConversationController manageConversationController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Conversation> lvConversations;

	@Setter
	public static Conversation selectedItem;
	public TextField txtId;
	public ComboBox<Conversation> cmbConversation;
	public TextField txtConversationName;
	public TextField txtConversationType;
	public DatePicker dtCreatedAt;
	public ComboBox<Creator> cmbCreator;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Conversation[] conversations =  (Conversation[]) ConversationService.getService().getAll();
		cmbConversation.getItems().addAll(conversations);
		StringConverter<Conversation> conversationConverter = new StringConverter<Conversation>() {
			@Override
			public String toString(Conversation conversation) {
			if(conversation==null)
				return "";
			else
				return conversation.toString();
			}
			@Override
			public Conversation fromString(String s) {
				if(!s.isEmpty()){
					for (Conversation conversation : conversations) {
						if (s.equals(conversation.getName())){
							return conversation;
						}
					}
				}
				return null;
			}
		};
		cmbConversation.setConverter(conversationConverter);
		Creator[] creators =  (Creator[]) CreatorService.getService().getAll();
		cmbCreator.getItems().addAll(creators);
		StringConverter<Creator> creatorConverter = new StringConverter<Creator>() {
			@Override
			public String toString(Creator creator) {
			if(creator==null)
				return "";
			else
				return creator.toString();
			}
			@Override
			public Creator fromString(String s) {
				if(!s.isEmpty()){
					for (Creator creator : creators) {
						if (s.equals(creator.getName())){
							return creator;
						}
					}
				}
				return null;
			}
		};
		cmbCreator.setConverter(creatorConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected Conversation toObject(boolean isEdit){
		Conversation conversation= new Conversation();
		try {
			if(isEdit) {
				conversation.setId(Integer.parseInt(txtId.getText()));
			}
			Conversation conversation = cmbConversation.getSelectionModel().getSelectedItem();
			conversation.setConversationId(conversation.getId());
			conversation.setConversationName(conversation.getName());
			conversation.setConversationType(txtConversationType.getText());
			conversation.setCreatedAt(toDate(dtCreatedAt.getValue()));
			Creator creator = cmbCreator.getSelectionModel().getSelectedItem();
			conversation.setCreatorId(creator.getId());
			conversation.setCreatorName(creator.getName());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return conversation;
	}
	protected void setFields(String action){
		String formattedDate;
		Conversation conversation = GenericConversationController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(conversation.getId()));
		Conversation conversation = ConversationService.getService().get(conversation.getConversationId());
		cmbConversation.getSelectionModel().select(conversation);
		if(action.equals("Create") || action.equals("Edit")){
			cmbConversation.setVisible(true);
			txtConversationName.setVisible(false);
			cmbConversation.getSelectionModel().select(conversation);
		}
		else{
			cmbConversation.setVisible(false);
			txtConversationName.setVisible(true);
			txtConversationName.setText(conversation.getName());
		}
		txtConversationName.setText(conversation.getConversationName());
		txtConversationType.setText(conversation.getConversationType());
		dtCreatedAt.setValue(toLocalDate(conversation.getCreatedAt()));
		Creator creator = CreatorService.getService().get(conversation.getCreatorId());
		cmbCreator.getSelectionModel().select(creator);
		if(action.equals("Create") || action.equals("Edit")){
			cmbCreator.setVisible(true);
			txtCreatorName.setVisible(false);
			cmbCreator.getSelectionModel().select(creator);
		}
		else{
			cmbCreator.setVisible(false);
			txtCreatorName.setVisible(true);
			txtCreatorName.setText(creator.getName());
		}
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbConversation.getSelectionModel().clearSelection();
		txtConversationName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbConversation.setVisible(true);
			txtConversationName.setVisible(false);
		}
		else{
			cmbConversation.setVisible(false);
			txtConversationName.setVisible(true);
		}
		//txtConversationName.setText("");
		//txtConversationType.setText("");
		//dtCreatedAt.setText("");
		cmbCreator.getSelectionModel().clearSelection();
		txtCreatorName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbCreator.setVisible(true);
			txtCreatorName.setVisible(false);
		}
		else{
			cmbCreator.setVisible(false);
			txtCreatorName.setVisible(true);
		}
	}

	protected void enableFields(boolean enable){
		cmbConversation.editableProperty().set(enable);
		txtConversationName.editableProperty().set(enable);
		txtConversationName.editableProperty().set(enable);
		txtConversationType.editableProperty().set(enable);
		dtCreatedAt.editableProperty().set(enable);
		cmbCreator.editableProperty().set(enable);
		txtCreatorName.editableProperty().set(enable);
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

