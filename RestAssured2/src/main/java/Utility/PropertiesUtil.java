package Utility;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {

	
	private static final Properties properties = new Properties();

    static {
        // Load properties from classpath
        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("Resources/Config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Properties file not found in classpath.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Failed to load properties file.");
        }
    }
    
   
    // Method to get the API key (specific use case)
    public static String getApiKey() {
        return properties.getProperty("API_KEY");
    }

	
	
}





    