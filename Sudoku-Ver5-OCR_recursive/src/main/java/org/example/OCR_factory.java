package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OCR_factory {
    public static Integer[][] process_txtFile(String filePath){
        Integer[][] numbers_OCRed = new Integer[9][9];
        try {
            File txtFile = new File(filePath);
            Scanner txtScanner = new Scanner(txtFile);
            while (txtScanner.hasNextLine()) {
                String numbersString = txtScanner.nextLine();
                String[] lines = numbersString.split(" ");
                for (int i = 0; i < lines.length; i++) {
                    numbers_OCRed[i] = process_oneLine(lines[i]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return numbers_OCRed;
    }

    public static Integer[] process_oneLine(String oneLine){
        Integer[] oneRow = new Integer[9];
        String[] oneRowString = oneLine.split(",");
        for (int i = 0; i < oneRowString.length; i++) {
            int oneNumber = Integer.parseInt(oneRowString[i]);
            if (oneNumber > 0) {
                oneRow[i] = oneNumber;
            } else {
                oneRow[i] = null;
            }
        }

        return oneRow;
    }

}
