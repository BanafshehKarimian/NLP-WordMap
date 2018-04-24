import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.max;

/**
 * Created by banafshbts on 18. 4. 18.
 */
public class WordMap {


    static Map<String, Integer> counter1 = new HashMap<>();
    static Integer c1 = 0;
    static Map<String, Integer> counter2 = new HashMap<>();
    static Integer c2 = 0;
    static Map<String, Integer[] > counterT = new HashMap<>();


    public void textForWordMap(String text1,String text2){

        //System.out.print(counter1.get("منقت"));
        //counter1.put("س",1);
        //System.out.print(counter1.get("س"));
        String arr[] = text1.split(" ");
        for(int i=0;i<arr.length;i++){
            //if(!arr[i].equals(" "))
            if(counter1.get(arr[i])==null){

                if(!arr[i].equals("")) {
                    counter1.put(arr[i], 1);
                    Integer a[] = new Integer[3];
                    a[0]=1;
                    a[1]=0;
                    a[2]=0;
                    counterT.put(arr[i],a);
                    c1++;
                }

            }
            else{

                //counter1.compute( text1.split(" ")[i] , (k, v) -> v == null ? 1 : v + 1);
                counter1.put(arr[i],counter1.get(arr[i])+1);
                Integer a[] = new Integer[3];
                a[0]=counter1.get(arr[i])+1;
                a[1]=0;
                a[2]=0;
                counterT.put(arr[i],a);

            }

        }
        String arr2[] = text2.split(" ");
        for(int i=0;i<arr2.length;i++){
            //if(!arr[i].equals(" "))
            if(counter2.get(arr2[i])==null){

                if(!arr2[i].equals("")) {
                    counter2.put(arr2[i], 1);
                    c2++;
                    if(counterT.get(arr2[i])!=null){
                        Integer a[] = new Integer[3];
                        a[0]=counterT.get(arr2[i])[0];
                        a[1]=counterT.get(arr2[i])[1]+1;
                        a[2]=0;
                        counterT.put(arr2[i],a);

                    }
                    else{
                        Integer a[] = new Integer[3];
                        a[0]=0;
                        a[1]=1;
                        a[2]=0;
                        counterT.put(arr2[i],a);

                    }
                }

            }
            else{

                //counter1.compute( text1.split(" ")[i] , (k, v) -> v == null ? 1 : v + 1);
                counter2.put(arr2[i],counter2.get(arr2[i])+1);
                Integer a[] = new Integer[3];
                a[0]=counterT.get(arr2[i])[0];
                a[1]=counterT.get(arr2[i])[1]+1;
                a[2]=0;
                counterT.put(arr2[i],a);

            }

        }
        //int max1 = 0,max2=0;
        //for (Map.Entry< String, Integer > entry : counter1.entrySet()) {
        //    if(entry.getValue()>max1)
        //        max1=entry.getValue();
        //}
        //for (Map.Entry< String, Integer > entry : counter2.entrySet()) {
         //   if(entry.getValue()>max2)
          //      max2=entry.getValue();
        //}
        BufferedWriter writer = null;
        BufferedWriter writer1 = null;
        BufferedWriter writer2 = null;
        BufferedWriter writer3 = null;
        BufferedWriter writer4 = null;
        //BufferedWriter writer5 = null;
        try {
            writer = new BufferedWriter(new FileWriter("WM_1343"));
            writer1 = new BufferedWriter(new FileWriter("WM_1343moreThan1391"));
            writer2 = new BufferedWriter(new FileWriter("WM"));
            writer3 = new BufferedWriter(new FileWriter("WM_1391"));
            writer4 = new BufferedWriter(new FileWriter("WM_1391moreThan1343"));
            //writer5 = new BufferedWriter(new FileWriter("wm2-3"));
            for (Map.Entry< String, Integer > entry : counter1.entrySet()) {
                for(int i=0;i< entry.getValue();i++)
                    writer.write(entry.getKey() + " " );
              /*  int x;
                if(counter2.get(entry.getKey())!=null)
                    x = entry.getValue()-counter2.get(entry.getKey());
                else
                    x = entry.getValue();
                if(x<0)
                    x=x*-1;
                for(int i=0;i<x;i++)
                    writer1.write(entry.getKey() + " " );*/
          //      for(int i=0;i<(max(max1,max2)-x)/4;i++)
            //        writer2.write(entry.getKey() + " " );
                writer.newLine();
                //writer1.newLine();
              //  writer2.newLine();

            }
            for (Map.Entry< String, Integer > entry : counter2.entrySet()) {
                for(int i=0;i< entry.getValue();i++)
                    writer3.write(entry.getKey() + " " );
              /*  int x;
                if(counter1.get(entry.getKey())!=null)
                    x = entry.getValue()-counter1.get(entry.getKey());
                else
                    x = entry.getValue();
                if(x<0)
                    x=x*-1;
                for(int i=0;i<x;i++)
                    writer4.write(entry.getKey() + " " );
                //for(int i=0;i<(max(max1,max2)-x)/4;i++)
                  //  writer5.write(entry.getKey() + " " );*/
                writer3.newLine();
                //writer4.newLine();
                //writer5.newLine();

            }
            Integer  max =0;
            String imp1="";
            String imp2="";
            for (Map.Entry< String, Integer[] > entry : counterT.entrySet()){
                String s = entry.getKey();
                Integer a[] =new Integer[3];
                a = entry.getValue();
                a[2]=a[0]-a[1];
                if(Math.abs(a[2])>max) {
                    if(a[2]<0)
                        imp2=s;
                    else
                        imp1=s;
                    max = Math.abs(a[2]);
                }
                counterT.put(entry.getKey(),a);
                //System.out.print("word:"+entry.getKey()+"|1:"+a[0]+"|2:"+a[1]+"|diff:"+a[2]+"\n");
                if(a[2]<0)
                    for (int i=0;i<Math.abs(a[2]);i++)
                        writer4.write(s+" ");
                else if(a[2]>0)
                    for (int i=0;i<a[2];i++)
                        writer1.write(s+" ");
                writer1.newLine();
                writer4.newLine();
            }
            System.out.print("most important feature in 1343:"+imp1+"\n");
            System.out.print("most important feature in 1391:"+imp2+"\n");
            for (Map.Entry< String, Integer[] > entry : counterT.entrySet()){

                String s = entry.getKey();
                Integer a[] =new Integer[3];
                a = entry.getValue();
                for (int i=0;i<max - Math.abs(a[2]);i++)
                    writer2.write(s+" ");
                writer2.newLine();

            }
            writer.close();
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            //writer5.close();
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

}
