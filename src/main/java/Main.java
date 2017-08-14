import java.io.*;
import java.util.LinkedList;


public class Main {
    public static final String WORK_DIR = System.getProperty("user.dir");
    public static final String DATA_DIR = WORK_DIR+"/data/";
    public static final String CLIENTS = "clients.txt";
    public static final String ORDERS = "orders.txt";
    public static final String RESULTS = "results.txt";

    public static void main(String[] args) {

        String[] data;
        try {
            BufferedReader cReader = new BufferedReader(new FileReader(DATA_DIR+CLIENTS));
            for (String curLine; (curLine = cReader.readLine()) != null;) {
                data = curLine.split("\t");
                Client client = new Client(data);
                trade(client.getName(),client.getClientData());
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки данных из файла "+CLIENTS);
            e.printStackTrace();
        }

    }

    public static void trade(String clientName, LinkedList<Integer> clientData) {
        String[] orderData;
        LinkedList<Integer> results;
        String resultLine;
        BufferedWriter rWriter = null;
        FileWriter fWriter = null;
        try {
            BufferedReader dReader = new BufferedReader(new FileReader(DATA_DIR+ORDERS));
            for (String orderLine; (orderLine = dReader.readLine()) != null;) {
                orderData = orderLine.split("\t");
                Order order = new Order(orderData);
                if (clientName.equals(order.getName())) {
                    if (order.getOperation().equals("b")) {
                        results = buy(clientData,order.getStockName(),order.getPrice(),order.getAmount());
                    } else {
                        results = sell(clientData,order.getStockName(),order.getPrice(),order.getAmount());
                    }
                    rWriter = new BufferedWriter(fWriter = new FileWriter(DATA_DIR+RESULTS, true));
                    resultLine = clientName;
                    for (int i = 0; i < results.size(); i++) {
                        if (i == results.size() - 1) {
                            resultLine += "\t" + results.get(i) + "\n";
                        } else {
                            resultLine += "\t" + results.get(i);
                        }

                    }

                    rWriter.write(resultLine);
                } else {}
            }
        } catch (IOException e) {
            System.err.println("Ошибка обработки данных из файла "+ORDERS);
        } finally {
            try {
                if (rWriter != null)
                    rWriter.close();

                if (fWriter != null)
                    fWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static LinkedList<Integer> buy(LinkedList<Integer> list, String stock, int price, int amount) {
        try {
            list.set(0, list.getFirst() - price * amount);

            switch (stock) {
                case "A":
                    list.set(1, list.get(1) + amount);
                    break;
                case "B":
                    list.set(2, list.get(2) + amount);
                    break;
                case "C":
                    list.set(3, list.get(3) + amount);
                    break;
                case "D":
                    list.set(4, list.getLast() + amount);
                    break;
                default:
                    System.err.println("Неизвестный тип ценной бумаги");
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Неверные входные данные");
            e.printStackTrace();
        }
        return list;
    }

    public static LinkedList<Integer> sell(LinkedList<Integer> list, String stock, int price, int amount) {
        list.set(0,list.getFirst() + price * amount);

        switch (stock) {
            case "A":
                list.set(1,list.get(1) - amount);
                break;
            case "B":
                list.set(2,list.get(2) - amount);
                break;
            case "C":
                list.set(3,list.get(3) - amount);
                break;
            case "D":
                list.set(4,list.getLast() - amount);
                break;
            default:
                System.err.println("Неизвестный тип ценной бумаги");
                break;
        }
        return list;
    }
}
