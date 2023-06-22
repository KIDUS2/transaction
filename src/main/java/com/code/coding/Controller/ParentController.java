package com.code.coding.Controller;

import com.code.coding.Dto.ResponseDto;
import com.code.coding.Model.Parent;
import com.code.coding.Service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParentController {
    @Autowired
    private ParentService parentService;
    private Parent parentTransactions;

    public void setParentTransactions(Parent parentTransactions) {
        this.parentTransactions = parentTransactions;
    }

    @GetMapping("/getAllParentsTransactions")
    public ResponseEntity<ResponseDto> getAllParentsTransactions(){
        return parentService.getAllParentsTransactions();
    }
}
