package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Client;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antoine
 */
public class ClientDalTest {

    /**
     * Test of init method, of class ClientDal.
     */
    @Test
    public void testInit() throws Exception {
        System.out.println("init");
        ClientDal instance = new ClientDalImpl();
        boolean expResult = true;
        boolean result = instance.init();
        assertEquals(expResult, result);

        result = instance.init();
        assertEquals(false, result);

    }

    /**
     * Test of getClients method, of class ClientDal.
     */
    @Test
    public void testGetClients() throws Exception {
        System.out.println("getClients");
        ClientDal instance = new ClientDalImpl();
        List<Client> expResult = new ArrayList<>();
        List<Client> result = instance.getClients();
        assertEquals(expResult, result);
        
        instance.init();
        assertEquals(2, instance.getClients().size());

    }

    /**
     * Test of getClientByUsername method, of class ClientDal.
     */
    @Test
    public void testGetClientByUsername() throws Exception {
        System.out.println("getClientByUsername");
        String username = "antoine";
        ClientDal instance = new ClientDalImpl();
        Client expResult = new Client("antoine","test");
        instance.init();
        Client result = instance.getClientByUsername(username);
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());

    }

    /**
     * Test of addClient method, of class ClientDal.
     */
    @Test
    public void testAddClient() throws Exception {
        System.out.println("addClient");
        String username = "test";
        String password = "nova";
        ClientDal instance = new ClientDalImpl();
        Client expResult = new Client(username,password);
        Client result = instance.addClient(username, password);
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());
    }

    /**
     * Test of setAdmin method, of class ClientDal.
     */
    @Test
    public void testSetAdmin() throws Exception {
        System.out.println("setAdmin");
        String username = "antoine";
        ClientDal instance = new ClientDalImpl();
        boolean expResult = true;
        instance.init();
        boolean result = instance.setAdmin(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of login method, of class ClientDal.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "antoine";
        String password = "test";
        ClientDal instance = new ClientDalImpl();
        instance.init();
        Client expResult = new Client(username,password);
        Client result = instance.login(username, password);
        
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());
        
        assertEquals(expResult.getUsername(), instance.getCurrentClient().getUsername());
        assertEquals(expResult.getPassword(), instance.getCurrentClient().getPassword());
        
        assertNull(instance.login("", ""));

    }

    public class ClientDalImpl implements ClientDal {

        private List<Client> clients = new ArrayList<>();
        private Client current = null;

        public boolean init() throws Exception {
            Client client = new Client("antoine", "test");
            Client client2 = new Client("clement", "test");

            if (getClientByUsername("antoine") != null) {
                return false;
            }
            if (getClientByUsername("clement") != null) {
                return false;
            }

            clients.add(client);
            clients.add(client2);
            return true;
        }

        public List<Client> getClients() throws Exception {
            return clients;
        }

        public Client getClientByUsername(String username) throws Exception {
            for (Client client : clients) {
                if (client.getUsername().compareTo(username) == 0) {
                    return client;
                }
            }

            return null;
        }

        public Client addClient(String username, String password) throws Exception {
            Client client = new Client(username, password);
            clients.add(client);
            return client;
        }

        public boolean setAdmin(String username) throws Exception {
            Client client = getClientByUsername(username);
            if (client.isAdmin() == false) {
                client.setAdmin(true);
                return true;
            }
            return false;
        }

        public Client login(String username, String password) throws Exception {

            Client client = getClientByUsername(username);
            if(client == null) return null;
            if (client.getPassword().compareTo(password) == 0) {
                this.setCurrentClient(client);
                return client;
            }
            return null;

        }

        @Override
        public Client getCurrentClient() throws Exception {
            return current;
        }

        @Override
        public void setCurrentClient(Client currentClient) throws Exception {
            this.current = currentClient;
        }

    }

}
