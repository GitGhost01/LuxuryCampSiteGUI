package com.example.luxurycampsitegui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;


public class Controller implements Initializable {

    @FXML
    private ComboBox<String> areaComboBox;

    @FXML
    private TextField areaDescriptionTextField;

    private final String[] areas = {"Hilltop", "Wild Meadow", "Woodland", "Lakeview"};

    @FXML
    private ComboBox<String> cleaningStatusComboBox;

    private final String[] cleaningStatus = {"Clean", "Requires Cleaning"};

    @FXML
    private TextField breakfastReqTextField;

    @FXML
    private TextField cleaningReqTextField;

    @FXML
    private TextField accommodationTypeTextField;

    @FXML
    private TextField accommodationNumberTextField;

    @FXML
    private TextField accommodatesTextField;

    @FXML
    private TextField pricePerNightTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField telephoneTextField;

    @FXML
    private TextField numberOfGuestsTextField;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private TextField numberOfNightsTextField;

    @FXML
    private CheckBox breakfastRequiredCheckBox;

    @FXML
    private Button checkInButton;

    @FXML
    private Button checkOutButton;

    @FXML
    private Button updateCleanStatusButton;

    @FXML
    private TableView<Table> table;

    @FXML
    private TableColumn<Table, Integer> numberCol;

    @FXML
    private TableColumn<Table, String> accommodationTypeCol;

    @FXML
    private TableColumn<Table, String> occupancyCol;

    @FXML
    private TableColumn<Table, String> availabilityCol;

    @FXML
    private TableColumn<Table, String> statusCol;

    @FXML
    private TableColumn<Table, Integer> guestsCol;

    @FXML
    private TableColumn<Table, String> breakfastCol;

    @FXML
    private TextField shepherdHut1TextField;

    @FXML
    private TextField shepherdHut2TextField;

    @FXML
    private TextField shepherdHut3TextField;

    @FXML
    private TextField yurt1TextField;

    @FXML
    private TextField yurt2TextField;

    @FXML
    private TextField yurt3TextField;

    @FXML
    private TextField yurt4TextField;

    @FXML
    private TextField geodesicDome1TextField;

    @FXML
    private TextField geodesicDome2TextField;

    @FXML
    private TextField geodesicDome3TextField;

    @FXML
    private TextField geodesicDome4TextField;

    @FXML
    private TextField cabin1TextField;

    @FXML
    private TextField cabin2TextField;

    @FXML
    private TextField cabin3TextField;

    @FXML
    void displaySelected(MouseEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Row Selected");
            alert.setHeaderText("Please select a row.");
            alert.showAndWait();
        }
        accommodationNumberTextField.setText(String.valueOf(selectedIndex + 1));
    }

    @FXML
    void checkedIn(ActionEvent event) {

        //Perform validation on first name
        if (!firstNameValidation()) {
            return; // Stop execution if validation fails
        }

        // Perform validation on last name
        if (!lastNameValidation()) {
            return; // Stop execution if validation fails
        }

        //Perform validation on telephone number
        if (!telephoneValidation()){
            return; // Stop execution if validation fails
        }

        //Perform validation on number of guests
        if (!numberOfGuestsValidation()){
            return; // Stop execution if validation fails
        }

        //Perform validation on check in date
        if (!checkInDatePickerValidation()){
            return; // Stop execution if validation fails
        }

        // Perform validation on number of nights
        if (!numberOfNightsValidation()) {
            return; // Stop execution if validation fails
        }

        // Perform validation on if accommodation is available or not
        if (!availabilityValidation()) {
            return; // Stop execution if validation fails
        }

        //changes occupancy, availability, guests and breakfast columns accordingly
        occupancyCol.getCellData(table.getSelectionModel().getSelectedItem().setOccupancy("Occupied"));
        availabilityCol.getCellData(table.getSelectionModel().getSelectedItem().setAvailability("Unavailable"));
        guestsCol.getCellData(table.getSelectionModel().getSelectedItem().setGuests(Integer.parseInt(numberOfGuestsTextField.getText())));

        //updates the "number of breakfast" text field to increase if it is ticked
        if (breakfastRequiredCheckBox.isSelected()) {
            breakfastCol.getCellData(table.getSelectionModel().getSelectedItem().setBreakfast("Yes"));
            int breakfastReq = Integer.parseInt(breakfastReqTextField.getText());
            breakfastReq++;
            breakfastReqTextField.setText(String.valueOf(breakfastReq));
        } else {
            breakfastCol.getCellData(table.getSelectionModel().getSelectedItem().setBreakfast("No"));
        }

        table.refresh();

        copyUserInputToOverview();

        firstNameTextField.clear();
        lastNameTextField.clear();
        telephoneTextField.clear();
        numberOfGuestsTextField.clear();
        checkInDatePicker.setValue(null);
        numberOfNightsTextField.clear();
        breakfastRequiredCheckBox.setSelected(false);

    }

    @FXML
    void checkedOut(ActionEvent event) {

        String currentOccupancy = occupancyCol.getCellData(table.getSelectionModel().getSelectedItem());
        String currentAvailability = availabilityCol.getCellData(table.getSelectionModel().getSelectedItem());
        String currentCleanStatus = statusCol.getCellData(table.getSelectionModel().getSelectedItem());

        //changes occupancy, guests, breakfast and status columns accordingly
        occupancyCol.getCellData(table.getSelectionModel().getSelectedItem().setOccupancy("Unoccupied"));
        guestsCol.getCellData(table.getSelectionModel().getSelectedItem().setGuests(0));

        //updates the "number of breakfast" text field to decrease if it was needed (i.e. if it was ticked initially)
        if (breakfastCol.getCellData(table.getSelectionModel().getSelectedItem()).equals("Yes")) {
            int breakfastReq = Integer.parseInt(breakfastReqTextField.getText());
            breakfastReq--;
            breakfastReqTextField.setText(String.valueOf(breakfastReq));
            breakfastCol.getCellData(table.getSelectionModel().getSelectedItem().setBreakfast("No"));
        }
        else {
            breakfastCol.getCellData(table.getSelectionModel().getSelectedItem().setBreakfast("No"));
        }

        int cleaningReq = Integer.parseInt(cleaningReqTextField.getText());

        //updates the "number require cleaning" text field to increase when check out button is clicked as accommodation is now unclean
        //and if the status is already set to require cleaning it will not increment the "number require cleaning" text field
        if (!currentAvailability.equals("Available") && !currentCleanStatus.equals("Requires Cleaning")){
            statusCol.getCellData(table.getSelectionModel().getSelectedItem().setStatus("Requires Cleaning"));
            cleaningReq++;
            cleaningReqTextField.setText(String.valueOf(cleaningReq));
        }

        table.refresh();

        if (!currentOccupancy.equals("Occupied")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Check Out");
            alert.setContentText("You cannot check out an empty/unoccupied accommodation");
            alert.showAndWait();
        }
        else {
            clearOverviewTextField();
        }

    }

    @FXML
    void updateCleaning(ActionEvent event) {

        String selectedOption = cleaningStatusComboBox.getSelectionModel().getSelectedItem();
        int cleaningReq = Integer.parseInt(cleaningReqTextField.getText());
        String currentCleanStatus = statusCol.getCellData(table.getSelectionModel().getSelectedItem());

        //if a cleaning status is not selected an alert box is shown
        if (selectedOption == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Choose a cleaning status");
            alert.setContentText("You cannot update the table without choosing a cleaning status");
            alert.showAndWait();
        }

        //updates the "number requires cleaning" text field to ensure it is updated and cannot increment or decrement needlessly
        else {
            statusCol.getCellData(table.getSelectionModel().getSelectedItem().setStatus(selectedOption));

            if (currentCleanStatus.equals("Clean") && selectedOption.equals("Clean")){

            }
            else if (currentCleanStatus.equals("Requires Cleaning") && selectedOption.equals("Requires Cleaning")){

            }
            else if (currentCleanStatus.equals("Clean") && selectedOption.equals("Requires Cleaning")) {
                cleaningReq++;
                cleaningReqTextField.setText(String.valueOf(cleaningReq));
            }
            else if (currentCleanStatus.equals("Requires Cleaning") && selectedOption.equals("Clean")) {
                cleaningReq--;
                cleaningReqTextField.setText(String.valueOf(cleaningReq));
                availabilityCol.getCellData(table.getSelectionModel().getSelectedItem().setAvailability("Available"));
            }
            table.refresh();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        accommodationTypeCol.setCellValueFactory(new PropertyValueFactory<>("accommodationType"));
        occupancyCol.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        guestsCol.setCellValueFactory(new PropertyValueFactory<>("guests"));
        breakfastCol.setCellValueFactory(new PropertyValueFactory<>("breakfast"));

        //will add the various options to both the area and cleaning status drop-downs
        areaComboBox.getItems().addAll(areas);
        cleaningStatusComboBox.getItems().addAll(cleaningStatus);

        //disabling the various text fields and other components so the user cannot change them outside the boundaries of the program
        shepherdHut1TextField.setEditable(false);
        shepherdHut2TextField.setEditable(false);
        shepherdHut3TextField.setEditable(false);
        yurt1TextField.setEditable(false);
        yurt2TextField.setEditable(false);
        yurt3TextField.setEditable(false);
        yurt4TextField.setEditable(false);
        geodesicDome1TextField.setEditable(false);
        geodesicDome2TextField.setEditable(false);
        geodesicDome3TextField.setEditable(false);
        geodesicDome4TextField.setEditable(false);
        cabin1TextField.setEditable(false);
        cabin2TextField.setEditable(false);
        cabin3TextField.setEditable(false);

        areaDescriptionTextField.setEditable(false);
        breakfastReqTextField.setEditable(false);
        cleaningReqTextField.setEditable(false);
        accommodationTypeTextField.setEditable(false);
        accommodationNumberTextField.setEditable(false);
        accommodatesTextField.setEditable(false);
        checkInDatePicker.setEditable(false);
        pricePerNightTextField.setEditable(false);
        table.setEditable(false);

        isTableRowSelected();//disables update, check in/out button if table row not selected

        //will update the table based on which area is selected, save and load changed to retain values when user changes between areas
        //updates area description, accommodation type, accommodates and price per night
        //and sets "number of breakfast" and "number require cleaning" text fields to 0
        areaComboBox.setOnAction(e -> {
            if (areaComboBox.getSelectionModel().getSelectedItem().equals("Hilltop")) {
                loadHilltopTable();
                saveHilltopTableState();
                loadHilltopTableState();
            } else if (areaComboBox.getSelectionModel().getSelectedItem().equals("Wild Meadow")) {
                loadWildMeadowTable();
                saveWildMeadowTableState();
                loadWildMeadowTableState();
            } else if (areaComboBox.getSelectionModel().getSelectedItem().equals("Woodland")) {
                loadWoodlandTable();
                saveWoodlandTableState();
                loadWoodlandTableState();
            } else if (areaComboBox.getSelectionModel().getSelectedItem().equals("Lakeview")) {
                loadLakeviewTable();
                saveLakeviewTableState();
                loadLakeviewTableState();
            }
        });

    }

    private List<Table> originalHilltopAccommodations;
    private List<Table> originalWildMeadowAccommodations;
    private List<Table> originalWoodlandAccommodations;

    private List<Table> originalLakeviewAccommodations;

    public void loadHilltopTable() {
        if (originalHilltopAccommodations == null) {
            originalHilltopAccommodations = new ArrayList<>(Arrays.asList(
                    new Table(1, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(2, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(3, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No")
            ));
        }
        table.setItems(FXCollections.observableArrayList(originalHilltopAccommodations));
        areaDescriptionTextField.setText("Experience breathtaking panoramic views from the summit of a hill top and bask in the splendor of nature.");
        accommodationTypeTextField.setText("Shepherd Hut");
        accommodatesTextField.setText("3");
        pricePerNightTextField.setText("£140");
    }

    public void saveHilltopTableState() {
        originalHilltopAccommodations = new ArrayList<>(table.getItems());
    }

    public void loadHilltopTableState() {
        if (originalHilltopAccommodations != null) {
            table.setItems(FXCollections.observableArrayList(originalHilltopAccommodations));
        }
    }

    public void loadWildMeadowTable() {
        if (originalWildMeadowAccommodations == null) {
            originalWildMeadowAccommodations = new ArrayList<>(Arrays.asList(
                    new Table(1, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(2, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(3, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(4, "Yurt", "Unoccupied", "Available", "Clean", 0, "No")
            ));
        }
        table.setItems(FXCollections.observableArrayList(originalWildMeadowAccommodations));
        areaDescriptionTextField.setText("Step into a world of untamed beauty with a visit to a wild meadow, be surrounded by towering grasses and wildflowers.");
        accommodationTypeTextField.setText("Yurt");
        accommodatesTextField.setText("2");
        pricePerNightTextField.setText("£110");
    }

    public void saveWildMeadowTableState() {
        originalWildMeadowAccommodations = new ArrayList<>(table.getItems());
    }

    public void loadWildMeadowTableState() {
        if (originalWildMeadowAccommodations != null) {
            table.setItems(FXCollections.observableArrayList(originalWildMeadowAccommodations));
        }
    }

    public void loadWoodlandTable() {
        if (originalWoodlandAccommodations == null) {
            originalWoodlandAccommodations = new ArrayList<>(Arrays.asList(
                    new Table(1, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(2, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(3, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(4, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No")
            ));
        }
        table.setItems(FXCollections.observableArrayList(originalWoodlandAccommodations));
        areaDescriptionTextField.setText("Find tranquility among the trees and indulge in a true escape to nature in a cozy woodland cabin.");
        accommodationTypeTextField.setText("Cabin");
        accommodatesTextField.setText("4");
        pricePerNightTextField.setText("£180");
    }

    public void saveWoodlandTableState() {
        originalWoodlandAccommodations = new ArrayList<>(table.getItems());
    }

    public void loadWoodlandTableState() {
        if (originalWoodlandAccommodations != null) {
            table.setItems(FXCollections.observableArrayList(originalWoodlandAccommodations));
        }
    }

    public void loadLakeviewTable() {
        if (originalLakeviewAccommodations == null) {
            originalLakeviewAccommodations = new ArrayList<>(Arrays.asList(
                    new Table(1, "Cabin", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(2, "Cabin", "Unoccupied", "Available", "Clean", 0, "No"),
                    new Table(3, "Cabin", "Unoccupied", "Available", "Clean", 0, "No")
            ));
        }
        table.setItems(FXCollections.observableArrayList(originalLakeviewAccommodations));
        areaDescriptionTextField.setText("Enjoy stunning views of the lake and surrounding forests in the comfort of a cozy cabin.");
        accommodationTypeTextField.setText("Cabin");
        accommodatesTextField.setText("4");
        pricePerNightTextField.setText("£180");
    }

    public void saveLakeviewTableState() {
        originalLakeviewAccommodations = new ArrayList<>(table.getItems());
    }

    public void loadLakeviewTableState() {
        if (originalLakeviewAccommodations != null) {
            table.setItems(FXCollections.observableArrayList(originalLakeviewAccommodations));
        }
    }

    public boolean firstNameValidation() {
        String firstName = firstNameTextField.getText();

        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isLetter(firstName.charAt(i))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid First Name");
                alert.setContentText("First name can only contain letters");
                alert.showAndWait();
                return false; // Validation fails
            }
        }
        if (firstName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid First Name");
            alert.setContentText("First name cannot be empty");
            alert.showAndWait();
            return false; // Validation fails
        }
        return true; // Validation is successful
    }

    public boolean lastNameValidation() {
        String lastName = lastNameTextField.getText();

        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isLetter(lastName.charAt(i))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Last Name");
                alert.setContentText("Last name can only contain letters");
                alert.showAndWait();
                return false; // Validation fails

            }
        }
        if (lastName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Last Name");
            alert.setContentText("Last name cannot be empty");
            alert.showAndWait();
            return false; // Validation fails
        }
        return true; // Validation is successful
    }

    public boolean telephoneValidation() {
        String telephone = telephoneTextField.getText();

        if (telephone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Telephone Number");
            alert.setContentText("Telephone number cannot be empty");
            alert.showAndWait();
            return false; // Validation fails
        } else if
        (!telephone.matches("\\d{11}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Telephone Number");
            alert.setContentText("Telephone number must be 11 digits long and can only be integers");
            alert.showAndWait();
            return false; // Validation fails
        }
        return true; // Validation is successful
    }


    public boolean numberOfGuestsValidation() {
        String numberOfGuests = numberOfGuestsTextField.getText();
        int accommodates = Integer.parseInt(accommodatesTextField.getText());

        if (areaComboBox.getSelectionModel().getSelectedItem().equals("Hilltop") || areaComboBox.getSelectionModel().getSelectedItem().equals("Woodland")
                || areaComboBox.getSelectionModel().getSelectedItem().equals("Wild Meadow") || areaComboBox.getSelectionModel().getSelectedItem().equals("Lakeview")) {
            try {
                int value = Integer.parseInt(numberOfGuests);
                if (value > accommodates) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Number Of Guests");
                    alert.setContentText("Number of guests cannot exceed accommodates");
                    alert.showAndWait();
                    return false; // Validation fails
                } else if (value <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Number Of Guests");
                    alert.setContentText("Number of guests cannot be less than or equal to 0");
                    alert.showAndWait();
                    return false; // Validation fails
                }
            } catch (NumberFormatException e) {
                if (numberOfGuests.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Number Of Guests");
                    alert.setContentText("Number of guests cannot be empty");
                    alert.showAndWait();
                    return false; // Validation fails
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Number Of Guests");
                    alert.setContentText("Number of guests must be an integer");
                    alert.showAndWait();
                    return false; // Validation fails
                }
            }
        }
        return true; // Validation is successful
    }

    public boolean checkInDatePickerValidation() {
        if (checkInDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Check in Date");
            alert.setContentText("Check in Date cannot be empty");
            alert.showAndWait();
            return false; // Validation fails
        }
        return true; // Validation is successful
    }

    public boolean numberOfNightsValidation() {
        String numberOfNights = numberOfNightsTextField.getText();

        try {
            int value = Integer.parseInt(numberOfNights);
            if (value <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Check in Date");
                alert.setContentText("Number of nights cannot be less than or equal to 0");
                alert.showAndWait();
                return false; // Validation fails
            }
        } catch (NumberFormatException e) {
            if (numberOfNights.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Check in Date");
                alert.setContentText("Number of nights cannot be empty");
                alert.showAndWait();
                return false; // Validation fails
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Check in Date");
                alert.setContentText("Number of nights must be an integer");
                alert.showAndWait();
                return false; // Validation fails
            }
        }
        return true; // Validation is successful
    }

    public boolean availabilityValidation () {
        if (availabilityCol.getCellData(table.getSelectionModel().getSelectedItem()).equals("Unavailable")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Check In");
            alert.setContentText("You cannot check into an unavailable accommodation, it is either occupied or requires cleaning");
            alert.showAndWait();
            return false; // Validation fails
        }
        return true; // Validation is successful
    }

    public void isTableRowSelected() { //disables update, check in/out button if row not selected
        checkInButton.setDisable(true);
        checkOutButton.setDisable(true);
        updateCleanStatusButton.setDisable(true);

        table.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex.intValue() >= 0) {
                checkInButton.setDisable(false);
                checkOutButton.setDisable(false);
                updateCleanStatusButton.setDisable(false);
            }
        });
    }

    public void copyUserInputToOverview(){

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String telephone = telephoneTextField.getText();
        String numberOfGuests = numberOfGuestsTextField.getText();
        String checkInDate = String.valueOf(checkInDatePicker.getValue());
        String numberOfNights = numberOfNightsTextField.getText();

        String combinedTextField = firstName + " " + lastName + ", " + telephone + ", Number of guests: " + numberOfGuests + " , Check in date: " + checkInDate + ", Number of nights: " + numberOfNights;

        int selectedRow = table.getSelectionModel().getSelectedIndex();
        int selectedIndex = areaComboBox.getSelectionModel().getSelectedIndex();

        //code which copies the user's inputs to the overview section
        //selected index of 0 represents hilltop
        if (selectedIndex == 0 && selectedRow == 0) {
            shepherdHut1TextField.setText(combinedTextField);
        } else if (selectedIndex == 0 && selectedRow == 1) {
            shepherdHut2TextField.setText(combinedTextField);
        } else if (selectedIndex == 0 && selectedRow == 2) {
            shepherdHut3TextField.setText(combinedTextField);
        }

        //selected index of 1 represents wild meadow
        if (selectedIndex == 1 && selectedRow == 0) {
            yurt1TextField.setText(combinedTextField);
        } else if (selectedIndex == 1 && selectedRow == 1) {
            yurt2TextField.setText(combinedTextField);
        } else if (selectedIndex == 1 && selectedRow == 2) {
            yurt3TextField.setText(combinedTextField);
        } else if (selectedIndex == 1 && selectedRow == 3) {
            yurt4TextField.setText(combinedTextField);
        }

        //selected index of 2 represents woodland
        if (selectedIndex == 2 && selectedRow == 0) {
            geodesicDome1TextField.setText(combinedTextField);
        } else if (selectedIndex == 2 && selectedRow == 1) {
            geodesicDome2TextField.setText(combinedTextField);
        } else if (selectedIndex == 2 && selectedRow == 2) {
            geodesicDome3TextField.setText(combinedTextField);
        } else if (selectedIndex == 2 && selectedRow == 3) {
            geodesicDome4TextField.setText(combinedTextField);
        }

        //selected index of 3 represents lakeview
        if (selectedIndex == 3 && selectedRow == 0) {
            cabin1TextField.setText(combinedTextField);
        } else if (selectedIndex == 3 && selectedRow == 1) {
            cabin2TextField.setText(combinedTextField);
        } else if (selectedIndex == 3 && selectedRow == 2) {
            cabin3TextField.setText(combinedTextField);
        }

    }

    public void clearOverviewTextField(){
        int selectedRow = table.getSelectionModel().getSelectedIndex();
        int selectedIndex = areaComboBox.getSelectionModel().getSelectedIndex();

        //code which clears the user's inputs in the overview section as they are getting checked out
        //selected index of 0 represents hilltop
        if (selectedIndex == 0 && selectedRow == 0) {
            shepherdHut1TextField.clear();
        } else if (selectedIndex == 0 && selectedRow == 1) {
            shepherdHut2TextField.clear();
        } else if (selectedIndex == 0 && selectedRow == 2) {
            shepherdHut3TextField.clear();
        }

        //selected index of 1 represents wild meadow
        if (selectedIndex == 1 && selectedRow == 0) {
            yurt1TextField.clear();
        } else if (selectedIndex == 1 && selectedRow == 1) {
            yurt2TextField.clear();
        } else if (selectedIndex == 1 && selectedRow == 2) {
            yurt3TextField.clear();
        } else if (selectedIndex == 1 && selectedRow == 3) {
            yurt4TextField.clear();
        }

        //selected index of 2 represents woodland
        if (selectedIndex == 2 && selectedRow == 0) {
            geodesicDome1TextField.clear();
        } else if (selectedIndex == 2 && selectedRow == 1) {
            geodesicDome2TextField.clear();
        } else if (selectedIndex == 2 && selectedRow == 2) {
            geodesicDome3TextField.clear();
        } else if (selectedIndex == 2 && selectedRow == 3) {
            geodesicDome4TextField.clear();
        }

        //selected index of 3 represents lakeview
        if (selectedIndex == 3 && selectedRow == 0) {
            cabin1TextField.clear();
        } else if (selectedIndex == 3 && selectedRow == 1) {
            cabin2TextField.clear();
        } else if (selectedIndex == 3 && selectedRow == 2) {
            cabin3TextField.clear();
        }
    }

}
