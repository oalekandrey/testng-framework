package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static Properties properties;

    static {
        properties = new Properties();
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("testEnv.properties");
            properties.load(fis);
            fis.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static String readProperty(String key){
        return properties.getProperty(key);
    }
}
