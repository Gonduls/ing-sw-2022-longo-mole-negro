package it.polimi.ingsw.client;

/**
 * Class needed to read config json file
 */
public class ConfigServer {
    private String address;
    private int port;

    /**
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return ip address
     */
    public String getAddress() {
        return address;
    }
}
