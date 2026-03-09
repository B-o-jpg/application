package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password");
            errorLabel.setStyle("-fx-text-fill: #c41e3a;");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Restaurant POS System");
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Error loading POS system");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}