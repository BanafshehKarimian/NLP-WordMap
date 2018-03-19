import java.awt.*;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

public class TestOpenCloud {

  /*  private static final String[] WORDS = { "art", "australia", "baby", "beach", "birthday", "blue", "bw", "california", "canada", "canon",
            "cat", "chicago", "china", "christmas", "city", "dog", "england", "europe", "family", "festival", "flower", "flowers", "food",
            "france", "friends", "fun", "germany", "holiday", "india", "italy", "japan", "london", "me", "mexico", "music", "nature",
            "new", "newyork", "night", "nikon", "nyc", "paris", "park", "party", "people", "portrait", "sanfrancisco", "sky", "snow",
            "spain", "summer", "sunset", "taiwan", "tokyo", "travel", "trip", "uk", "usa", "vacation", "water", "wedding" };
*/
    protected void initUI(String[] WORDS,Map<String, Integer> c) {
        JFrame frame = new JFrame(TestOpenCloud.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
       /* Cloud cloud = new Cloud();
        Random random = new Random();
        for (String s : WORDS) {
            for (int i = c.get(s); i > 0; i--) {
                cloud.addTag(s);
            }
        }*/
        for (Map.Entry< String, Integer > entry : c.entrySet()) {
            //System.out.println(entry.getKey()+" : "+entry.getValue());
            if(entry.getValue()>10) {
                final JLabel label = new JLabel(entry.getKey());
                label.setOpaque(false);
                label.setFont(new Font("arial", Font.PLAIN, entry.getValue()));
                //label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
                panel.add(label);
            }
        }
       /* for (Tag tag : cloud.tags()) {
            final JLabel label = new JLabel(tag.getName());
            label.setOpaque(false);
            label.setFont(new Font("arial", Font.PLAIN, (int) tag.getWeight() *100));
            //label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
            panel.add(label);
        }*/
        frame.add(panel);
        //frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void run(String [] in,Map<String, Integer> c) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestOpenCloud().initUI(in,c);
            }
        });
    }

}