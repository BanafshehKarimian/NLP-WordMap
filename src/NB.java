import java.util.Vector;

/**
 * Created by banafshbts on 18. 4. 18.
 */
public class NB {

    static int cn1=0;
    static int cn2=0;
    static Vector<Vector<String>> train  = new Vector<>();
    static Vector<Vector<String>> test  = new Vector<>();
    static String[] sent1;// = new Vector<>();
    static String[] sent2;// = new Vector<>();
    static WordMap wm =new WordMap();




    public static void naiive(boolean smooth){


        float precision1 = (float) 0;
        float recall1 = (float) 0;
        float precision2 = (float) 0;
        float recall2 = (float) 0;
        float acc = (float) 0;
        float truePositive = (float) 0;
        float positive = (float) 0;
        float falsePositive = (float) 0;
        float truePositive2 = (float) 0;
        float positive2 = (float) 0;
        float falsePositive2 = (float) 0;

        int s = 0;
        if(smooth)
            s=1;
        for (int i=0;i<test.size();i++){

            test.get(i).add(prob(test.get(i),s));
            if(test.get(i).get(test.get(i).size()-2).equals(test.get(i).get(test.get(i).size()-1))) {
                if (test.get(i).get(test.get(i).size() - 2).equals("1")) {
                    truePositive++;
                    positive++;
                }
                else{
                    truePositive2++;
                    positive2++;
                }
                acc++;
            }
            else {
                if (test.get(i).get(test.get(i).size() - 2).equals("1")) {
                    falsePositive++;
                    positive2++;
                }
                else{
                    positive++;
                    falsePositive2++;
                }
            }
            //System.out.print("line"+i+":"+test.get(i).get(test.get(i).size()-2)+"="+test.get(i).get(test.get(i).size()-1)+"\n");
        }
        precision1 =truePositive/(truePositive+falsePositive); //precision1/test.size();
        precision2 =truePositive2/(truePositive2+falsePositive2); //precision2/test.size();
        recall1 = truePositive/positive;//precision1/recall1;
        recall2 = truePositive2/positive2;//precision2/recall2;
        acc=acc/test.size();
        System.out.print("label 1 as Positive: p="+precision1+",R="+recall1+"\n");
        System.out.print("label 2 as Positive: p="+precision2+",R="+recall2+"\n");
        System.out.print("accuracy:="+acc+"\n");
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

            //p_1+=Math.log10((count[i]+1*smooth)/(wm.counter1.size()+all1));
            p_1=p_1*(count[i]+1*smooth)/(wm.counterT.size()+all1);

        }
        p_1=p_1*cn1/(cn2+cn1);

        //p_1 += Math.log10(cn1/(cn2+cn1));
        for (int i=0;i<count2.length;i++){

            //p_2 += Math.log10((count2[i]+1*smooth)/(wm.counter2.size()+all2));
            p_2=p_2*(count2[i]+1*smooth)/(wm.counterT.size()+all2);//t.size()*smooth-1*smooth

        }
        p_2=p_2*cn2/(cn2+cn1);
        //p_2 += Math.log10(cn2/(cn2+cn1));

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


    public void init(){
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

}
