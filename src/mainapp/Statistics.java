package mainapp;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;


public class Statistics {
    public static JFrame createStatsWindow(Map<String, Integer> data) {
        JFrame statsFrame = new JFrame();
        statsFrame.setSize(400, 400);
        statsFrame.setDefaultCloseOperation(2);
        statsFrame.setAlwaysOnTop(true);
        statsFrame.setVisible(true);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Session", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Sessions Over Time",
            "Day",
            "Sessions",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        statsFrame.add(chartPanel);

        return statsFrame;
    }

    public static Map<String, Integer> datasetMaker() {
        try {
            File file = new File("base.txt");
            Scanner sc = new Scanner(file);

            Map <String, Integer> counts = new HashMap<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.indexOf("on:") == -1 || line.indexOf("at") == -1){
                    continue;
                } else {
                    int start = line.indexOf("on:") + 3;
                    int end = line.indexOf("at");
                
                    String date = line.substring(start, end).trim();
                    int oldValue = counts.getOrDefault(date, 0);
                    int newValue = oldValue + 1;
                    counts.put(date, newValue);
                }
                
            }
            System.out.println("=== Totals by date ===");
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                String d = entry.getKey();
                Integer c = entry.getValue();
                System.out.println(d + " -> " + c);
            }
            sc.close();
            return counts;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return null;
        }
    }
}
