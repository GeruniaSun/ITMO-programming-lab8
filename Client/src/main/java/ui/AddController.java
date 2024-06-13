package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import lt.shgg.data.*;

import java.util.Optional;

public class AddController {
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
        clear();
        WindowLoader.getInstance().closeWindow(WindowEnum.ADD_WINDOW);
    }

    public void show(){
        WindowLoader.getInstance().showAndWaitWindow(WindowEnum.ADD_WINDOW);
    }

    @FXML
    private Optional<Ticket> readTicket() {
        var builder = new TicketBuilder();
        // TODO персонализированные ошибки в билдерах
        // TODO сообщать юзернэйм сюда
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
            builder.withAuthor("ТУТ ПЕНИС ИЛИ ЧТО");
            return Optional.ofNullable(builder.build());
        } catch (NullPointerException | IllegalArgumentException e) {
            label.setText(e.getMessage());
            return Optional.empty();
        }
    }

    private void makeFieldNumeric(TextField... fields){
        for(TextField field : fields) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("-?\\d+\\.?\\d*")) {
                        newValue = newValue.replaceAll("[^-\\d.]+", "");
                        field.setText(newValue);
                    }
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
}
