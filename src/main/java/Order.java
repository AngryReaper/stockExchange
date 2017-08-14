
public class Order {
    private String[] orderData;
    private String name;
    private String operation;
    private String stockName;
    private int price;
    private int amount;

    public Order(String[] orderData) {
        this.name = orderData[0];
        this.operation = orderData[1];
        this.stockName = orderData[2];
        try {
            this.price = Integer.parseInt(orderData[3]);
            this.amount = Integer.parseInt(orderData[4]);
        } catch (NumberFormatException e) {
            System.err.println("Переданы неверные данные!");
        }


    }

    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public String getStockName() {
        return stockName;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
