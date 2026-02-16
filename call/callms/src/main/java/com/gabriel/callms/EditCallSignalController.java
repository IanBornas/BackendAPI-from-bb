package com.gabriel.callms;
import com.gabriel.callms.model.CallSignal;
import com.gabriel.callms.service.CallSignalService;
import com.gabriel.callms.model.Call;
import com.gabriel.callms.service.CallService;
import com.gabriel.callms.model.Caller;
import com.gabriel.callms.service.CallerService;
import com.gabriel.callms.model.Receiver;
import com.gabriel.callms.service.ReceiverService;
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

public class EditCallSignalController extends GenericCallSignalController {
	public ImageView imgCallSignal;
	@Override
	public void init() {
		setFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			CallSignal callSignal = toObject(true);
			CallSignal newCallSignal = CallSignalService.getService().update(callSignal);
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage CallSignal");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating callSignal", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
