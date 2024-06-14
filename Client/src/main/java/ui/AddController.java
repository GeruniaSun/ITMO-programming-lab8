package ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.shgg.data.*;
import utils.Authorisator;

public class AddController implements Controller{
    private Stage stage;
    @FXML
    private Label label;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private TextField venueNameField;
    @FXML
    private TextField venueCapacityField;
    @FXML
    private TextField venueAddressField;

    @FXML
    public void initialize(){
        makeFieldNumeric(xField, yField, priceField, venueCapacityField);
    }

    @FXML
    private void cancelButtonOnClick(){
        //WindowLoader.getInstance().closeWindow(WindowEnum.ADD_WINDOW);
        stage.close();
    }

    public void show(){
        clear();
        stage.showAndWait();
    }

    public void fill(Ticket ticket){
        nameField.setText(ticket.getName());
        xField.setText(String.valueOf(ticket.getCoordinates().getX()));
        yField.setText(String.valueOf(ticket.getCoordinates().getY()));
        priceField.setText(String.valueOf(ticket.getPrice()));
        typeField.setValue(String.valueOf(ticket.getType()));
        venueNameField.setText(ticket.getVenue().getName());
        venueCapacityField.setText(String.valueOf(ticket.getVenue().getCapacity()));
        venueAddressField.setText(ticket.getVenue().getAddress().getStreet());
    }

    @FXML
    private void readTicket() {
        var builder = new TicketBuilder();
        try {
            builder.withName(nameField.getText());
            builder.withStringCoordinates(xField.getText(), yField.getText());
            builder.withStringPrice(priceField.getText());
            builder.withType(Ticket.TicketType.valueOf(typeField.getValue()));
            if (!(venueNameField.getText().isEmpty() && venueCapacityField.getText().isEmpty())) {
                var venueBuilder = new VenueBuilder();
                venueBuilder.withName(venueNameField.getText());
                venueBuilder.withStringCapacity(venueCapacityField.getText());
                if (!venueAddressField.getText().isEmpty())
                    venueBuilder.withAddress(new Venue.Address(venueAddressField.getText()));
                builder.withVenue(venueBuilder.build());
            }
            builder.withAuthor(Authorisator.getUser().getLogin());
            MainPageController.currTicket = builder.build();
            WindowLoader.getInstance().closeWindow(WindowEnum.ADD_WINDOW);
        } catch (NullPointerException | IllegalArgumentException e) {
            label.setText(e.getMessage());
        }
    }

    private void makeFieldNumeric(TextField... fields){
        for(TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("-?\\d+\\.?\\d*")) {
                    newValue = newValue.replaceAll("[^-\\d.]+", "");
                    field.setText(newValue);
                }
            });
        }
    }

    public void clear(){
        nameField.clear();
        xField.clear();
        yField.clear();
        priceField.clear();
        typeField.setValue(null);
        venueNameField.clear();
        venueCapacityField.clear();
        venueAddressField.clear();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
