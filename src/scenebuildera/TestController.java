/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenebuildera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author PC
 */
public class TestController implements Initializable {

    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem about;

    @FXML
    private MenuBar menubar;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fullName;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;

    @FXML
    private RadioButton single;
    @FXML
    private RadioButton married;
    @FXML
    private RadioButton divorced;
    @FXML
    private ToggleGroup tg;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<String> availableSkills;
    @FXML
    private ListView<String> selectedSkills;

    @FXML
    private Button addSkill;
    @FXML
    private Button removeSkill;

    @FXML
    private Button uploadImage;
    @FXML
    private Button uploadCoverLetter;
    @FXML
    private Button save;

    @FXML
    private ImageView imgView;

    @FXML
    private TextArea textArea;

    @FXML
    private Slider slider;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private RadioMenuItem arialMenu;
    @FXML
    private RadioMenuItem timesMenu;
    @FXML
    private RadioMenuItem sansMenu;
    @FXML
    private ToggleGroup tg2;

    @FXML
    private RadioMenuItem smallMenu;
    @FXML
    private RadioMenuItem mediumMenu;
    @FXML
    private RadioMenuItem largeMenu;
    @FXML
    private ToggleGroup tg3;

    File selectedImageFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> skills = FXCollections.observableArrayList();
        skills.add("Java");
        skills.add("C++");
        skills.add("Python");
        skills.add("HTML");
        skills.add("CSS");
        skills.add("JavaScript");
        skills.add("SQL");
        skills.add("Problem Solving");
        skills.add("Communication");
        skills.add("Teamwork");

        availableSkills.setItems(skills);

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(LocalDate.of(2010, 1, 1))) {
                            setDisable(true);
                        }
                    }
                };
            }
        });

        slider.setMin(10);
        slider.setMax(30);
        slider.setValue(12);

        textArea.setStyle("-fx-font-size:12px;");

        colorPicker.setOnAction(e -> {
            rootPane.setStyle("-fx-background-color: " + toRgbString(colorPicker.getValue()) + ";");
        });
    }

    @FXML
    private void exitHandle(ActionEvent event) {
        ((Stage) menubar.getScene().getWindow()).close();
    }

    @FXML
    private void aboutHandle(ActionEvent event) {
        showAlert("information", "About", "Job Application System",
                "Application Name: Job Application System\nPurpose: Apply for jobs through desktop interface\nDeveloper: Aya Alharazin");
    }

    @FXML
    private void sliderHandle(MouseEvent event) {
        textArea.setStyle("-fx-font-size:" + (int) slider.getValue() + "px;");
    }

    @FXML
    private void uploadImageHandle(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File f = fc.showOpenDialog(uploadImage.getScene().getWindow());

        if (f != null) {
            selectedImageFile = f;
            try {
                Image img = new Image(new FileInputStream(f));
                imgView.setImage(img);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            showAlert("warning", "warning", "No Image Chosen",
                    "Make sure to select a valid image file");
        }
    }

    @FXML
    private void uploadCoverLetterHandle(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File f = fc.showOpenDialog(uploadCoverLetter.getScene().getWindow());

        if (f != null) {
            textArea.setText("");

            try (Scanner s = new Scanner(f)) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    textArea.appendText(line + "\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("warning", "warning", "No File Chosen",
                    "Make sure to select a valid text file");
        }
    }

    @FXML
    private void addSkillHandle(ActionEvent event) {
        String skill = availableSkills.getSelectionModel().getSelectedItem();

        if (skill != null) {
            selectedSkills.getItems().add(skill);
            availableSkills.getItems().remove(skill);
        } else {
            showAlert("warning", "warning", "No Skill Selected",
                    "Please select a skill from available skills");
        }
    }

    @FXML
    private void removeSkillHandle(ActionEvent event) {
        String skill = selectedSkills.getSelectionModel().getSelectedItem();

        if (skill != null) {
            availableSkills.getItems().add(skill);
            selectedSkills.getItems().remove(skill);
        } else {
            showAlert("warning", "warning", "No Skill Selected",
                    "Please select a skill from selected skills");
        }
    }

    @FXML
    private void saveHandle(ActionEvent event) throws IOException {
        if (validate()) {

            String maritalStatus = "";

            if (single.isSelected()) {
                maritalStatus = "Single";
            } else if (married.isSelected()) {
                maritalStatus = "Married";
            } else if (divorced.isSelected()) {
                maritalStatus = "Divorced";
            }

            Random r = new Random();
            int num = r.nextInt(10000);

            String fileName = fullName.getText().trim().replaceAll("\\s+", "_") + "_" + num + ".txt";

            try (PrintWriter pr = new PrintWriter(new FileWriter(fileName))) {

                pr.println("Full Name: " + fullName.getText());
                pr.println("Address: " + address.getText());
                pr.println("Phone Number: " + phoneNumber.getText());
                pr.println("Marital Status: " + maritalStatus);
                pr.println("Birthdate: " + datePicker.getValue());
                pr.println("Selected Skills: " + selectedSkills.getItems());
                pr.println("Image Path: " + selectedImageFile.getAbsolutePath());
                pr.println("Cover Letter:");
                pr.println(textArea.getText());
            }

            showAlert("information", "successful", "Data saved successfully",
                    "Applicant data saved successfully");

            clearData();

        } else {
            showAlert("warning", "warning", "Invalid Data",
                    "Please make sure all fields are filled correctly");
        }
    }

    @FXML
    private void fontFamilyHandle(ActionEvent event) {
        if (arialMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-family: Arial;");
        } else if (timesMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-family: 'Times New Roman';");
        } else if (sansMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-family: 'SansSerif';");
        }
    }

    @FXML
    private void fontSizeHandle(ActionEvent event) {
        if (smallMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-size: 12px;");
        } else if (mediumMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-size: 16px;");
        } else if (largeMenu.isSelected()) {
            rootPane.setStyle(rootPane.getStyle() + "-fx-font-size: 20px;");
        }
    }

    public boolean validate() {

        if (fullName.getText().trim().isEmpty()) {
            showAlert("warning", "warning", "Missing Full Name",
                    "Please enter full name");
            return false;
        }

        if (address.getText().trim().isEmpty()) {
            showAlert("warning", "warning", "Missing Address",
                    "Please enter address");
            return false;
        }

        if (phoneNumber.getText().trim().isEmpty()) {
            showAlert("warning", "warning", "Missing Phone Number",
                    "Please enter phone number");
            return false;
        }

        if (!phoneNumber.getText().matches("[0-9+\\- ]+")) {
            showAlert("warning", "warning", "Invalid Phone Number",
                    "Phone number should contain valid characters only");
            return false;
        }

        if (!single.isSelected() && !married.isSelected() && !divorced.isSelected()) {
            showAlert("warning", "warning", "Missing Marital Status",
                    "Please select marital status");
            return false;
        }

        if (datePicker.getValue() == null) {
            showAlert("warning", "warning", "Missing Birthdate",
                    "Please select birthdate");
            return false;
        }

        if (selectedSkills.getItems().isEmpty()) {
            showAlert("warning", "warning", "Missing Skills",
                    "Please select at least one skill");
            return false;
        }

        if (textArea.getText().trim().isEmpty()) {
            showAlert("warning", "warning", "Missing Cover Letter",
                    "Please enter or upload cover letter");
            return false;
        }

        if (selectedImageFile == null) {
            showAlert("warning", "warning", "Missing Image",
                    "Please upload image");
            return false;
        }

        return true;
    }

    public void clearData() {
        fullName.setText("");
        address.setText("");
        phoneNumber.setText("");
        tg.selectToggle(null);
        datePicker.setValue(null);
        textArea.setText("");
        imgView.setImage(null);
        selectedImageFile = null;

        availableSkills.getItems().clear();
        selectedSkills.getItems().clear();

        availableSkills.getItems().addAll(
                "Java", "C++", "Python", "HTML", "CSS",
                "JavaScript", "SQL", "Problem Solving", "Communication", "Teamwork"
        );
    }

    public void showAlert(String type, String title, String header, String content) {
        Alert alert = null;

        if (type.equals("information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else if (type.equals("warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String toRgbString(javafx.scene.paint.Color c) {
        return String.format("rgb(%d, %d, %d)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

}