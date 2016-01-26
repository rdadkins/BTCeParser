package co.bitsquared.btceparser.ui.callback;

public interface PasswordCallback extends BaseUICallback {

    void onPasswordSupplied(String password);

    void onPasswordDenied();

}
