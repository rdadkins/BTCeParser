package co.bitsquared.btceparser.ui.view.popup;

import co.bitsquared.btceparser.ui.callback.BaseUICallback;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public abstract class BasePopup extends Application {

    private Stage myStage;

    @Override
    public final void start(Stage primaryStage) {
        myStage = primaryStage;
        myStage.show();
    }

    public final void okButtonClicked(ActionEvent actionEvent) {
        dismiss();
        handleOK(actionEvent);
    }

    public final void cancelButtonClicked(ActionEvent actionEvent) {
        dismiss();
        handleCancel(actionEvent);
    }

    protected final void dismiss() {
        myStage.hide();
    }

    protected abstract void handleOK(ActionEvent actionEvent);

    protected abstract void handleCancel(ActionEvent actionEvent);

    public abstract void registerListener(BaseUICallback callback);

}
