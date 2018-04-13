import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by banafshbts on 18. 4. 9.
 */
public class Checker {
    Map <String,String> dictionary = new HashMap<>();
    Checker(){


        InputStream in = getClass().getResourceAsStream("./worddb.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                dictionary.put(line,"YES");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
