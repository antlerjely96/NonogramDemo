package org.example.nonogramdemo.Controller;

import org.example.nonogramdemo.DTO.NonogramRequest;
import org.example.nonogramdemo.Service.NonogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nonogram")
@CrossOrigin(origins = "http://localhost:3000")
public class NonogramController {
    @Autowired
    private NonogramService nonogramService;

    @PostMapping("/solve")
    public ResponseEntity<int[][]> solveNonogram(@RequestBody NonogramRequest request){
        int[][] result = nonogramService.solve(request.getRowClues(), request.getColumnClues());
        if (result == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
