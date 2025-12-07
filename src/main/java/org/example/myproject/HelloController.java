package org.example.myproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class HelloController {

    @FXML
    private FontIcon close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    public  void setClose() {
        Platform.exit();
    }

    @FXML
    public void loginAdmin() {
        try {
            String user = this.username.getText().trim();
            String pass = this.password.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText((String)null);
                alert.setContentText("Будь ласка заповніть поля логін та пароль");
                alert.showAndWait();
                return;
            }

            if ("admin".equals(user) && "1234".equals(pass)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Успіх");
                alert.setHeaderText((String)null);
                alert.setContentText("Вхід успішний!");
                alert.showAndWait();
                Stage currentStage = (Stage)this.loginBtn.getScene().getWindow();
                currentStage.hide();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("dashboard.fxml"));
                Parent root = (Parent)loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Система обліку студентів");
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText((String)null);
                alert.setContentText("Невірний логін або пароль");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(AlertType.ERROR);
            error.setTitle("Помилка");
            error.setHeaderText("Невідома помилка");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }

    }
}
