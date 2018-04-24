import htz.ir.stemming.PersianStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by banafshbts on 18. 3. 19.
 */
public class filereader {

    String read(String name){


        InputStream in = getClass().getResourceAsStream("./"+name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        String res = "";
        try {
            while ((line = reader.readLine()) != null) {
                res+=line+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    String cleaner(String input){
        String output ="";
        output = input.replace("\u200C"," ").replace("\n"," \n ");
        output = removeSimbols(output);
        output = replaceArabic(output);
        output = removeExtra(output);
       // output = removeVerbs(output);
        output = output.replace("های ","").replace("هایی ","");

        output = output.replace("ܫܲܫ"," ").replace("ہ", " ").replace("ے"," ").replace("ں","ر").replace("ی","ی");
        output = replaceAlphabet(output);
        String x= "";
        PersianStemmer ps = new PersianStemmer();

        for(int i=0;i<output.split(" ").length;i++){

            x+= ps.run(output.split(" ")[i]);
            x+=" ";

        }

        return output;//removeVerbs(output);
    }


    String replaceAlphabet(String input){


        InputStream in = getClass().getResourceAsStream("./alphabet");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                input = input.replace(" "+line+" "," ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    String replaceArabic(String input){

        String output = input;
        InputStream in = getClass().getResourceAsStream("./arabic");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
               // if(line.split(" ").length>1)
                    output = output.replace(line.split(",")[0],line.split(",")[1]);
              //  else
                    //output = output.replace(line.split(" ")[0],"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;

    }

    String removeSimbols(String input){


        String output = input;
        InputStream in = getClass().getResourceAsStream("./sim");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                //System.out.print(line+"\n");
                output = output.replace(line," ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;

    }


    String removeExtra(String input){


        String output = input;
        InputStream in = getClass().getResourceAsStream("./extra");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                output = output.replace(" "+line+" "," ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;

    }

    String removeVerbs(String input){

        InputStream in = getClass().getResourceAsStream("./verbs");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        String output = input;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.replace("\u200C"," ");
                output = output.replace(" "+line+" "," ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //output = output.replace("های "," ").replace("ها"," ").replace("است "," ").replace("آن "," ").replace("گفت "," ").replace("این "," ").replace("یک "," ").replace("در "," ");
        output = output.replace("است"," ").replace("بود"," ").replace("کردند"," ").replace("کرد"," ").replace("کرد"," ");
        return output;

    }

}
