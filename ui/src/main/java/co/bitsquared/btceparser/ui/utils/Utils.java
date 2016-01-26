package co.bitsquared.btceparser.ui.utils;

import co.bitsquared.btceparser.ui.callback.BaseUICallback;
import co.bitsquared.btceparser.ui.view.popup.BasePopup;
import co.bitsquared.btceparser.ui.view.popup.PasswordPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static void createPopup(InputStream inputStream, String title, BaseUICallback callback) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage popupStage = new Stage();
        popupStage.setScene(new Scene((Parent) loader.load(inputStream)));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);
        BasePopup popup = loader.getController();
        popup.registerListener(callback);
        popup.start(popupStage);
    }

}
