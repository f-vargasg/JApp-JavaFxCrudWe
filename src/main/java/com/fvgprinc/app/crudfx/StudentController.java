/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.fvgprinc.app.crudfx;

import com.fvgprinc.app.crudfx.be.StudentBe;
import com.fvgprinc.app.crudfx.bl.StudentBl;
import com.fvgprinc.tools.common.string.MyCommonString;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author garfi
 */
public class StudentController implements Initializable {

    private static Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        fieldSearch.textProperty().addListener((ObservableList, oldValue, newValue) -> {
            filterData(newValue);
        });
    }

    @FXML
    public TextField fieldFirstName;

    @FXML
    public TextField fieldMiddleName;

    @FXML
    public TextField fieldLastName;

    @FXML
    public TextField fieldSearch;

    @FXML
    public Button btnNew;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnDelete;

    @FXML
    public TableView<StudentBe> tableView;

    @FXML
    public TableColumn<StudentBe, Integer> colId;

    @FXML
    public TableColumn<StudentBe, String> colFirstName;

    @FXML
    public TableColumn<StudentBe, String> colMiddleName;

    @FXML
    public TableColumn<StudentBe, String> colLastName;

    private StudentBe studentBe;

    @FXML
    private void addStudent() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add confirmation");
        dialog.setHeaderText("Are you sure you want to save?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(getStage());
        Label label = new Label("Name: " + fieldFirstName.getText() + "  " + fieldLastName.getText());
        dialog.getDialogPane().setContent(label);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            StudentBe studentBe = new StudentBe(fieldFirstName.getText(), fieldMiddleName.getText(),
                    fieldLastName.getText());
            StudentBl studentBl = new StudentBl();
            studentBl.add(studentBe);
            showStudents();
        }

    }

    @FXML
    private void showStudents() {
        StudentBl studentBl = new StudentBl();

        ObservableList<StudentBe> list = studentBl.getList();
        colId.setCellValueFactory(new PropertyValueFactory<StudentBe, Integer>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<StudentBe, String>("firstName"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<StudentBe, String>("middleName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<StudentBe, String>("lastName"));
        tableView.setItems(list);
    }

    @FXML
    public void mouseClicked(MouseEvent e) {
        try {
            StudentBe studentBe1 = tableView.getSelectionModel().getSelectedItem();
            studentBe1 = new StudentBe(studentBe1.getId(), studentBe1.getFirstName(),
                    studentBe1.getMiddleName(), studentBe1.getLastName());
            this.studentBe = studentBe1;
            fieldFirstName.setText(studentBe1.getFirstName());
            fieldMiddleName.setText(studentBe1.getMiddleName());
            fieldLastName.setText(studentBe1.getLastName());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateStudent() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update confirmation");
            dialog.setHeaderText("Are you sure you want to update?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(getStage());
            Label label = new Label("Name: " + fieldFirstName.getText() + "  " + fieldLastName.getText());
            dialog.getDialogPane().setContent(label);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                StudentBl studentBl = new StudentBl();
                StudentBe studentBe1 = new StudentBe(this.studentBe.getId(), fieldFirstName.getText(),
                        fieldMiddleName.getText(), fieldLastName.getText());
                studentBl.update(studentBe1);
                showStudents();
                clearFields();
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Delete confirmation");
            dialog.setHeaderText("Are you sure you want to delete?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(getStage());
            Label label = new Label("Name: " + fieldFirstName.getText() + "  " + fieldLastName.getText());
            dialog.getDialogPane().setContent(label);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                StudentBl studentBl = new StudentBl();
                StudentBe studentBe1 = new StudentBe(this.studentBe.getId(), fieldFirstName.getText(),
                        fieldMiddleName.getText(), fieldLastName.getText());
                studentBl.delete(studentBe);
                showStudents();
                clearFields();
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        fieldFirstName.setText(MyCommonString.EMPTYSTR);
        fieldMiddleName.setText(MyCommonString.EMPTYSTR);
        fieldLastName.setText(MyCommonString.EMPTYSTR);
    }

    @FXML
    private void clickNew() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();
        btnSave.setDisable(false);
    }

    private void filterData(String searchName) {
        ObservableList<StudentBe> lstDataFiltered = FXCollections.observableArrayList();
        StudentBl studentBl = new StudentBl();
        ObservableList<StudentBe> list = studentBl.getList();
        for (StudentBe studentBe1 : list) {
            if (studentBe1.getFirstName().toLowerCase().contains(searchName.toLowerCase())
                    || studentBe1.getMiddleName().toLowerCase().contains(searchName.toLowerCase())
                    || studentBe1.getLastName().toLowerCase().contains(searchName.toLowerCase())) {
                lstDataFiltered.add(studentBe1);
            }
        }
        tableView.setItems(lstDataFiltered);

    }

    /**
     * @return the stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * @param aStage the stage to set
     */
    public static void setStage(Stage aStage) {
        stage = aStage;
    }
}
