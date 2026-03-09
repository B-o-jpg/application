package application.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private int id;
    private LocalDateTime timestamp;
    private String dinners;
    private String dessert;
    private String drink;
    private double total;
    private double cashPaid;
    private double change;

    public Receipt(int id, String dinners, String dessert, String drink, double total, double cashPaid) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.dinners = dinners;
        this.dessert = dessert;
        this.drink = drink;
        this.total = total;
        this.cashPaid = cashPaid;
        this.change = cashPaid - total;
    }

    public String getFormattedReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "--- RESTAURANT RECEIPT ---\n" +
                "Receipt ID: " + id + "\n" +
                "Time: " + timestamp.format(formatter) + "\n" +
                "--------------------------\n" +
                "Dinners: " + dinners + "\n" +
                "Dessert: " + dessert + "\n" +
                "Drinks:  " + drink + "\n" +
                "--------------------------\n" +
                "TOTAL:  M" + String.format("%.2f", total) + "\n" +
                "CASH:   M" + String.format("%.2f", cashPaid) + "\n" +
                "CHANGE: M" + String.format("%.2f", change) + "\n" +
                "--------------------------\n" +
                "Thank you for dining with us!\n";
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDinners() { return dinners; }
    public String getDessert() { return dessert; }
    public String getDrink() { return drink; }
    public double getTotal() { return total; }
    public double getCashPaid() { return cashPaid; }
    public double getChange() { return change; }
}
