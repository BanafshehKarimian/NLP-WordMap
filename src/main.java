import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.max;
import static java.lang.StrictMath.abs;

/**
 * Created by banafshbts on 18. 3. 19.
 */
public class main {

    static Map<String, Integer> counter1 = new HashMap<>();
    static Integer c1 = 0;
    static Map<String, Integer> counter2 = new HashMap<>();
    static Integer c2 = 0;
    static String text1 = "";
    static String text2 = "";

    public static String func(String in,String out){

        filereader fr = new filereader();
        String st = fr.cleaner(fr.read(in));
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(out));
            writer.write(st);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;

    }

    public static void main(String[] args) {

        text2=func("1391","wth").replace("\n"," ");
        text1=func("1343","wth1").replace("\n"," ");
        findfrequency();
    }
    public static void findfrequency(){

        //System.out.print(counter1.get("منقت"));
        //counter1.put("س",1);
        //System.out.print(counter1.get("س"));
        String arr[] = text1.split(" ");
        for(int i=0;i<arr.length;i++){
            //if(!arr[i].equals(" "))
            if(counter1.get(arr[i])==null){

                if(!arr[i].equals("")) {
                    counter1.put(arr[i], 1);
                    c1++;
                }

            }
            else{

                //counter1.compute( text1.split(" ")[i] , (k, v) -> v == null ? 1 : v + 1);
                counter1.put(arr[i],counter1.get(arr[i])+1);


            }

        }
        String arr2[] = text2.split(" ");
        for(int i=0;i<arr2.length;i++){
            //if(!arr[i].equals(" "))
            if(counter2.get(arr2[i])==null){

                if(!arr2[i].equals("")) {
                    counter2.put(arr2[i], 1);
                    c2++;
                }

            }
            else{

                //counter1.compute( text1.split(" ")[i] , (k, v) -> v == null ? 1 : v + 1);
                counter2.put(arr2[i],counter2.get(arr2[i])+1);


            }

        }
        int max1 = 0,max2=0;
        for (Map.Entry< String, Integer > entry : counter1.entrySet()) {
            if(entry.getValue()>max1)
                max1=entry.getValue();
        }
        for (Map.Entry< String, Integer > entry : counter2.entrySet()) {
            if(entry.getValue()>max2)
                max2=entry.getValue();
        }
        BufferedWriter writer = null;
        BufferedWriter writer1 = null;
        BufferedWriter writer2 = null;
        BufferedWriter writer3 = null;
        BufferedWriter writer4 = null;
        BufferedWriter writer5 = null;
        try {
            writer = new BufferedWriter(new FileWriter("wm1-1"));
            writer1 = new BufferedWriter(new FileWriter("wm1-2"));
            writer2 = new BufferedWriter(new FileWriter("wm1-3"));
            writer3 = new BufferedWriter(new FileWriter("wm2-1"));
            writer4 = new BufferedWriter(new FileWriter("wm2-2"));
            writer5 = new BufferedWriter(new FileWriter("wm2-3"));
            for (Map.Entry< String, Integer > entry : counter1.entrySet()) {
                for(int i=0;i< entry.getValue();i++)
                    writer.write(entry.getKey() + " " );
                int x;
                if(counter2.get(entry.getKey())!=null)
                    x = entry.getValue()-counter2.get(entry.getKey());
                else
                    x = entry.getValue();
                if(x<0)
                    x=x*-1;
                for(int i=0;i<x;i++)
                    writer1.write(entry.getKey() + " " );
                for(int i=0;i<max(max1,max2)-x;i++)
                    writer2.write(entry.getKey() + " " );
                writer.newLine();
                writer1.newLine();
                writer2.newLine();

            }
            for (Map.Entry< String, Integer > entry : counter2.entrySet()) {
                for(int i=0;i< entry.getValue();i++)
                    writer3.write(entry.getKey() + " " );
                int x;
                if(counter1.get(entry.getKey())!=null)
                    x = entry.getValue()-counter1.get(entry.getKey());
                else
                    x = entry.getValue();
                if(x<0)
                    x=x*-1;
                for(int i=0;i<x;i++)
                    writer4.write(entry.getKey() + " " );
                for(int i=0;i<max(max1,max2)-x;i++)
                    writer5.write(entry.getKey() + " " );
                writer3.newLine();
                writer4.newLine();
                writer5.newLine();

            }
            writer.close();
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            writer5.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* String[] a1= counter1.keySet().toArray(new String[0]);
        //for(int i=0;i<a.length;i++)
         //   System.out.print(a[i]);
        TestOpenCloud toc = new TestOpenCloud();
        toc.run(a1,counter1);*/
    }




}
