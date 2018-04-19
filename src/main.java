import htz.ir.stemming.PersianStemmer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.lang.Integer.highestOneBit;
import static java.lang.Integer.max;
import static java.lang.StrictMath.abs;

/**
 * Created by banafshbts on 18. 3. 19.
 */
public class main {

    static Integer c1 = 0;
    static Integer c2 = 0;
    static String text1 = "";
    static String text2 = "";
    static WordMap wm = new WordMap();
    static NB nb = new NB();

    public static String func(String in,String out){


        filereader fr = new filereader();
        String input = fr.read(in);
        String st = fr.cleaner(input);
        input = st;
        input = input.replace(".","E").replace("۰","E").replace("?","E").replace("؟","E").replace(".","E").replace("۰","E").replace("!","E").replace("•","E").replace("\n"," ");
        input = input.replace("E\\s*E","E").replace("\\s\\s*","\\s");
        input = input.replaceAll("۱"," ").replaceAll("۹"," ").replaceAll("۸"," ").replaceAll("۷"," ").replaceAll("۶"," ").replaceAll("۵"," ").replaceAll("۴"," ").replaceAll("۳"," ").replaceAll("۲"," ").replaceAll("۰"," ");
        st = st.replace("."," ").replace("۰"," ").replace("?"," ").replace("؟"," ").replace("."," ").replace("۰"," ").replace("!"," ").replace("•"," ").replace("\n"," ");
        if(in.equals("1343")){

            nb.sent1=input.replace("EE","E").replace("E E","E").split("E");


        }
        else{

            nb.sent2=input.replace("EE","E").replace("E E","E").split("E");

        }

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
        //textForWordMap();
        wv();
        wm.textForWordMap(text1,text2);
        nb.wm=wm;
        nb.init();
        nb.naiive(false);
        //PersianStemmer ps = new PersianStemmer();
        //System.out.println(ps.run("معنی دار".replace(" ","\u200C")));
    }

    public static void wv(){

        BufferedWriter writer = null;
        Vector<String > x1 =new Vector<String>();
        Vector<String > x2 =new Vector<String>();
        try {

            writer = new BufferedWriter(new FileWriter("wv"));
            int s = Math.min(nb.sent1.length,nb.sent2.length);
            int x = 0;
            for(int i=0;i<s ; i++){
                if(x%2==0)
                    if(nb.sent1[i].length()>0&&!nb.sent1[i].matches("\\s\\s*")) {
                        writer.write("1|" + nb.sent1[i] + "\n");
                        x1.add(nb.sent1[i]);
                    }
                    else
                        x--;
                else
                    if(nb.sent2[i].length()>0&&!nb.sent2[i].matches("\\s\\s*")) {
                        writer.write("2|" + nb.sent2[i] + "\n");
                        x2.add(nb.sent2[i]);
                    }
                    else
                        x--;
                x++;
                //writer.newLine();
            }
            for(int i = s-1;i<nb.sent1.length;i++){

                if(nb.sent1[i].length()>0&&!nb.sent1[i].matches("\\s\\s*")) {

                    x1.add(nb.sent1[i]);

                }
            }
            for(int i = s-1;i<nb.sent2.length;i++){

                if(nb.sent2[i].length()>0&&!nb.sent2[i].matches("\\s\\s*")) {

                    x2.add(nb.sent2[i]);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        nb.sent1 = x1.toArray(new String[x1.size()]);
        nb.sent2 = x2.toArray(new String[x2.size()]);

    }






    public static int check(String[] t){

       int c = 0;
       Checker ch = new Checker();
        PersianStemmer ps = new PersianStemmer();
//        System.out.println(ps.run("معنی دار".replace(" ","\u200C")));
       for(int i=0;i<t.length;i++){

           int x = t[i].length()-1;
            if(ch.dictionary.get(ps.run(t[i]))!=null)
                c++;
            else if(t[i].charAt(x)=='ی'){
                if(ch.dictionary.get(ps.run(t[i].replace(t[i].charAt(x)+"","")))!=null)
                    c++;//System.out.print("\n"+t[i]);
                else
                    System.out.print("\n"+t[i]+"\n");
                }
           else if(t[i].length()>1){
                String s = t[i].charAt(0)+""+t[i].charAt(1)+"";
                if(s.equals("ال")){
                    if(ch.dictionary.get(ps.run(t[i].replace("ال","")))!=null)
                        c++;
                    else
                        System.out.print("\n"+t[i]+"\n");}
                else
                    System.out.print("\n"+t[i]+"\n");

            }
            else
                System.out.print("\n"+t[i]+"\n");

       }
       return c;

    }


}
