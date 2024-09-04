package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
    * Description:  This program takes a Sudoku table with preset numbers
    *               and fills it out adhering to the rules of the game.
    *
    * Functioning:  A Sudoku is represented by a 2-D array of Cells, which is 1 square out of the 81 total,
    *               containing an integer (the correct number) and an integer list (the numbers possible in given Cell).
    *               The code fills up the possible OCR'ed numbers, finds the first cell that has the shortest list
    *               of possible numbers, then starts "guessing" using these numbers and a recursive method.
    *               On the terminal, the original and the final versions of the table are shown.
    *
    * Tests:        The package contains 3 screenshots from 3 different Sudoku puzzles (from a mobile app). With the
    *               use of AI, the pictures have been OCR'ed, giving 3 .txt files also included in the package.
    *               The 3 .txt files can be fed to the program via the String filePath in the Main class for testing.
    *               The difficulty of the preset games varies, which is reflected in the number of necessary recursions.
    *
    * Verification: A verification is also included in the program
    *               to check if every number only appears once in every row, column and block.
     * */

    public static Integer runNumber = 0;

    public static void main(String[] args) {
        Cell[][] sudoku = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = new Cell();
            }
        }
        String filePath = "OCRed3.txt";
        Integer[][] numbers_OCRed = OCR_factory.process_txtFile(filePath);
        Logic.insert_OCRnumbers(sudoku, numbers_OCRed);

        Logic.printOut_sudoku(sudoku);

        sudoku = do_sudoku(sudoku);

        if (sudoku == null){
            System.out.println("Failed");
        }
        Logic.printOut_sudoku(sudoku);
        System.out.println(Logic.verify(sudoku));
        System.out.println("Number of recusrions: " + runNumber);


    }


    public static Cell[][] do_sudoku(Cell[][] original){
        if (Logic.count_emptyCells(original) == 0){                     //KÉSZ: ha már nem lehet több számot beírni
            return original;
        }
        Integer[] listCoordinates = Logic.find_shortestList(original);
        if (listCoordinates == null){
            return null;
        }
        //-------------------------------------
        //A "legrövidebb" lista adatai
        int row = listCoordinates[0];
        int column = listCoordinates[1];
        List<Integer> list = new ArrayList<>(original[row][column].possibleNumbers);
        //-------------------------------------
        while (!list.isEmpty()) {
            Cell[][] sudoku = Logic.copy_sudoku(original);
            Logic.add_number(sudoku, row, column, list.get(0));
            list.remove(0);
            runNumber++;
            Cell[][] testSudoku = do_sudoku(sudoku);
            if (testSudoku != null){
                return testSudoku;
            }
        }
        return null;
    }

    /*Leglalulról indulva:
        -HA már nincs több üres cella (number==null), visszaadja a kapott sudokut;
            -ezt a while-on belüli testSudoku fogadja, ami így nem nulla, és ezt adja tovább mindaddig, amíg visszaérünk a Main.main-be
        -HA nincs beírva szám, DE a possibleNumbers lista üres: egy fentebbi elágazásánál valahol rossz számot választottunk egy listából -> RETURN NULL
        -2. másolat (testSudoku) azért kell, hogy a 'sudoku'-t a legrövidebb lista többi elemével is végig tesztelhessük, ha egy 'testSudoku' = null
        -Végső 'return null' - ha minden számot végigpróbáltunk, mégsem kaptunk teljes sudokut
     */


}
