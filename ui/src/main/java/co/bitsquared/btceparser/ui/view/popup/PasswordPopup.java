package co.bitsquared.btceparser.ui.view.popup;

import co.bitsquared.btceparser.ui.callback.BaseUICallback;
import co.bitsquared.btceparser.ui.callback.PasswordCallback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class PasswordPopup extends BasePopup {

    private PasswordCallback callback;
    @FXML private PasswordField passwordField;

    protected void handleOK(ActionEvent actionEvent) {
        if (callback != null) {
            callback.onPasswordSupplied(passwordField.getText());
        }
    }

    protected void handleCancel(ActionEvent actionEvent) {
        if (callback != null) {
            callback.onPasswordDenied();
        }
    }

    public void registerListener(BaseUICallback callback) {
        try {
            this.callback = (PasswordCallback) callback;
        } catch (ClassCastException e) {
            throw new RuntimeException("Implementing class must implement PasswordCallback");
        }
    }

}
