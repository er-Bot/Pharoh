package core.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientCommand {

    int command;
    int client;
    int medicine;
    Date date;
    int quantity;
    double price;
    double amount;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qunatity) {
        this.quantity = qunatity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ClientCommand(int command, int client, int medicine, Date date, int quantity, double price, double amount) {
        this.command = command;
        this.client = client;
        this.medicine = medicine;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }

    public static ClientCommand getInstance(ResultSet rc) throws SQLException {
        return new ClientCommand(
                rc.getInt("command"),
                rc.getInt("client"),
                rc.getInt("medicine"),
                rc.getDate("date"),
                rc.getInt("quantity"),
                rc.getInt("price"),
                rc.getDouble("amount")
        );
    }
}
