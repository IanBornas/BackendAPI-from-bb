package com.gabriel.messms;
import com.gabriel.messms.model.Message;
import com.gabriel.messms.service.MessageService;
import com.gabriel.messms.model.Conversation;
import com.gabriel.messms.service.ConversationService;
import com.gabriel.messms.model.Sender;
import com.gabriel.messms.service.SenderService;
import com.gabriel.messms.model.ReplyToMessage;
import com.gabriel.messms.service.ReplyToMessageService;
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

public class GenericMessageController implements Initializable{
	@Setter
	CreateMessageController createMessageController;

	@Setter
	DeleteMessageController deleteMessageController ;

	@Setter
	EditMessageController editMessageController;

	@Setter
	ManageMessageController manageMessageController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Message> lvMessages;

	@Setter
	public static Message selectedItem;
	public TextField txtId;
	public ComboBox<Message> cmbMessage;
	public ComboBox<Conversation> cmbConversation;
	public ComboBox<Sender> cmbSender;
	public TextField txtContent;
	public TextField txtMessageType;
	public DatePicker dtSentAt;
	public DatePicker dtDeliveredAt;
	public DatePicker dtReadAt;
	public TextField txtMediaUrl;
	public ComboBox<ReplyToMessage> cmbReplyToMessage;
	public TextField txtMessageName;
	public TextField txtConversationName;
	public TextField txtSenderName;
	public TextField txtReplyToMessageName;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Message[] messages =  (Message[]) MessageService.getService().getAll();
		cmbMessage.getItems().addAll(messages);
		StringConverter<Message> messageConverter = new StringConverter<Message>() {
			@Override
			public String toString(Message message) {
			if(message==null)
				return "";
			else
				return message.toString();
			}
			@Override
			public Message fromString(String s) {
				if(!s.isEmpty()){
					for (Message message : messages) {
						if (s.equals(message.getContent())){
							return message;
						}
					}
				}
				return null;
			}
		};
		cmbMessage.setConverter(messageConverter);
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
		Sender[] senders =  (Sender[]) SenderService.getService().getAll();
		cmbSender.getItems().addAll(senders);
		StringConverter<Sender> senderConverter = new StringConverter<Sender>() {
			@Override
			public String toString(Sender sender) {
			if(sender==null)
				return "";
			else
				return sender.toString();
			}
			@Override
			public Sender fromString(String s) {
				if(!s.isEmpty()){
					for (Sender sender : senders) {
						if (s.equals(sender.getName())){
							return sender;
						}
					}
				}
				return null;
			}
		};
		cmbSender.setConverter(senderConverter);
		ReplyToMessage[] replyToMessages =  (ReplyToMessage[]) ReplyToMessageService.getService().getAll();
		cmbReplyToMessage.getItems().addAll(replyToMessages);
		StringConverter<ReplyToMessage> replyToMessageConverter = new StringConverter<ReplyToMessage>() {
			@Override
			public String toString(ReplyToMessage replyToMessage) {
			if(replyToMessage==null)
				return "";
			else
				return replyToMessage.toString();
			}
			@Override
			public ReplyToMessage fromString(String s) {
				if(!s.isEmpty()){
					for (ReplyToMessage replyToMessage : replyToMessages) {
						if (s.equals(replyToMessage.getName())){
							return replyToMessage;
						}
					}
				}
				return null;
			}
		};
		cmbReplyToMessage.setConverter(replyToMessageConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected Message toObject(boolean isEdit){
		Message message= new Message();
		try {
			if(isEdit) {
				message.setId(Integer.parseInt(txtId.getText()));
			}
			Message selectedMessage = cmbMessage.getSelectionModel().getSelectedItem();
			if (selectedMessage != null) {
				message.setMessageId(selectedMessage.getMessageId());
			}
			Conversation conversation = cmbConversation.getSelectionModel().getSelectedItem();
			if (conversation != null) {
				message.setConversationId(conversation.getId());
			}
			Sender sender = cmbSender.getSelectionModel().getSelectedItem();
			if (sender != null) {
				message.setSenderId(sender.getId());
			}
			message.setContent(txtContent.getText());
			message.setMessageType(txtMessageType.getText());
			message.setSentAt(toDate(dtSentAt.getValue()));
			message.setDeliveredAt(toDate(dtDeliveredAt.getValue()));
			message.setReadAt(toDate(dtReadAt.getValue()));
			message.setMediaUrl(txtMediaUrl.getText());
			ReplyToMessage replyToMessage = cmbReplyToMessage.getSelectionModel().getSelectedItem();
			if (replyToMessage != null) {
				message.setReplyToMessageId(replyToMessage.getId());
			}
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return message;
	}
	protected void setFields(String action){
		Message message = GenericMessageController.selectedItem;
		txtId.setText(Integer.toString(message.getId()));
		Message fetchedMessage = MessageService.getService().get(message.getMessageId());
		cmbMessage.getSelectionModel().select(fetchedMessage);
		if(action.equals("Create") || action.equals("Edit")){
			cmbMessage.setVisible(true);
			txtMessageName.setVisible(false);
			cmbMessage.getSelectionModel().select(fetchedMessage);
		}
		else{
			cmbMessage.setVisible(false);
			txtMessageName.setVisible(true);
			txtMessageName.setText(fetchedMessage.getContent());
		}
		Conversation conversation = ConversationService.getService().get(fetchedMessage.getConversationId());
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
		Sender sender = SenderService.getService().get(fetchedMessage.getSenderId());
		cmbSender.getSelectionModel().select(sender);
		if(action.equals("Create") || action.equals("Edit")){
			cmbSender.setVisible(true);
			txtSenderName.setVisible(false);
			cmbSender.getSelectionModel().select(sender);
		}
		else{
			cmbSender.setVisible(false);
			txtSenderName.setVisible(true);
			txtSenderName.setText(sender.getName());
		}
		txtContent.setText(fetchedMessage.getContent());
		txtMessageType.setText(fetchedMessage.getMessageType());
		dtSentAt.setValue(toLocalDate(fetchedMessage.getSentAt()));
		dtDeliveredAt.setValue(toLocalDate(fetchedMessage.getDeliveredAt()));
		dtReadAt.setValue(toLocalDate(fetchedMessage.getReadAt()));
		txtMediaUrl.setText(fetchedMessage.getMediaUrl());
		ReplyToMessage replyToMessage = ReplyToMessageService.getService().get(fetchedMessage.getReplyToMessageId());
		cmbReplyToMessage.getSelectionModel().select(replyToMessage);
		if(action.equals("Create") || action.equals("Edit")){
			cmbReplyToMessage.setVisible(true);
			txtReplyToMessageName.setVisible(false);
			cmbReplyToMessage.getSelectionModel().select(replyToMessage);
		}
		else{
			cmbReplyToMessage.setVisible(false);
			txtReplyToMessageName.setVisible(true);
			txtReplyToMessageName.setText(replyToMessage.getName());
		}
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbMessage.getSelectionModel().clearSelection();
		txtMessageName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbMessage.setVisible(true);
			txtMessageName.setVisible(false);
		}
		else{
			cmbMessage.setVisible(false);
			txtMessageName.setVisible(true);
		}
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
		cmbSender.getSelectionModel().clearSelection();
		txtSenderName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbSender.setVisible(true);
			txtSenderName.setVisible(false);
		}
		else{
			cmbSender.setVisible(false);
			txtSenderName.setVisible(true);
		}
		//txtContent.setText("");
		//txtMessageType.setText("");
		//dtSentAt.setText("");
		//dtDeliveredAt.setText("");
		//dtReadAt.setText("");
		//txtMediaUrl.setText("");
		cmbReplyToMessage.getSelectionModel().clearSelection();
		txtReplyToMessageName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbReplyToMessage.setVisible(true);
			txtReplyToMessageName.setVisible(false);
		}
		else{
			cmbReplyToMessage.setVisible(false);
			txtReplyToMessageName.setVisible(true);
		}
	}

	protected void enableFields(boolean enable){
		cmbMessage.editableProperty().set(enable);
		txtMessageName.editableProperty().set(enable);
		cmbConversation.editableProperty().set(enable);
		txtConversationName.editableProperty().set(enable);
		cmbSender.editableProperty().set(enable);
		txtSenderName.editableProperty().set(enable);
		txtContent.editableProperty().set(enable);
		txtMessageType.editableProperty().set(enable);
		dtSentAt.editableProperty().set(enable);
		dtDeliveredAt.editableProperty().set(enable);
		dtReadAt.editableProperty().set(enable);
		txtMediaUrl.editableProperty().set(enable);
		cmbReplyToMessage.editableProperty().set(enable);
		txtReplyToMessageName.editableProperty().set(enable);
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

