package org.example;

import java.util.ArrayList;
import java.util.List;

public class Logic {

    public static void add_number(Cell[][] sudoku, Integer row, Integer column, Integer number){
        sudoku[row][column].number = number;
        sudoku[row][column].possibleNumbers = new ArrayList<>();
        removeFrom_possibleNumbersLists(sudoku, row, column, number);
    }

    private static void removeFrom_possibleNumbersLists(Cell[][] sudoku, Integer row, Integer column, Integer number){
        removeNumber_fromROW(sudoku, row, number);
        removeNumber_fromCOLUMN(sudoku, column, number);
        removeNumber_fromBLOCK(sudoku, row, column, number);
    }
    private static void removeNumber_fromROW(Cell[][] sudoku, Integer row, Integer number){
        for (int i = 0; i < 9; i++) {
            sudoku[row][i].remove_listElement(number);
        }
    }
    private static void removeNumber_fromCOLUMN(Cell[][] sudoku, Integer column, Integer number){
        for (int i = 0; i < 9; i++) {
            sudoku[i][column].remove_listElement(number);
        }
    }
    private static void removeNumber_fromBLOCK(Cell[][] sudoku, Integer row, Integer column, Integer number){
        int firstRow = (row / 3) * 3;
        int firstColumn = (column / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudoku[firstRow+i][firstColumn+j].remove_listElement(number);
            }
        }
    }
    public static Integer[] find_shortestList(Cell[][] sudoku){
        int minSize = Integer.MAX_VALUE;
        Integer[] minCoordinates = new Integer[2];
        //A legrövidebb "possibleNumbers" lista megkeresése
        for (int i = 0; i < 9; i++) {                                                               //SOR
            for (int j = 0; j < 9; j++) {                                                           //OSZLOP
                List<Integer> list = sudoku[i][j].possibleNumbers;
                if (sudoku[i][j].number == null && list.size() < minSize) {
                    if (list.isEmpty()){
                        return null;
                    }
                    minSize = list.size();
                    minCoordinates[0] = i;
                    minCoordinates[1] = j;
                }
            }
        }
        return minCoordinates;
    }

    public static Cell[][] copy_sudoku(Cell[][] original){
        Cell[][] copy = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = new Cell();
                copy[i][j].number = (Integer) original[i][j].number;
                copy[i][j].possibleNumbers = new ArrayList<>(original[i][j].possibleNumbers);
            }
        }
        return copy;
    }

    public static void insert_OCRnumbers(Cell[][] sudoku, Integer[][] numbersOCR){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (numbersOCR[i][j] != null){
                    add_number(sudoku, i, j, numbersOCR[i][j]);
                    //TESZT
//                    printOut_sudoku(sudoku);
                }
            }
        }
    }

    public static void printOut_sudoku(Cell[][] sudoku) {
        System.out.println("\n");
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                System.out.print(i + "  ");
            }
            for (int j = 0; j < 9; j++) {
                if (i == 0) {
                    System.out.printf("%4d", j + 1);
                } else {
                    if (sudoku[i - 1][j].number == null) {
                        System.out.printf("%4s", "-");
                    } else {
                        System.out.printf("%4d", sudoku[i - 1][j].number);
                    }
                }
            }
            if (i == 0) {
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Integer count_emptyCells(Cell[][] sudoku){
        int cnt = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j].number == null){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static String verify (Cell[][] sudoku){
        //SOR
        for (int i = 0; i < 9; i++) {                               //SOR
            List<Integer> thisUnit = new ArrayList<>();
            for (int j = 0; j < 9; j++) {                           //OSZLOP
                if (thisUnit.contains(sudoku[i][j].number)){
                    return "ERROR IN ROW: " + i + ":" + j;
                }
                thisUnit.add(sudoku[i][j].number);
            }
        }
        //OSZLOP
        for (int i = 0; i < 9; i++) {                               //OSZLOP
            List<Integer> thisUnit = new ArrayList<>();
            for (int j = 0; j < 9; j++) {                           //SOR
                if (thisUnit.contains(sudoku[j][i].number)){
                    return "ERROR IN COLUMN: " + j + ":" + i;
                }
                thisUnit.add(sudoku[j][i].number);
            }
        }
        //BLOKK
        for (int i = 0; i < 9; i++) {                               //BLOKK
            List<Integer> thisUnit = new ArrayList<>();
            int initRow = (i/3)*3;
            int initColumn = (i%3)*3;
            for (int j = 0; j < 3; j++) {                           //SOR
                for (int k = 0; k < 3; k++) {                       //OSZLOP
                    int number = sudoku[initRow+j][initColumn+k].number;
                    if (thisUnit.contains(number)){
                        return "ERROR IN BLOCK: " + initRow+j + ":" + initColumn+k;
                    }
                    thisUnit.add(number);
                }
            }

        }
        return "VERIFIED";
    }











}
