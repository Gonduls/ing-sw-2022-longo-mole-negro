package it.polimi.ingsw.client;

public class ConfigServer {
    String address;
    int port;

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
