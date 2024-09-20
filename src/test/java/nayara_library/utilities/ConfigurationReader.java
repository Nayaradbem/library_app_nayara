package nayara_library.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    // static = to make sure that it will run before everything else

    //make it global variable to use it in the static block and also in the method

    private static Properties properties = new Properties();

    static {

        try {
            FileInputStream file = new FileInputStream("configuration.properties");

            properties.load(file);

            //close file in the memory, save memory
            file.close();

        } catch (IOException e) {

            System.out.println("FILE NOT FOUND WITH GIVEN PATH");
            e.printStackTrace();
        }
    }

    //create utility method to use the obj to read

    public static String getProperty(String keyword){

        return properties.getProperty(keyword);
    }
}
