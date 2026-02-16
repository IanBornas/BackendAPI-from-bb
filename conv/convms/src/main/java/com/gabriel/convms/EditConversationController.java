package com.gabriel.convms;
import com.gabriel.convms.model.Conversation;
import com.gabriel.convms.service.ConversationService;
import com.gabriel.convms.model.Conversation;
import com.gabriel.convms.service.ConversationService;
import com.gabriel.convms.model.Creator;
import com.gabriel.convms.service.CreatorService;
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

public class EditConversationController extends GenericConversationController {
	public ImageView imgConversation;
	@Override
	public void init() {
		setFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			Conversation conversation = toObject(true);
			Conversation newConversation = ConversationService.getService().update(conversation);
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage Conversation");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating conversation", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
