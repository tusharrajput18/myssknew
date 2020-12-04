package in.co.vsys.myssksamaj.views;

/**
 * @author Abhijeet.J
 */
public interface MVPView {
    void showLoading();

    void hideLoading();

    void showError(String message);
}