package com.example.luxurycampsitegui;

public class Table {


    private int number;
    private String accommodationType;
    private String occupancy;
    private String availability;
    private String status;
    private int guests;
    private String breakfast;

    public Table setOccupancy(String occupancy) {
        this.occupancy = occupancy;
        return null;
    }

    public Table setAvailability(String availability) {
        this.availability = availability;
        return null;
    }

    public Table setBreakfast(String breakfast) {
        this.breakfast = breakfast;
        return null;
    }

    public Table setGuests(int guests) {
        this.guests = guests;
        return null;
    }

    public Table setStatus(String status) {
        this.status = status;
        return null;
    }

    public Table(int number, String accommodationType, String occupancy, String availability, String status, int guests, String breakfast) {
        this.number = number;
        this.accommodationType = accommodationType;
        this.occupancy = occupancy;
        this.availability = availability;
        this.status = status;
        this.guests = guests;
        this.breakfast = breakfast;
    }

    public Table() {

    }

    public int getNumber() {
        return number;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public String getAvailability() {
        return availability;
    }

    public String getStatus() {
        return status;
    }

    public int getGuests() {
        return guests;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getFirstName() {
        return null;
    }

    public String getLastName() {
        return null;
    }

}


/*

    public void loadHilltopTable() {
        ObservableList<Table> hilltopList = FXCollections.observableArrayList(
                new Table(1, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(2, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(3, "Shepherd Hut", "Unoccupied", "Available", "Clean", 0, "No")
        );
        table.setItems(hilltopList);
        areaDescriptionTextField.setText("Experience breathtaking panoramic views from the summit of a hill top and bask in the splendor of nature.");
        accommodationTypeTextField.setText("Shepherd Hut");
        accommodatesTextField.setText("3");
        pricePerNightTextField.setText("£140");
        breakfastReqTextField.setText("0");
        cleaningReqTextField.setText("0");
    }

    public void loadWildMeadowTable() {
        ObservableList<Table> wildMeadowList = FXCollections.observableArrayList(
                new Table(1, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(2, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(3, "Yurt", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(4, "Yurt", "Unoccupied", "Available", "Clean", 0, "No")
        );
        table.setItems(wildMeadowList);
        areaDescriptionTextField.setText("Step into a world of untamed beauty with a visit to a wild meadow, be surrounded by towering grasses and wildflowers.");
        accommodationTypeTextField.setText("Yurt");
        accommodatesTextField.setText("2");
        pricePerNightTextField.setText("£110");
        breakfastReqTextField.setText("0");
        cleaningReqTextField.setText("0");
    }

    public void loadWoodlandTable() {
        ObservableList<Table> woodlandList = FXCollections.observableArrayList(
                new Table(1, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(2, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(3, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(4, "Geodesic Dome", "Unoccupied", "Available", "Clean", 0, "No")
        );
        table.setItems(woodlandList);
        areaDescriptionTextField.setText("A woodland area echoing the sound of nature and wildlife, a haven for adventure seekers and nature lovers alike.");
        accommodationTypeTextField.setText("Geodesic Dome");
        accommodatesTextField.setText("2");
        pricePerNightTextField.setText("£120");
        breakfastReqTextField.setText("0");
        cleaningReqTextField.setText("0");
    }

    public void loadLakeviewTable() {
        ObservableList<Table> lakeviewList = FXCollections.observableArrayList(
                new Table(1, "Cabin", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(2, "Cabin", "Unoccupied", "Available", "Clean", 0, "No"),
                new Table(3, "Cabin", "Unoccupied", "Available", "Clean", 0, "No")
        );
        table.setItems(lakeviewList);
        areaDescriptionTextField.setText("Escape to a serene paradise with a stunning lakeview and gaze out at the still waters of a tranquil lake");
        accommodationTypeTextField.setText("Cabin");
        accommodatesTextField.setText("4");
        pricePerNightTextField.setText("£160");
        breakfastReqTextField.setText("0");
        cleaningReqTextField.setText("0");
    }

 */