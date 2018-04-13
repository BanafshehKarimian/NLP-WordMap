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

    static Map<String, Integer> counter1 = new HashMap<>();
    static Integer c1 = 0;
    static Map<String, Integer> counter2 = new HashMap<>();
    static Integer c2 = 0;
    static String text1 = "";
    static String text2 = "";
    static String[] sent1;// = new Vector<>();
    static String[] sent2;// = new Vector<>();
    static Vector<Vector<String>> train  = new Vector<>();
    static Vector<Vector<String>> test  = new Vector<>();
    static int cn1=0;
    static int cn2=0;

    public static String func(String in,String out){



        filereader fr = new filereader();
        String input = fr.read(in);
        String st = fr.cleaner(input);
        input = input.replace("?","E").replace("؟","E").replace(".","E").replace("۰","E").replace("!","E").replace("•","E");
        if(in.equals("1343")){

            sent1=input.split("E");


        }
        else{

            sent2=input.split("E");

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
        textForWordMap();
        findfrequency2();
        naiive(true);
        //PersianStemmer ps = new PersianStemmer();
        //System.out.println(ps.run("معنی دار".replace(" ","\u200C")));
    }
    public static void naiive(boolean smooth){


        float precision1 = (float) 0;
        float recall1 = (float) 0;
        float precision2 = (float) 0;
        float recall2 = (float) 0;
        float acc = (float) 0;
        int s = 0;
        if(smooth)
            s=1;
        for (int i=0;i<test.size();i++){

            test.get(i).add(prob(test.get(i),s));
            if(test.get(i).get(test.get(i).size()-2).equals(test.get(i).get(test.get(i).size()-1))) {
                if (test.get(i).get(test.get(i).size() - 2).equals("1"))
                    precision1++;
                else
                    precision2++;
                acc++;
            }

            if(test.get(i).get(test.get(i).size()-2).equals("1"))
                recall1++;
            else
                recall2++;
            //System.out.print("line"+i+":"+test.get(i).get(test.get(i).size()-2)+"="+test.get(i).get(test.get(i).size()-1)+"\n");
        }
        precision1 = precision1/test.size();
        precision2 = precision2/test.size();
        recall1 = precision1/recall1;
        recall2 = precision2/recall2;
        acc=acc/test.size();
        System.out.print("label 1 as Positive: p="+precision1+",R="+recall1+"\n");
        System.out.print("label 2 as Positive: p="+precision2+",R="+recall2+"\n");
        System.out.print("accuracy:="+acc);
    }
    public static String  prob(Vector<String >t,int smooth){

        float p_1 = (float) 1;//cn1/(cn1+cn2);
        float p_2 = (float) 1;//cn2/(cn1+cn2);
        int all1 =0;
        int all2 =0;
        int[] count = new int[t.size()-1];
        for (int i=0;i<count.length;i++)
            count[i]=0;
        int[] count2 = new int[t.size()-1];
        for (int i=0;i<count2.length;i++)
            count2[i]=0;
        for(int i=0;i<train.size();i++){
            for (int j=0;j<train.get(i).size()-1;j++){
                for (int k=0;k<t.size()-1;k++){
                    if(train.get(i).get(j).equals(t.get(k))){
                       if(train.get(i).get(train.get(i).size()-1).equals("1"))
                        count[k]++;
                       else
                           count2[k]++;
                    }
                }
                if(train.get(i).get(train.get(i).size()-1).equals("1"))
                    all1++;
                else
                    all2++;
            }
        }
        for (int i=0;i<count.length;i++){

            p_1=p_1*(count[i]+1*smooth)/(t.size()*smooth-1*smooth+all1);

        }
        p_1=p_1*cn1/(cn2+cn1);
        for (int i=0;i<count2.length;i++){

            p_2=p_2*(count2[i]+1*smooth)/(t.size()*smooth-1*smooth+all2);

        }
        p_2=p_2*cn2/(cn2+cn1);

        if(p_1>p_2)
            return "1";
        else
            return "2";
        /*if(p_1<p_2)
            return "2";
        else
            return "1";*/
        //return p;
    }
    public static void findfrequency2(){
        filereader fr = new filereader();
        Vector<Vector<String>> v1  = new Vector<>();
        for(int i=0;i<sent1.length;i++) {
            sent1[i]=fr.cleaner(sent1[i]);
            String []y =sent1[i].replace("\n"," ").split(" ");
            Vector<String > x = new Vector<>();
            boolean b=false;
            for(int j =0;j<y.length;j++)
                if (!y[j].equals("")&&!y[j].equals("\n")) {
                    x.add(y[j]);
                    b = true;
                }
            if(b)
                v1.add(x);
        }
        Vector<Vector<String>> v2  = new Vector<>();
        for(int i=0;i<sent2.length;i++) {
            sent2[i]=fr.cleaner(sent2[i]);
            String []y =sent2[i].split(" ");
            Vector<String > x = new Vector<>();
            boolean b=false;
            for(int j =0;j<y.length;j++)
                if (!y[j].equals("")&&!y[j].equals("\n")) {
                    x.add(y[j]);
                    b = true;
                }
            if(b)
                v2.add(x);
        }
        //System.out.print(sent2.length);
       /* for(int i=0;i<v2.size();i++){
            System.out.print("sent"+i+":");
            for(int j=0;j<v2.get(i).size();j++){

                System.out.print(v2.get(i).get(j)+"-");
            }
            System.out.print("\n");
        }*/
       for (int i=0;i<v1.size();i++){
           Vector<String> x = v1.get(i);
           x.add("1");
           if(i%10==0){

               test.add(x);

           }
           else{


               train.add(x);
               cn1++;

           }

       }
        for (int i=0;i<v2.size();i++){
            Vector<String> x = v2.get(i);
            x.add("2");
            if(i%10==0){

                test.add(x);

            }
            else{

                train.add(x);
                cn2++;

            }

        }


    }
    public static void textForWordMap(){

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
                for(int i=0;i<(max(max1,max2)-x)/4;i++)
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
                for(int i=0;i<(max(max1,max2)-x)/4;i++)
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

        /*String[] a1= counter2.keySet().toArray(new String[0]);
        ///*
        for(int i=0;i<a1.length;i++)
            System.out.print(i+": "+a1[i]+"\n");
        //
        System.out.print(check(a1));
        /*TestOpenCloud toc = new TestOpenCloud();
        toc.run(a1,counter1);*/
    }

    public static int check(String[] t){

       int c = 0;
       Checker ch = new Checker();
        PersianStemmer ps = new PersianStemmer();
        System.out.println(ps.run("معنی دار".replace(" ","\u200C")));
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
