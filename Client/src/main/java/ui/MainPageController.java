package ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lt.shgg.commands.*;
import lt.shgg.data.Ticket;
import lt.shgg.data.User;
import lt.shgg.database.DatabaseParser;
import utils.Sender;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainPageController {
    @FXML
    private TableView<Ticket> table;
    @FXML
    private Label userLabel;
    private static String username;

    private Sender sender = new Sender("localhost", 1488, 5000, 7);
    private ObservableList<Ticket> data = FXCollections.observableList(new DatabaseParser().load().stream().toList());

    private final AddController addController = new AddController();

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
        //TODO исправить
        userLabel.setText("Я ТВОЮ МАМУ ЕБАЛ");
    }

    @FXML
    public void exitButtonOnClick(){
        System.exit(0);
    }

    @FXML
    public void help(){
        PushManager.info("PENIS", "", new Locale("ru", "RU"));
    }

    @FXML
    public void add(){
        addController.show();
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

    public void setUsername(String input) {
        username = input;
        userLabel.setText(username);
    }
}
