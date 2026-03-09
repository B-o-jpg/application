package application.controllers;

import application.models.MenuItem;
import application.models.Receipt;
import application.utils.FileHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class POSController {

    @FXML private Label timeLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label totalLabel;
    @FXML private Label changeLabel;
    @FXML private Label errorLabel;

    @FXML private CheckBox steak;
    @FXML private CheckBox roastedChicken;
    @FXML private CheckBox creamyPasta;
    @FXML private CheckBox lambChops;
    @FXML private CheckBox grilledFish;
    @FXML private CheckBox beefBurger;

    @FXML private RadioButton chocolateCake;
    @FXML private RadioButton vanillaIceCream;
    @FXML private RadioButton applePie;
    @FXML private RadioButton malvaPudding;
    @FXML private ToggleGroup dessertGroup;

    @FXML private ComboBox<MenuItem> drinkCombo;
    @FXML private TextField cashPaidField;

    @FXML private Button saveButton;

    private List<MenuItem> dinners = new ArrayList<>();
    private List<MenuItem> drinks = new ArrayList<>();
    private List<CheckBox> dinnerCheckboxes;

    @FXML
    public void initialize() {

        setupMenu();
        setupDrinkCombo();
        setupClock();

        cashPaidField.textProperty().addListener((obs,o,n)->updateTotal());
    }

    private void setupMenu() {

        dinners.add(new MenuItem("steak","Steak",150));
        dinners.add(new MenuItem("chicken","Roasted Chicken",120));
        dinners.add(new MenuItem("pasta","Creamy Pasta",95));
        dinners.add(new MenuItem("lamb","Lamb Chops",180));
        dinners.add(new MenuItem("fish","Grilled Fish",140));
        dinners.add(new MenuItem("burger","Double Beef Burger",110));

        drinks.add(new MenuItem("water","Still Water",15));
        drinks.add(new MenuItem("soda","Soft Drink",20));
        drinks.add(new MenuItem("wine","Glass of Wine",65));
        drinks.add(new MenuItem("juice","Fresh Juice",30));
        drinks.add(new MenuItem("beer","Local Beer",35));

        dinnerCheckboxes = List.of(steak,roastedChicken,creamyPasta,lambChops,grilledFish,beefBurger);

        for(CheckBox cb : dinnerCheckboxes){
            cb.selectedProperty().addListener((obs,o,n)->updateTotal());
        }
    }

    private void setupDrinkCombo(){
        drinkCombo.getItems().addAll(drinks);
        drinkCombo.valueProperty().addListener((obs,o,n)->updateTotal());
    }

    private void setupClock(){

        Thread clock = new Thread(()->{
            while(true){
                try{
                    Thread.sleep(1000);

                    String time = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    Platform.runLater(() -> timeLabel.setText(time));

                }catch(Exception ignored){}
            }
        });

        clock.setDaemon(true);
        clock.start();
    }

    private void updateTotal(){

        double dinnerTotal = 0;

        for(int i=0;i<dinnerCheckboxes.size();i++){
            if(dinnerCheckboxes.get(i).isSelected()){
                dinnerTotal += dinners.get(i).getPrice();
            }
        }

        double dessertTotal = 0;

        if(chocolateCake.isSelected()) dessertTotal = 45;
        else if(vanillaIceCream.isSelected()) dessertTotal = 35;
        else if(applePie.isSelected()) dessertTotal = 40;
        else if(malvaPudding.isSelected()) dessertTotal = 50;

        double drinkTotal = drinkCombo.getValue()!=null ? drinkCombo.getValue().getPrice() : 0;

        double total = dinnerTotal + dessertTotal + drinkTotal;

        subtotalLabel.setText("M"+String.format("%.2f",total));
        totalLabel.setText("M"+String.format("%.2f",total));

        double cash=0;

        try{
            cash = Double.parseDouble(cashPaidField.getText());
        }catch(Exception ignored){}

        double change = Math.max(0,cash-total);
        changeLabel.setText("M"+String.format("%.2f",change));
    }

    @FXML
    private void handleSave(){

        try{

            double total = Double.parseDouble(totalLabel.getText().replace("M",""));
            double cash = Double.parseDouble(cashPaidField.getText());

            Receipt receipt = new Receipt(
                    FileHandler.getNextReceiptId(),
                    "Dinner",
                    "Dessert",
                    "Drink",
                    total,
                    cash
            );

            FileHandler.saveReceipt(receipt);

            errorLabel.setText("Receipt saved successfully!");

        }catch(Exception e){
            errorLabel.setText("Error saving receipt");
        }

        handleReset();
    }

    @FXML
    private void handleReset(){

        for(CheckBox cb : dinnerCheckboxes){
            cb.setSelected(false);
        }

        dessertGroup.selectToggle(null);
        drinkCombo.setValue(null);
        cashPaidField.clear();
        changeLabel.setText("M0.00");
        totalLabel.setText("M0.00");
        subtotalLabel.setText("M0.00");
    }

    @FXML
    private void handleExit(){

        try{

            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(root));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}