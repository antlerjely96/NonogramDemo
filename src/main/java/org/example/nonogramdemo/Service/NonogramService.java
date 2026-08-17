package org.example.nonogramdemo.Service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NonogramService {
    private int rows;
    private int columns;

    private List<List<int[]>> rowPosibilities;
    private List<List<int[]>> columnsPosibilities;

    private int[][] grid;

    public int[][] solve(List<List<Integer>> rowsClue, List<List<Integer>> columnsClue){
        this.rows = rowsClue.size();
        this.columns = columnsClue.size();
        this.grid = new int[rows][columns];

        this.rowPosibilities = new ArrayList<>();
        for (List<Integer> clues : rowsClue){
            rowPosibilities.add(getAllCombinations(columns, clues));
        }

        this.columnsPosibilities = new ArrayList<>();
        for (List<Integer> clues : columnsClue){
            columnsPosibilities.add(getAllCombinations(rows, clues));
        }

        if (backtrack(0)){
            return this.grid;
        }
        return null;
    }

    private List<int[]> getAllCombinations(int length, List<Integer> clues){
        List<int[]> results = new ArrayList<>();
        generateCombinations(clues, 0, 0, new int[length], 0, results, length);
        return results;
    }

    private void generateCombinations(List<Integer> clues, int clueIdx, int currentPosition, int[] currentLine, int fillVal, List<int[]> results, int length){
        if(clueIdx == clues.size()){
            int[] lineCopy = new int[length];
            System.arraycopy(currentLine, 0, lineCopy, 0, length);

            for (int i = currentPosition; i < length; i++){
                lineCopy[i] = -1;
            }

            results.add(lineCopy);
            return;
        }

        int blockSize = clues.get(clueIdx);
        int minSpaceNeeded = 0;
        for (int i = clueIdx + 1; i < clues.size(); i++){
            minSpaceNeeded += clues.get(i);
        }
        minSpaceNeeded += (clues.size() - 1 - clueIdx);
        int maxStart = length - minSpaceNeeded - blockSize;

        for (int start = currentPosition; start <= maxStart; start++){
            int[] nextLine = new int[length];
            System.arraycopy(currentLine, 0, nextLine, 0, length);

            for (int i = currentPosition; i < start; i++){
                nextLine[i] = -1;
            }

            for (int i = start; i < start + blockSize; i++){
                nextLine[i] = 1;
            }

            int nextPosition = start + blockSize;
            if(clueIdx < clues.size() - 1){
                if (nextPosition < length){
                    nextLine[nextPosition] = -1;
                }
                nextPosition += 1;
            }

            generateCombinations(clues, clueIdx + 1, nextPosition, nextLine, fillVal, results, length);
        }
    }

    private boolean checkColumnCompatibility(int colIdx){
        for(int[] pattern : columnsPosibilities.get(colIdx)){
            boolean match = true;

            for(int row = 0; row < rows; row++){
                int gridVal = grid[row][colIdx];
                if(pattern[row] != 0 && gridVal != 0 && pattern[row] != gridVal){
                    match = false;
                    break;
                }
            }

            if (match) {
                return true;
            }
        }
        return false;
    }

    private boolean backtrack(int rowIdx){
        if (rowIdx == rows){
            for (int column = 0; column < columns; column++){
                if(!checkColumnCompatibility(column)){
                    return false;
                }
            }
            return true;
        }

        for (int[] pattern : rowPosibilities.get(rowIdx)){
            boolean valid = true;
            int[] backup = new int[columns];
            for (int column = 0; column < columns; column++){
                backup[column] = grid[rowIdx][column];
                if(grid[rowIdx][column] != 0 && grid[rowIdx][column] != pattern[column]){
                    valid = false;
                    break;
                }
                grid[rowIdx][column] = pattern[column];
            }

            if(valid){
                boolean columnCheck = true;
                for(int column = 0; column < columns; column++){
                    if (!checkColumnCompatibility(column)){
                        columnCheck = false;
                        break;
                    }
                }

                if(columnCheck){
                    if(backtrack(rowIdx + 1)){
                        return true;
                    }
                }
            }

            System.arraycopy(backup, 0, grid[rowIdx], 0, columns);
        }
        return false;
    }

}
