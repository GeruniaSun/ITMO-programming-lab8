package ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lt.shgg.commands.*;
import lt.shgg.data.Ticket;
import lt.shgg.data.Venue;
import lt.shgg.database.DatabaseParser;
import lt.shgg.network.Request;
import utils.Authorisator;
import utils.ScriptRunner;
import utils.Sender;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPageController implements Controller{
    @FXML
    private TextField filterField;
    @FXML
    private ComboBox<String> filterBox;
    @FXML
    private Button helpButton;
    @FXML
    private Button visButton;
    @FXML
    private Button exitButton;
    @FXML
    private ComboBox<String> localeButton;
    @FXML
    private ComboBox<String> typeFilter;
    @FXML
    private TableView<Ticket> table;
    @FXML
    private Label userLabel;

    private Stage stage;

    private final Sender sender = new Sender("localhost", 1488, 5000, 7);
    private ObservableList<Ticket> data = FXCollections.observableList(new DatabaseParser().load().stream().toList());

    public static Ticket currTicket;
    public static Object args;
    private Locale locale = new Locale("ru");

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
    private ComboBox<String> sortBox;

    @FXML
    public void initialize(){
        initializeTables(data);
    }

    @FXML
    public void exitButtonOnClick(){
        System.exit(0);
    }

    @FXML
    public void setLocale(){
        this.locale = new Locale(localeButton.getValue());
        changeLanguage(locale);
    }

    @FXML
    public void visualization(){
        var windowLoader = WindowLoader.getInstance();
        var visualizationController = (VisualizationController) windowLoader.getWindow(WindowEnum.VISUALIZATION_WINDOW);
        visualizationController.visualise();
    }

    @FXML
    public void help() {
        // TODO можно поменять на ресурс
        processCommand(new Help());
    }

    @FXML
    public void add(){
        showAdder();
        processCommand(new Add());
        refreshTables();
    }

    @FXML
    public void addIfMax(){
        showAdder();
        processCommand(new AddIfMax());
        refreshTables();
    }

    @FXML
    public void clear(){
        processCommand(new Clear());
        refreshTables();
    }

    @FXML
    public void removeGreater(){
        showAdder();
        processCommand(new RemoveGreater());
        refreshTables();
    }

    @FXML
    public void removeLower(){
        showAdder();
        processCommand(new RemoveLower());
        refreshTables();
    }

    @FXML
    public void info(){
        processCommand(new Info());
    }

    @FXML
    public void countGreaterThanType(){
        var windowLoader = WindowLoader.getInstance();
        var argumentReceiverController = (ArgumentReceiverController)
                windowLoader.getWindow(WindowEnum.ARGUMENT_RECEIVER_WINDOW);
        argumentReceiverController.show(locale);
        processCommand(new CountGreaterThanType());
    }

    @FXML
    public void executeScript(){
        var windowLoader = WindowLoader.getInstance();
        var argumentReceiverController = (ArgumentReceiverController)
                windowLoader.getWindow(WindowEnum.ARGUMENT_RECEIVER_WINDOW);
        argumentReceiverController.show(locale);
        var runner = new ScriptRunner();
        try {
            runner.runScript(args.toString(), Authorisator.getUser());
        } catch (AccessDeniedException | FileNotFoundException e) {
            var errorPushController = (ErrorPushController) windowLoader.getWindow(WindowEnum.ERROR_WINDOW);
            errorPushController.writeError(e, locale);
        }
        refreshTables();
    }

    @FXML
    public void filterByType(){
        if(typeFilter.getValue().equals("NONE"))
            initializeTables(FXCollections.observableList(new DatabaseParser().load().stream().toList()));
        else {
            var type = Ticket.TicketType.valueOf(typeFilter.getValue());
            var tickets = new DatabaseParser().load().stream()
                .filter(ticket -> ticket.getType().equals(type)).toList();
            initializeTables(FXCollections.observableList(tickets));
        }
    }

    @FXML
    private void filter(){
        List<Ticket> data = new DatabaseParser().load().stream().toList();
        var seq = filterField.getText();
        switch (filterBox.getValue()) {
            case "ID" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getId().toString().contains(seq)).toList()));
            case "name" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getName().contains(seq)).toList()));
            case "X" -> initializeTables(FXCollections.observableList(data.stream()
                    .filter(ticket -> Float.toString(ticket.getCoordinates().getX()).contains(seq)).toList()));
            case "Y" -> initializeTables(FXCollections.observableList(data.stream()
                    .filter(ticket -> Integer.toString(ticket.getCoordinates().getY()).contains(seq)).toList()));
            case "date" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getCreationDate().toString().contains(seq)).toList()));
            case "price" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getPrice().toString().contains(seq)).toList()));
            case "venue" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getVenue() != null &&
                            ticket.getVenue().getName().contains(seq)).toList()));
            case "capacity" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getVenue() != null &&
                            Integer.toString(ticket.getVenue().getCapacity()).contains(seq)).toList()));
            case "address" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getVenue() != null &&
                            ticket.getVenue().getAddress() != null &&
                            ticket.getVenue().getAddress().getStreet().contains(seq)).toList()));
            case "author" -> initializeTables(FXCollections.observableList(
                    data.stream().filter(ticket -> ticket.getAuthor().contains(seq)).toList()));
        }
    }

    @FXML
    private void sort(){
        var column = sortBox.getValue();
        List<Ticket> data = new DatabaseParser().load().stream().toList();
        switch (column) {
            case "ID" -> initializeTables(FXCollections.observableList(
            data.stream().sorted(Comparator.comparingLong(Ticket::getId)).toList()));
            case "name" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(Comparator.comparing(Ticket::getName)).toList()));
            case "X" -> initializeTables(FXCollections.observableList(data.stream().sorted((t1, t2) ->
                            Float.compare(t1.getCoordinates().getX(), t2.getCoordinates().getX())).toList()));
            case "Y" -> initializeTables(FXCollections.observableList(data.stream().sorted(
                    Comparator.comparingInt(t -> t.getCoordinates().getY())).toList()));
            case "date" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(Comparator.comparing(Ticket::getCreationDate)).toList()));
            case "price" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(Comparator.comparingLong(Ticket::getPrice)).toList()));
            case "venue" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(
                            Comparator.comparing(t -> t.getVenue().getName(), STRING_NULL_COMPARATOR)).toList()));
            case "capacity" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(Comparator.comparingInt(t -> t.getVenue().getCapacity())).toList()));
            case "address" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(
                            Comparator.comparing(t -> t.getVenue().getAddress(),
                            ADDRESS_NULL_COMPARATOR)).toList()));
            case "author" -> initializeTables(FXCollections.observableList(
                    data.stream().sorted(Comparator.comparing(Ticket::getAuthor)).toList()));
            default -> initializeTables(FXCollections.observableList(data));
        }
    }

    private static final Comparator<String> STRING_NULL_COMPARATOR = (c1, c2) -> {
        if (c1 != null && c2 == null) {
            return 1;
        }
        if (c1 == null && c2 != null) {
            return -1;
        }
        return 0;
    };

    private static final Comparator<Venue.Address> ADDRESS_NULL_COMPARATOR = (c1, c2) -> {
        if (c1 != null && c2 == null) {
            return 1;
        }
        if (c1 == null && c2 != null) {
            return -1;
        }
        return 0;
    };

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

    private void processCommand(Command command){
        var request = new Request();
        var windowLoader = WindowLoader.getInstance();
        request.setCommand(command);
        request.setTicket(currTicket);
        request.setArgs(args);
        request.setUser(Authorisator.getUser());
        args = null;
        currTicket = null;
        try {
            var responsePushController = (ResponsePushController) windowLoader.getWindow(WindowEnum.RESPONSE_WINDOW);
            responsePushController.writeResponse(sender.sendRequest(request), locale);
        } catch (InterruptedException e) {
            var errorPushController = (ErrorPushController) windowLoader.getWindow(WindowEnum.ERROR_WINDOW);
            errorPushController.writeError(e, locale);
        }
    }

    @FXML
    public void setUserLabel() {
        userLabel.setText(Authorisator.user.getLogin());
    }

    private void refreshTables(){
        data = FXCollections.observableList(new DatabaseParser().load().stream().toList());
        initializeTables(data);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showAdder(){
        var windowLoader = WindowLoader.getInstance();
        var addController = (AddController) windowLoader.getWindow(WindowEnum.ADD_WINDOW);
        addController.show();
    }

    private void changeLanguage(Locale locale){
        var resources = ResourceBundle.getBundle("ui.l10n.MainLabels", locale);
        exitButton.setText(resources.getString("exitButton"));
        visButton.setText(resources.getString("visButton"));
        localeButton.setPromptText(resources.getString("langButton"));
        helpButton.setText(resources.getString("helpButton"));
        sortBox.setPromptText(resources.getString("sortButton"));
        typeFilter.setPromptText(resources.getString("filterButton"));
        nameColumn.setText(resources.getString("nameCol"));
        creationDateColumn.setText(resources.getString("dateCol"));
        priceColumn.setText(resources.getString("priceCol"));
        typeColumn.setText(resources.getString("typeCol"));
        venueNameColumn.setText(resources.getString("venueCol"));
        venueCapacityColumn.setText(resources.getString("capCol"));
        addressColumn.setText(resources.getString("addressCol"));
        authorColumn.setText(resources.getString("authorCol"));
    }
}
