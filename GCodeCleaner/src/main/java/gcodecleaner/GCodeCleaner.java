/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gcodecleaner;

import static gcodecleaner.GCodeCleaner.infoBox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author parke
 */
public class GCodeCleaner {

    public static List<String> badGCode = new ArrayList(Arrays.asList("G94", "G30"));
    public static File badGCodeFile = new File("badGCode.txt");
    //Arrays.asList("G94", "G30")

    public static void saveListToHiddenFile(File file, List<String> items) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GCodeCleaner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PrintWriter writer = null;
        try {
            setHiddenAttribute(file, false);;
            writer = new PrintWriter(file);
            for (String string : items) {
                if (items.indexOf(string) != items.size() - 1) {
                    writer.write(string + "\n");
                } else {
                    writer.write(string);
                }
            }
            writer.flush();
        } catch (Exception ex) {
            Logger.getLogger(GCodeCleaner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
        try {
            if (!file.isHidden()) {
                setHiddenAttribute(file, true);
            }
        } catch (Exception e) {
        }
    }

    public static void setHiddenAttribute(File file, Boolean hidden) {
        try {
            Path p = file.toPath();

            //link file to DosFileAttributes
            DosFileAttributes dos = Files.readAttributes(p, DosFileAttributes.class);

            //hide the Log file
            Files.setAttribute(p, "dos:hidden", hidden);
        } catch (IOException ex) {
            Logger.getLogger(GCodeCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
        }
        if (badGCodeFile.exists()) {
            badGCode = readHiddenFileAsList(badGCodeFile);
        } else {
            saveListToHiddenFile(badGCodeFile, badGCode);
        }
        Frame frame = new Frame();
    }

    public static boolean stringContainListOfStrings(String string, List<String> list) {
        for (String string2 : list) {
            if (string.contains(string2)) {
                return true;
            }
        }
        return false;
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static List<String> cleanGCode(File file) {

        try {

            if (file == null) {
                infoBox("No file selected", "Error");
                return null;
            }

            if (!file.getPath().toLowerCase().endsWith(".nc")) {
                infoBox("Wrong file type", "Error");
                return null;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            int lineNumber = 0;
            List<String> badLines = new ArrayList();
            while ((line = br.readLine()) != null) {
                if (!stringContainListOfStrings(line, badGCode)) {
                    System.out.println(line);
                    pw.println(line);
                    pw.flush();
                } else {
                    System.err.println(line);
                    badLines.add("Line " + lineNumber + "= " + line);
                }
                lineNumber ++;
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!file.delete()) {
                System.out.println("Could not delete file");
                return null;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(file)) {
                System.out.println("Could not rename file");
            }
            return badLines;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void resetStoredBadGCode() {
        badGCode = new ArrayList(Arrays.asList("G94", "G30"));
        saveListToHiddenFile(badGCodeFile,badGCode );
    }

    public static void addItemToList(String string) {
        badGCode.add(string);
        saveListToHiddenFile(badGCodeFile, badGCode);
    }

    public static void removeItemFromList(String string) {
        badGCode.remove(string);
        saveListToHiddenFile(badGCodeFile, badGCode);
    }

    private static List<String> readHiddenFileAsList(File file) {
        setHiddenAttribute(file, false);
        List<String> temp = new ArrayList();
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                temp.add(sc.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GCodeCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }

        setHiddenAttribute(file, true);

        return temp;
    }
}
