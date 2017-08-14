import java.util.LinkedList;


public class Client {
    private String[] data;
    private String name;
    private LinkedList<Integer> clientData;


    public Client(String[] data) {
        this.data = data;
        this.name = data[0];
    }

    public String getName() {
        return name;
    }

    public LinkedList<Integer> getClientData() {
        clientData = new LinkedList<Integer>();
        for (int i = 1; i < data.length; i++) {
            clientData.add(Integer.parseInt(data[i]));
        }
        return clientData;
    }
}
