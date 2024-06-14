package ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lt.shgg.commands.*;
import lt.shgg.data.Ticket;
import lt.shgg.database.DatabaseParser;
import lt.shgg.network.Request;
import utils.Authorisator;
import utils.Sender;

import java.time.LocalDate;
import java.util.Locale;

public class MainPageController {
    @FXML
    private TableView<Ticket> table;
    @FXML
    private Label userLabel;

    private Sender sender = new Sender("localhost", 1488, 5000, 7);
    private ObservableList<Ticket> data = FXCollections.observableList(new DatabaseParser().load().stream().toList());

    private final ResponsePushController responsePushController = new ResponsePushController();
    private final ErrorPushController errorPushController = new ErrorPushController();
    private final AddController addController = new AddController();
    public static Ticket currTicket;
    private Locale locale = new Locale("ru");

    @FXML
    private Button exitButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button langButton;

    @FXML
    private TableColumn<Ticket, Long> idColumn;
    @FXML
    private TableColumn<Ticket, String> nameColumn;
    @FXML
    private TableColumn<Ticket, Float> xColumn;
    @FXML
    private TableColumn<Ticket, Integer> yColumn;
    @FXML
    private TableColumn<Ticket, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<Ticket, Long> priceColumn;
    @FXML
    private TableColumn<Ticket, Ticket.TicketType> typeColumn;
    @FXML
    private TableColumn<Ticket, String> venueNameColumn;
    @FXML
    private TableColumn<Ticket, Integer> venueCapacityColumn;
    @FXML
    private TableColumn<Ticket, String> addressColumn;
    @FXML
    private TableColumn<Ticket, String> authorColumn;

    @FXML
    public void initialize(){
        initializeTables(data);
    }

    @FXML
    public void exitButtonOnClick(){
        System.exit(0);
    }

    @FXML
    public void help() {}

    @FXML
    public void add(){
        addController.show();
        processCommand(new Add(), null);
        refreshTables();
    }

    @FXML
    public void addIfMax(){
        addController.show();
        processCommand(new AddIfMax(), null);
        refreshTables();
    }

    @FXML
    public void clear(){
        processCommand(new Clear(), null);
        refreshTables();
    }

    @FXML
    public void removeGreater(){
        addController.show();
        processCommand(new RemoveGreater(), null);
        refreshTables();
    }

    @FXML
    public void removeLower(){
        addController.show();
        processCommand(new RemoveLower(), null);
        refreshTables();
    }

    private void initializeTables(ObservableList<Ticket> list) {
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        xColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        creationDateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        priceColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        typeColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getType()));
        venueNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVenue().getName() == null ?
                        "null" : cellData.getValue().getVenue().getName()));
        venueCapacityColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVenue().getName() == null ?
                        0 : cellData.getValue().getVenue().getCapacity()));
        addressColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getVenue().getAddress() == null ?
                        "null" : cellData.getValue().getVenue().getAddress().getStreet()));
        authorColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getAuthor()));

        table.setItems(list);
    }

    private void processCommand(Command command, Object args){
        var request = new Request();
        request.setCommand(command);
        request.setTicket(currTicket);
        request.setArgs(args);
        request.setUser(Authorisator.getUser());
        try {
            responsePushController.writeResponse(sender.sendRequest(request), locale);
        } catch (InterruptedException e) {
            errorPushController.writeError(e, locale);
        }
    }

    @FXML
    public void setUserLabel() {
        userLabel.setText(Authorisator.getUser().getLogin());
    }

    private void refreshTables(){
        data = FXCollections.observableList(new DatabaseParser().load().stream().toList());
        initializeTables(data);
    }
}
