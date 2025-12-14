package org.example.myproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class dashboardController implements Initializable {

    @FXML private Button addStudent_btn;
    @FXML private AnchorPane addStudent_form;

    @FXML private Button addStudents_addBtn;
    @FXML private DatePicker addStudents_birth;
    @FXML private Button addStudents_changeBtn;
    @FXML private Button addStudents_clearBtn;

    @FXML private TableColumn<studentData, Date> addStudents_col_birth;
    @FXML private TableColumn<studentData, String> addStudents_col_course;
    @FXML private TableColumn<studentData, String> addStudents_col_gender;
    @FXML private TableColumn<studentData, String> addStudents_col_group;
    @FXML private TableColumn<studentData, String> addStudents_col_name;
    @FXML private TableColumn<studentData, String> addStudents_col_surname;
    @FXML private TableColumn<studentData, String> addStudents_col_zk;

    @FXML private ComboBox<String> addStudents_course;
    @FXML private Button addStudents_deleteBtn;
    @FXML private ComboBox<String> addStudents_gender;
    @FXML private ComboBox<String> addStudents_group;
    @FXML private ImageView addStudents_imageView;

    @FXML private TextField addStudents_name;
    @FXML private TextField addStudents_search;
    @FXML private TextField addStudents_surname;
    @FXML private TableView<studentData> addStudents_tableView;
    @FXML private TextField addStudents_zk;

    @FXML private Button studentsGroup_addBtn;
    @FXML private Button studentsGroup_changeBtn;
    @FXML private Button studentsGroup_clearBtn;
    @FXML private TableColumn<groupData, String> studentsGroup_col_faculty;
    @FXML private TableColumn<groupData, String> studentsGroup_col_group;
    @FXML private TableColumn<groupData, String> studentsGroup_col_program;
    @FXML private Button studentsGroup_deleteBtn;
    @FXML private TextField studentsGroup_faculty;
    @FXML private TextField studentsGroup_group;
    @FXML private TextField studentsGroup_program;
    @FXML private TableView<groupData> studentsGroup_tableView;

    @FXML private Button studentsGroup_btn;
    @FXML private AnchorPane studentsGroup_form;

    @FXML private Button close;
    @FXML private Button home_btn;

    @FXML private Label home_enrolled;
    @FXML private BarChart<?, ?> home_enrolledChart;
    @FXML private Label home_enrolledFemale;
    @FXML private Label home_enrolledMale;
    @FXML private AreaChart<?, ?> home_femaleChart;
    @FXML private AnchorPane home_form;
    @FXML private LineChart<?, ?> home_maleChart;

    @FXML private Button logout;
    @FXML private AnchorPane main_form;
    @FXML private Button minimize;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;

    @FXML
    public void setClose() {
        Platform.exit();
    }

    @FXML
    public void setMinimize() {
        Stage stage = (Stage) this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    private void lockStageSize(Stage stage) {
        if (stage == null) return;

        Runnable lock = () -> {
            try {
                try { stage.sizeToScene(); } catch (Exception ignore) {}

                double w = stage.getWidth();
                double h = stage.getHeight();

                if (w <= 0 || h <= 0) {
                    Scene s = stage.getScene();
                    if (s != null) {
                        Parent r = s.getRoot();
                        if (r != null) {
                            if (w <= 0) w = r.prefWidth(-1);
                            if (h <= 0) h = r.prefHeight(-1);
                        }
                    }
                }

                if (w > 0 && h > 0) {
                    stage.setResizable(false);
                    stage.setMinWidth(w);
                    stage.setMaxWidth(w);
                    stage.setMinHeight(h);
                    stage.setMaxHeight(h);
                } else {
                    stage.setResizable(false);
                }
            } catch (Exception e) {
                try { stage.setResizable(false); } catch (Exception ignore) {}
            }
        };

        if (stage.isShowing()) {
            lock.run();
        } else {
            stage.setOnShown(evt -> lock.run());
        }
    }

    @FXML
    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження виходу");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете вийти?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                Stage currentStage = (Stage) this.logout.getScene().getWindow();
                currentStage.hide();

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/org/example/myproject/hello-view.fxml"));
                Parent root = (Parent) loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Логін");
                lockStageSize(stage);
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
            studentsGroup_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudent_btn.setStyle("-fx-background-color:transparent");
            studentsGroup_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addStudent_btn) {
            home_form.setVisible(false);
            addStudent_form.setVisible(true);
            studentsGroup_form.setVisible(false);

            addStudent_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            studentsGroup_btn.setStyle("-fx-background-color:transparent");

            addStudentsShowListData();
            addStudentsGroupList();
            addStudentsCourseList();
            addStudentsGenderList();

        } else if (event.getSource() == studentsGroup_btn) {
            home_form.setVisible(false);
            addStudent_form.setVisible(false);
            studentsGroup_form.setVisible(true);

            studentsGroup_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudent_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

            studentsGroupShowListData();
        }
    }

    @FXML
    public void addStudentsAdd() {
        String insertData = "INSERT INTO student "
                + "(zk,\"group\",course,name,surname,gender,birth,image,date) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addStudents_zk.getText().isEmpty()
                    || addStudents_group.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || addStudents_name.getText().isEmpty()
                    || addStudents_surname.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || getData.path == null || getData.path.isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            int zkValue;
            try {
                zkValue = Integer.parseInt(addStudents_zk.getText());
            } catch (NumberFormatException nfe) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Номер залікової книжки має бути числом.");
                alert.showAndWait();
                return;
            }

            String checkData = "SELECT zk FROM student WHERE zk = ?";
            prepare = connect.prepareStatement(checkData);
            prepare.setInt(1, zkValue);
            result = prepare.executeQuery();

            if (result.next()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Студент №" + zkValue + " вже існує!");
                alert.showAndWait();
                return;
            }

            prepare = connect.prepareStatement(insertData);
            prepare.setInt(1, zkValue);
            prepare.setString(2, (String) addStudents_group.getSelectionModel().getSelectedItem());
            prepare.setInt(3, Integer.parseInt((String) addStudents_course.getSelectionModel().getSelectedItem()));
            prepare.setString(4, addStudents_name.getText());
            prepare.setString(5, addStudents_surname.getText());
            prepare.setString(6, (String) addStudents_gender.getSelectionModel().getSelectedItem());
            prepare.setDate(7, java.sql.Date.valueOf(addStudents_birth.getValue()));

            String uri = getData.path;
            uri = uri.replace("\\", "\\\\");
            prepare.setString(8, uri);

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            prepare.setDate(9, sqlDate);

            prepare.executeUpdate();

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно додано!");
            info.showAndWait();

            addStudentsShowListData();
            addStudentsClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudentsUpdate() {

        String uri = getData.path != null ? getData.path : "";
        uri = uri.replace("\\", "\\\\");

        String updateData = "UPDATE student SET \"group\" = ?, course = ?, name = ?, surname = ?, gender = ?, birth = ?, image = ? WHERE zk = ?";


        connect = database.connectDb();

        try {
            Alert alert;

            if (addStudents_zk.getText().isEmpty()
                    || addStudents_group.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || addStudents_name.getText().isEmpty()
                    || addStudents_surname.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || getData.path == null || getData.path.isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            int zkValue;
            try {
                zkValue = Integer.parseInt(addStudents_zk.getText().trim());
            } catch (NumberFormatException nfe) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Номер залікової книжки має бути числом.");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете оновити дані студента №" + zkValue + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!(option.isPresent() && option.get() == ButtonType.OK)) return;

            prepare = connect.prepareStatement(updateData);
            prepare.setString(1, (String) addStudents_group.getSelectionModel().getSelectedItem());
            prepare.setInt(2, Integer.parseInt((String) addStudents_course.getSelectionModel().getSelectedItem()));
            prepare.setString(3, addStudents_name.getText());
            prepare.setString(4, addStudents_surname.getText());
            prepare.setString(5, (String) addStudents_gender.getSelectionModel().getSelectedItem());
            prepare.setDate(6, java.sql.Date.valueOf(addStudents_birth.getValue()));
            prepare.setString(7, uri);
            prepare.setInt(8, zkValue);

            prepare.executeUpdate();

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно оновлено!");
            info.showAndWait();

            addStudentsShowListData();
            addStudentsClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudentsDelete() {
        String deleteData = "DELETE FROM student WHERE zk = ?";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addStudents_zk.getText().isEmpty()
                    || addStudents_group.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || addStudents_name.getText().isEmpty()
                    || addStudents_surname.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || getData.path == null || getData.path.isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            int zkValue;
            try {
                zkValue = Integer.parseInt(addStudents_zk.getText().trim());
            } catch (NumberFormatException nfe) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Номер залікової книжки має бути числом.");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете видалити студента №" + zkValue + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!(option.isPresent() && option.get() == ButtonType.OK)) return;

            prepare = connect.prepareStatement(deleteData);
            prepare.setInt(1, zkValue);
            prepare.executeUpdate();

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно видалено!");
            info.showAndWait();

            addStudentsShowListData();
            addStudentsClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudentsClear() {
        addStudents_zk.clear();
        addStudents_group.getSelectionModel().clearSelection();
        addStudents_course.getSelectionModel().clearSelection();
        addStudents_name.clear();
        addStudents_surname.clear();
        addStudents_gender.getSelectionModel().clearSelection();
        addStudents_birth.setValue(null);
        addStudents_imageView.setImage(null);

        getData.path = "";
    }

    private String[] courseList = {"1", "2", "3", "4"};

    @FXML
    public void addStudentsCourseList() {

        List<String> courseL = new ArrayList<>();

        for (String data : courseList) {
            courseL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(courseL);
        addStudents_course.setItems(ObList);

    }

    @FXML
    public void addStudentsGroupList() {

        String listGroup = "SELECT * FROM \"group\"";

        connect = database.connectDb();

        try {

            ObservableList listG = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(listGroup);
            result = prepare.executeQuery();

            while (result.next()) {
                listG.add(result.getString("group"));
            }
            addStudents_group.setItems(listG);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String[] genderList = {"Чоловік", "Жінка"};

    public void addStudentsGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : genderList) {
            genderL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(genderL);
        addStudents_gender.setItems(ObList);
    }

    /* ----------------------------- Student list ----------------------------- */

    @FXML
    public ObservableList<studentData> addStudentsListData() {
        ObservableList<studentData> listStudents = FXCollections.observableArrayList();
        String sql = "SELECT * FROM student";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentData studentD = new studentData(
                        result.getInt("zk"),
                        result.getString("group"),
                        result.getInt("course"),
                        result.getString("name"),
                        result.getString("surname"),
                        result.getString("gender"),
                        result.getDate("birth"),
                        result.getString("image")
                );
                listStudents.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }

    private ObservableList<studentData> addStudentsListD;

    @FXML
    public void addStudentsShowListData() {
        addStudentsListD = addStudentsListData();

        addStudents_col_zk.setCellValueFactory(new PropertyValueFactory<>("zk"));
        addStudents_col_group.setCellValueFactory(new PropertyValueFactory<>("group"));
        addStudents_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        addStudents_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addStudents_col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("birth"));

        addStudents_tableView.setItems(addStudentsListD);
    }

    @FXML
    public void addStudentsSelect() {
        studentData studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();
        if (studentD == null || (num - 1) < -1) return;

        addStudents_zk.setText(String.valueOf(studentD.getZk()));
        addStudents_name.setText(studentD.getName());
        addStudents_surname.setText(studentD.getSurname());
        addStudents_birth.setValue(LocalDate.parse(String.valueOf(studentD.getBirth())));

        String uri = "file:" + studentD.getImage();
        image = new Image(uri, 117, 158, false, true);
        addStudents_imageView.setImage(image);

        getData.path = studentD.getImage();
    }

    /* ----------------------------- Groups ----------------------------- */

    @FXML
    public void studentsGroupAdd() {
        String insertData = "INSERT INTO \"group\" (\"group\",program,faculty) VALUES(?,?,?)";
        connect = database.connectDb();

        try {
            Alert alert;
            if (studentsGroup_group.getText().isEmpty()
                    || studentsGroup_program.getText().isEmpty()
                    || studentsGroup_faculty.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            String checkData = "SELECT \"group\" FROM \"group\" WHERE \"group\" = '" + studentsGroup_group.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Група: " + studentsGroup_group.getText() + " вже існує!");
                alert.showAndWait();
                return;
            }

            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, studentsGroup_group.getText());
            prepare.setString(2, studentsGroup_program.getText());
            prepare.setString(3, studentsGroup_faculty.getText());
            prepare.executeUpdate();

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно додано!");
            info.showAndWait();

            studentsGroupShowListData();
            studentsGroupClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void studentsGroupUpdate() {
        String updateData = "UPDATE \"group\" SET program = '" + studentsGroup_program.getText() + "', faculty = '" + studentsGroup_faculty.getText() + "' WHERE \"group\" = '" + studentsGroup_group.getText() + "'";
        connect = database.connectDb();

        try {
            Alert alert;
            if (studentsGroup_group.getText().isEmpty()
                    || studentsGroup_program.getText().isEmpty()
                    || studentsGroup_faculty.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете оновити групу: " + studentsGroup_group.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!(option.isPresent() && option.get() == ButtonType.OK)) return;

            statement = connect.createStatement();
            statement.executeUpdate(updateData);

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно оновлено!");
            info.showAndWait();

            studentsGroupShowListData();
            studentsGroupClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void studentsGroupDelete() {
        String deleteData = "DELETE FROM \"group\" WHERE \"group\" = '" + studentsGroup_group.getText() + "'";
        connect = database.connectDb();

        try {
            Alert alert;
            if (studentsGroup_group.getText().isEmpty()
                    || studentsGroup_program.getText().isEmpty()
                    || studentsGroup_faculty.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете видалити групу: " + studentsGroup_group.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!(option.isPresent() && option.get() == ButtonType.OK)) return;

            statement = connect.createStatement();
            statement.executeUpdate(deleteData);

            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Повідомлення");
            info.setHeaderText(null);
            info.setContentText("Успішно видалено!");
            info.showAndWait();

            studentsGroupShowListData();
            studentsGroupClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void studentsGroupClear() {
        studentsGroup_group.clear();
        studentsGroup_program.clear();
        studentsGroup_faculty.clear();
    }

    @FXML
    public ObservableList<groupData> studentsGroupListData() {
        ObservableList<groupData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM \"group\"";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                groupData groupD = new groupData(result.getString("group"), result.getString("program"), result.getString("faculty"));
                listData.add(groupD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<groupData> studentsGroupList;

    @FXML
    public void studentsGroupShowListData() {
        studentsGroupList = studentsGroupListData();

        studentsGroup_col_group.setCellValueFactory(new PropertyValueFactory<>("group"));
        studentsGroup_col_program.setCellValueFactory(new PropertyValueFactory<>("program"));
        studentsGroup_col_faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        studentsGroup_tableView.setItems(studentsGroupList);
    }

    @FXML
    public void studentsGroupSelect() {
        groupData courseD = studentsGroup_tableView.getSelectionModel().getSelectedItem();
        int num = studentsGroup_tableView.getSelectionModel().getSelectedIndex();
        if (courseD == null || (num - 1) < -1) return;

        studentsGroup_group.setText(courseD.getGroup());
        studentsGroup_program.setText(courseD.getProgram());
        studentsGroup_faculty.setText(courseD.getFaculty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addStudentsShowListData();
        addStudentsGroupList();
        addStudentsCourseList();
        addStudentsGenderList();
        studentsGroupShowListData();

        Platform.runLater(() -> {
            try {
                Stage primary = (Stage) this.main_form.getScene().getWindow();
                if (primary != null) lockStageSize(primary);
            } catch (Exception ignore) {}
        });
    }
}


