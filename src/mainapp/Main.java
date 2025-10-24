package mainapp;

import java.util.Scanner;
import java.util.Map;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.BorderLayout;

import javax.swing.*;

public class Main {
    public static void main(String args[]) {

        DefaultListModel<String> model = new DefaultListModel<String>();
        JList<String> list = new JList<String>(model);

        JScrollPane scroll = new JScrollPane(list);

        File file = new File("base.txt");

        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file!");
            }
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.indexOf("on:") == -1 || line.indexOf("at") == -1) {
                    continue;
                } else {
                    model.addElement(line);
                }
            }
        } catch (FileNotFoundException w) {
            System.out.println("File Not Found!");
        }

        // Creating our main window
        JFrame frame = new JFrame();
        frame.setSize(270, 400);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);

        // Statistic Button logic open
        JButton statisticButton = new JButton("Statistic");
        statisticButton.setBackground(new Color(0xF7F7F7));
        statisticButton.setBorderPainted(false);

        statisticButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                statisticButton.setBackground(new Color(0xE0E0E0));
            }
            public void mouseExited(MouseEvent e) {
                statisticButton.setBackground(new Color(0XF7F7F7));
            }
        });

        statisticButton.addActionListener(e -> {
            Map<String, Integer> data = Statistics.datasetMaker();
            Statistics.createStatsWindow(data);
            
        });
        // Statistic Button logic close


        // Plus Button logic open
        JButton plusButton = new JButton("+");
        plusButton.setBackground(new Color(0xF7F7F7));
        plusButton.setBorderPainted(false);

        plusButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                plusButton.setBackground(new Color(0xE0E0E0));
            }
            public void mouseExited(MouseEvent e) {
                plusButton.setBackground(new Color(0XF7F7F7));
            }
        });

        plusButton.addActionListener(e -> {

            System.out.println("Button clicked!");
            try {

                String line = ("\n" + "Smoke session on: " + LocalDate.now() + " at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                model.addElement(line);

                FileWriter fileWriter = new FileWriter("base.txt", true);
                fileWriter.write(line);
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException r) {
                System.out.println("Cannot create a fileWriter");
            }
            
        });
        // Plus Button logic close

        // Adding buttons to the panel
        buttonsPanel.add(plusButton, BorderLayout.EAST);
        buttonsPanel.add(statisticButton, BorderLayout.WEST);

        // Adding stuff to the JFrame
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        // Showing our window
        frame.setVisible(true);
    }
}
