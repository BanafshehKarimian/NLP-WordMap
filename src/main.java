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
        vwAcc();
        //PersianStemmer ps = new PersianStemmer();
        //System.out.println(ps.run("معنی دار".replace(" ","\u200C")));
    }

    public static void wv(){

        BufferedWriter writer = null;
        BufferedWriter writer2 = null;
        BufferedWriter writer3 = null;
        Vector<String > x1 =new Vector<String>();
        Vector<String > x2 =new Vector<String>();
        try {

            writer = new BufferedWriter(new FileWriter("vw-train"));
            writer2 = new BufferedWriter(new FileWriter("vw-test"));
            writer3 = new BufferedWriter(new FileWriter("vw-labels"));
            //writer3.write("waeftfdffff");
            int s = Math.min(nb.sent1.length,nb.sent2.length);
            int x = 0;
            for(int i=0;i<s ; i++){
                if(x%2==0)
                    if(nb.sent1[i].length()>0&&!nb.sent1[i].matches("\\s\\s*")) {
                        if(i%10!=0)
                            writer.write("1 |" + nb.sent1[i] + "\n");
                        else{
                            writer2.write("1 |" + nb.sent1[i] + "\n");
                            writer3.write("1");
                            writer3.newLine();
                        }
                        x1.add(nb.sent1[i]);
                    }
                    else
                        x--;
                else
                    if(nb.sent2[i].length()>0&&!nb.sent2[i].matches("\\s\\s*")) {
                        if(i%10!=0)
                            writer.write("-1 |" + nb.sent2[i] + "\n");
                        else{
                            writer2.write("-1 |" + nb.sent2[i] + "\n");
                            writer3.write("-1");
                            writer3.newLine();
                        }
                        x2.add(nb.sent2[i]);
                    }
                    else
                        x--;
                x++;
                //writer.newLine();
            }
            for(int i = s-1;i<nb.sent1.length;i++){

                if(nb.sent1[i].length()>0&&!nb.sent1[i].matches("\\s\\s*")) {

                    writer2.write("1 |" + nb.sent1[i] + "\n");
                    writer3.write("1");
                    writer3.newLine();
                    x1.add(nb.sent1[i]);

                }
            }
            for(int i = s-1;i<nb.sent2.length;i++){

                if(nb.sent2[i].length()>0&&!nb.sent2[i].matches("\\s\\s*")) {

                    writer2.write("-1 |" + nb.sent2[i] + "\n");
                    writer3.write("-1");
                    writer3.newLine();
                    x2.add(nb.sent2[i]);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        nb.sent1 = x1.toArray(new String[x1.size()]);
        nb.sent2 = x2.toArray(new String[x2.size()]);

    }


    public static void vwAcc(){

        float ac2=0,ac3=0,ac4=0,ac5=0,ac6=0,ac7=0,ac10=0,ac20=0,ac40=0;
        filereader fr = new filereader();
        String[] n2 = fr.read("vw/n2.txt").split("\n");
        String[] n3 = fr.read("vw/n3.txt").split("\n");
        String[] n4 = fr.read("vw/n4.txt").split("\n");
        String[] n5 = fr.read("vw/n5.txt").split("\n");
        String[] n6 = fr.read("vw/n6.txt").split("\n");
        String[] n7 = fr.read("vw/n7.txt").split("\n");
        String[] n10 = fr.read("vw/n10.txt").split("\n");
        String[] n20 = fr.read("vw/n20.txt").split("\n");
        String[] n40 = fr.read("vw/n40.txt").split("\n");
        String[] label = fr.read("vw/vw-testLabel").split("\n");
        for (int i=0;i<label.length;i++){

            float x = Float.parseFloat(n2[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac2++;//System.out.print("ok");
            x = Float.parseFloat(n3[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac3++;
            x = Float.parseFloat(n4[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac4++;
            x = Float.parseFloat(n5[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac5++;
            x = Float.parseFloat(n6[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac6++;
            x = Float.parseFloat(n7[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac7++;
            x = Float.parseFloat(n10[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac10++;
            x = Float.parseFloat(n20[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac20++;
            x = Float.parseFloat(n40[i]);
            if(x<0)
                x=-1;
            else
                x=1;
            if(x==Float.parseFloat(label[i]))
                ac40++;


        }
        System.out.print("Accuracy for n = 2 :" + ac2/label.length+"\n");
        System.out.print("Accuracy for n = 3 :" + ac3/label.length+"\n");
        System.out.print("Accuracy for n = 4 :" + ac4/label.length+"\n");
        System.out.print("Accuracy for n = 5 :" + ac5/label.length+"\n");
        System.out.print("Accuracy for n = 6 :" + ac6/label.length+"\n");
        System.out.print("Accuracy for n = 7 :" + ac7/label.length+"\n");
        System.out.print("Accuracy for n = 10 :" + ac10/label.length+"\n");
        System.out.print("Accuracy for n = 20 :" + ac20/label.length+"\n");
        System.out.print("Accuracy for n = 40 :" + ac40/label.length+"\n");
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
