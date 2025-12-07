package org.example.myproject;

import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class dashboardController {
    @FXML
    private Button addStudent_btn;

    @FXML
    private AnchorPane addStudent_form;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private Button addStudents_changeBtn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private TableColumn<?, ?> addStudents_col_birth;

    @FXML
    private TableColumn<?, ?> addStudents_col_course;

    @FXML
    private TableColumn<?, ?> addStudents_col_gender;

    @FXML
    private TableColumn<?, ?> addStudents_col_group;

    @FXML
    private TableColumn<?, ?> addStudents_col_name;

    @FXML
    private TableColumn<?, ?> addStudents_col_surname;

    @FXML
    private ComboBox<?> addStudents_course;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private ComboBox<?> addStudents_gender;

    @FXML
    private TextField addStudents_group;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private TextField addStudents_name;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TextField addStudents_surname;

    @FXML
    private TableView<?> addStudents_tableView;

    @FXML
    private Button availableCourse_addBtn;

    @FXML
    private Button availableCourse_changeBtn;

    @FXML
    private Button availableCourse_clearBtn;

    @FXML
    private TableColumn<?, ?> availableCourse_col_faculty;

    @FXML
    private TableColumn<?, ?> availableCourse_col_group;

    @FXML
    private TableColumn<?, ?> availableCourse_col_program;

    @FXML
    private Button availableCourse_deleteBtn;

    @FXML
    private TextField availableCourse_faculty;

    @FXML
    private TextField availableCourse_group;

    @FXML
    private TextField availableCourse_program;

    @FXML
    private TableView<?> availableCourse_tableView;

    @FXML
    private Button availableCourses_btn;

    @FXML
    private AnchorPane availableCourses_form;

    @FXML
    private Button close;

    @FXML

    private Button home_btn;

    @FXML
    private Label home_enrolled;

    @FXML
    private BarChart<?, ?> home_enrolledChart;

    @FXML
    private Label home_enrolledFemale;

    @FXML
    private Label home_enrolledMale;

    @FXML
    private AreaChart<?, ?> home_femaleChart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private LineChart<?, ?> home_maleChart;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button studentGrade_addBtn;

    @FXML
    private Button studentGrade_btn;

    @FXML
    private Button studentGrade_changeBtn;

    @FXML
    private Button studentGrade_clearBtn;

    @FXML
    private TableColumn<?, ?> studentGrade_col_studentGroup;

    @FXML
    private TableColumn<?, ?> studentGrade_col_studentGroup1;

    @FXML
    private TableColumn<?, ?> studentGrade_col_studentName;

    @FXML
    private TableColumn<?, ?> studentGrade_col_studentSurname;

    @FXML
    private TableColumn<?, ?> studentGrade_col_subject;

    @FXML
    private Button studentGrade_deleteBtn;

    @FXML
    private AnchorPane studentGrade_form;

    @FXML
    private ComboBox<?> studentGrade_grade;

    @FXML
    private TextField studentGrade_search;

    @FXML
    private TextField studentGrade_studentGroup;

    @FXML
    private TextField studentGrade_studentName;

    @FXML
    private TextField studentGrade_studentSurname;

    @FXML
    private TextField studentGrade_subject;

    @FXML
    private TableView<?> studentGrade_tableView;

    @FXML
    private Label username;

    @FXML
    public void setClose() {
        Platform.exit();
    }

    @FXML
    public void setMinimize() {
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText((String)null);
            alert.setContentText("Ви впевнені, що хочете вийти?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                Stage currentStage = (Stage)this.logout.getScene().getWindow();
                currentStage.hide();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/org/example/myproject/hello-view.fxml"));
                Parent root = (Parent)loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Логін");
                stage.show();
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

    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addStudent_form.setVisible(false);
            availableCourses_form.setVisible(false);
            studentGrade_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudent_btn.setStyle("-fx-background-color:transparent");
            availableCourses_btn.setStyle("-fx-background-color:transparent");
            studentGrade_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addStudent_btn) {
            home_form.setVisible(false);
            addStudent_form.setVisible(true);
            availableCourses_form.setVisible(false);
            studentGrade_form.setVisible(false);

            addStudent_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourses_btn.setStyle("-fx-background-color:transparent");
            studentGrade_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == availableCourses_btn) {
            home_form.setVisible(false);
            addStudent_form.setVisible(false);
            availableCourses_form.setVisible(true);
            studentGrade_form.setVisible(false);

            availableCourses_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudent_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            studentGrade_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == studentGrade_btn) {
            home_form.setVisible(false);
            addStudent_form.setVisible(false);
            availableCourses_form.setVisible(false);
            studentGrade_form.setVisible(true);

            studentGrade_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudent_btn.setStyle("-fx-background-color:transparent");
            availableCourses_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
        }
    }
}
