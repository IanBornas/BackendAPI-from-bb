package com.gabriel.callms;
import com.gabriel.callms.model.CallSignal;
import com.gabriel.callms.model.Call;
import com.gabriel.callms.service.CallService;
import com.gabriel.callms.model.Caller;
import com.gabriel.callms.service.CallerService;
import com.gabriel.callms.model.Receiver;
import com.gabriel.callms.service.ReceiverService;
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

public class GenericCallSignalController implements Initializable{
	@Setter
	CreateCallSignalController createCallSignalController;

	@Setter
	DeleteCallSignalController deleteCallSignalController ;

	@Setter
	EditCallSignalController editCallSignalController;

	@Setter
	ManageCallSignalController manageCallSignalController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<CallSignal> lvCallSignals;

	@Setter
	public static CallSignal selectedItem;
	public TextField txtId;
	public ComboBox<Call> cmbCall;
	public ComboBox<Caller> cmbCaller;
	public ComboBox<Receiver> cmbReceiver;
	public TextField txtCallType;
	public TextField txtSignalStatus;
	public DatePicker dtTimestamp;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Call[] calls =  (Call[]) CallService.getService().getAll();
		cmbCall.getItems().addAll(calls);
		StringConverter<Call> callConverter = new StringConverter<Call>() {
			@Override
			public String toString(Call call) {
			if(call==null)
				return "";
			else
				return call.toString();
			}
			@Override
			public Call fromString(String s) {
				if(!s.isEmpty()){
					for (Call call : calls) {
						if (s.equals(call.getName())){
							return call;
						}
					}
				}
				return null;
			}
		};
		cmbCall.setConverter(callConverter);
		Caller[] callers =  (Caller[]) CallerService.getService().getAll();
		cmbCaller.getItems().addAll(callers);
		StringConverter<Caller> callerConverter = new StringConverter<Caller>() {
			@Override
			public String toString(Caller caller) {
			if(caller==null)
				return "";
			else
				return caller.toString();
			}
			@Override
			public Caller fromString(String s) {
				if(!s.isEmpty()){
					for (Caller caller : callers) {
						if (s.equals(caller.getName())){
							return caller;
						}
					}
				}
				return null;
			}
		};
		cmbCaller.setConverter(callerConverter);
		Receiver[] receivers =  (Receiver[]) ReceiverService.getService().getAll();
		cmbReceiver.getItems().addAll(receivers);
		StringConverter<Receiver> receiverConverter = new StringConverter<Receiver>() {
			@Override
			public String toString(Receiver receiver) {
			if(receiver==null)
				return "";
			else
				return receiver.toString();
			}
			@Override
			public Receiver fromString(String s) {
				if(!s.isEmpty()){
					for (Receiver receiver : receivers) {
						if (s.equals(receiver.getName())){
							return receiver;
						}
					}
				}
				return null;
			}
		};
		cmbReceiver.setConverter(receiverConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected CallSignal toObject(boolean isEdit){
		CallSignal callSignal= new CallSignal();
		try {
			if(isEdit) {
				callSignal.setId(Integer.parseInt(txtId.getText()));
			}
			Call call = cmbCall.getSelectionModel().getSelectedItem();
			callSignal.setCallId(call.getId());
			callSignal.setCallName(call.getName());
			Caller caller = cmbCaller.getSelectionModel().getSelectedItem();
			callSignal.setCallerId(caller.getId());
			callSignal.setCallerName(caller.getName());
			Receiver receiver = cmbReceiver.getSelectionModel().getSelectedItem();
			callSignal.setReceiverId(receiver.getId());
			callSignal.setReceiverName(receiver.getName());
			callSignal.setCallType(txtCallType.getText());
			callSignal.setSignalStatus(txtSignalStatus.getText());
			callSignal.setTimestamp(toDate(dtTimestamp.getValue()));
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return callSignal;
	}
	protected void setFields(String action){
		String formattedDate;
		CallSignal callSignal = GenericCallSignalController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(callSignal.getId()));
		Call call = CallService.getService().get(callSignal.getCallId());
		cmbCall.getSelectionModel().select(call);
		if(action.equals("Create") || action.equals("Edit")){
			cmbCall.setVisible(true);
			txtCallName.setVisible(false);
			cmbCall.getSelectionModel().select(call);
		}
		else{
			cmbCall.setVisible(false);
			txtCallName.setVisible(true);
			txtCallName.setText(call.getName());
		}
		Caller caller = CallerService.getService().get(callSignal.getCallerId());
		cmbCaller.getSelectionModel().select(caller);
		if(action.equals("Create") || action.equals("Edit")){
			cmbCaller.setVisible(true);
			txtCallerName.setVisible(false);
			cmbCaller.getSelectionModel().select(caller);
		}
		else{
			cmbCaller.setVisible(false);
			txtCallerName.setVisible(true);
			txtCallerName.setText(caller.getName());
		}
		Receiver receiver = ReceiverService.getService().get(callSignal.getReceiverId());
		cmbReceiver.getSelectionModel().select(receiver);
		if(action.equals("Create") || action.equals("Edit")){
			cmbReceiver.setVisible(true);
			txtReceiverName.setVisible(false);
			cmbReceiver.getSelectionModel().select(receiver);
		}
		else{
			cmbReceiver.setVisible(false);
			txtReceiverName.setVisible(true);
			txtReceiverName.setText(receiver.getName());
		}
		txtCallType.setText(callSignal.getCallType());
		txtSignalStatus.setText(callSignal.getSignalStatus());
		dtTimestamp.setValue(toLocalDate(callSignal.getTimestamp()));
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbCall.getSelectionModel().clearSelection();
		txtCallName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbCall.setVisible(true);
			txtCallName.setVisible(false);
		}
		else{
			cmbCall.setVisible(false);
			txtCallName.setVisible(true);
		}
		cmbCaller.getSelectionModel().clearSelection();
		txtCallerName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbCaller.setVisible(true);
			txtCallerName.setVisible(false);
		}
		else{
			cmbCaller.setVisible(false);
			txtCallerName.setVisible(true);
		}
		cmbReceiver.getSelectionModel().clearSelection();
		txtReceiverName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbReceiver.setVisible(true);
			txtReceiverName.setVisible(false);
		}
		else{
			cmbReceiver.setVisible(false);
			txtReceiverName.setVisible(true);
		}
		//txtCallType.setText("");
		//txtSignalStatus.setText("");
		//dtTimestamp.setText("");
	}

	protected void enableFields(boolean enable){
		cmbCall.editableProperty().set(enable);
		txtCallName.editableProperty().set(enable);
		cmbCaller.editableProperty().set(enable);
		txtCallerName.editableProperty().set(enable);
		cmbReceiver.editableProperty().set(enable);
		txtReceiverName.editableProperty().set(enable);
		txtCallType.editableProperty().set(enable);
		txtSignalStatus.editableProperty().set(enable);
		dtTimestamp.editableProperty().set(enable);
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

