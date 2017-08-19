import java.io.*;
import java.util.*;


public class Main {
    public static final String WORK_DIR = System.getProperty("user.dir");
    public static final String DATA_DIR = WORK_DIR+"/data/";
    public static final String CLIENTS = "clients.txt";
    public static final String ORDERS = "orders.txt";
    public static final String RESULTS = "results.txt";
    public static HashMap<String, ArrayList<Order>> mapOfOrders;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        mapOfOrders = fillingStorage();
        String[] data;
        try {
            BufferedReader cReader = new BufferedReader(new FileReader(DATA_DIR+CLIENTS));
            for (String curLine; (curLine = cReader.readLine()) != null;) {
                data = curLine.split("\t");
                Client client = new Client(data);
                trade(client.getName(),client.getClientData(), mapOfOrders.get(client.getName()));
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки данных из файла "+CLIENTS);
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println(finish);
    }

    public static HashMap<String, ArrayList<Order>> fillingStorage() {

        HashMap<String, ArrayList<Order>> hsmap = new HashMap<>();

        String[] orderData;
        try {
            BufferedReader dReader = new BufferedReader(new FileReader(DATA_DIR+ORDERS));
            for (String orderLine; (orderLine = dReader.readLine()) != null;) {
                orderData = orderLine.split("\t");
                Order order = new Order(orderData);

                if (!hsmap.containsKey(order.getName())){
                    ArrayList<Order> arList = new ArrayList<>();
                    arList.add(order);
                    hsmap.put(order.getName(), arList);
                } else {
                    hsmap.get(order.getName()).add(order);
                }
            }
            dReader.close();

        } catch (IOException e) {
            System.err.println("Ошибка обработки данных из файла "+ORDERS);
        }
        return hsmap;
    }

    public static void trade(String clientName, LinkedList<Integer> clientData, ArrayList<Order> orders) {
        LinkedList<Integer> results;
        StringBuilder resultLine = new StringBuilder();
        BufferedWriter rWriter = null;
        try {
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                if (order.getOperation().equals("b")) {
                    results = buy(clientData,order.getStockName(),order.getPrice(),order.getAmount());
                } else {
                    results = sell(clientData,order.getStockName(),order.getPrice(),order.getAmount());
                }
                rWriter = new BufferedWriter(new FileWriter(DATA_DIR+RESULTS, true));
                resultLine.append(clientName);
                for (int j = 0; j < results.size(); j++) {
                    if (j == results.size() - 1) {
                        resultLine .append("\t" + results.get(j) + "\n");
                    } else {
                        resultLine.append("\t" + results.get(j));
                    }
                }
                rWriter.write(resultLine.toString());
                resultLine.setLength(0);
            }

        } catch (IOException e) {
            System.err.println("Ошибка обработки данных из файла "+ORDERS);
        } finally {
            try {
                if (rWriter != null)
                    rWriter.close();

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
