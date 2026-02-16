package com.gabriel.messms;
import com.gabriel.messms.model.Message;
import com.gabriel.messms.service.MessageService;
import com.gabriel.messms.model.Message;
import com.gabriel.messms.service.MessageService;
import com.gabriel.messms.model.Conversation;
import com.gabriel.messms.service.ConversationService;
import com.gabriel.messms.model.Sender;
import com.gabriel.messms.service.SenderService;
import com.gabriel.messms.model.ReplyToMessage;
import com.gabriel.messms.service.ReplyToMessageService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import java.net.URL;
import java.util.ResourceBundle;
import lombok.Setter;

public class CreateMessageController extends GenericMessageController {
	public ImageView imgMessage;
	@Override
	public void init() {
		clearFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			Message message = toObject(false);
			Message newMessage = MessageService.getService().create(message);
			manageMessageController.refresh();
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage Message");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating message", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
