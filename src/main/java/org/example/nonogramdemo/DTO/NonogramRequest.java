package org.example.nonogramdemo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class NonogramRequest {

    // Ép Spring Boot nhận key "rowsClue" từ JSON và gán vào biến rowClues
    @JsonProperty("rowsClue")
    private List<List<Integer>> rowClues;

    // Ép Spring Boot nhận key "columnsClue" từ JSON và gán vào biến columnClues
    @JsonProperty("columnsClue")
    private List<List<Integer>> columnClues;

    public List<List<Integer>> getRowClues() {
        return rowClues;
    }

    public void setRowClues(List<List<Integer>> rowClues) {
        this.rowClues = rowClues;
    }

    public List<List<Integer>> getColumnClues() {
        return columnClues;
    }

    public void setColumnClues(List<List<Integer>> columnClues) {
        this.columnClues = columnClues;
    }
}