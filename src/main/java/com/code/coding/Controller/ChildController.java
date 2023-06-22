package com.code.coding.Controller;

import com.code.coding.Dto.ResponseDto;
import com.code.coding.Service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class ChildController {
    @Autowired
    private ChildService childService;
    @GetMapping("/getAllChildTransactions")
    public ResponseEntity<ResponseDto> getAllChildTransactions(){
        return childService.getAllChildTransactions();
    }
}
