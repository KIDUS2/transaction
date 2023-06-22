package com.code.coding;

import com.code.coding.Controller.ChildController;
import com.code.coding.Controller.ParentController;
import com.code.coding.Dto.ResponseDto;
import com.code.coding.Model.Child;
import com.code.coding.Model.Parent;
import com.code.coding.Service.ParentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;

@SpringBootTest
class CodingApplicationTests {

    private static final String PARENT_JSON_PATH = "Parent.json";
    private static final String CHILD_JSON_PATH = "Child.json";

    @Test
    public void testGetAllParentTransactions() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Parent parentTransactions = objectMapper.readValue(new File(PARENT_JSON_PATH), Parent.class);
        ParentController parentController = new ParentController();
        parentController.setParentTransactions(parentTransactions);
        ResponseEntity<ResponseDto> responseDTO = parentController.getAllParentsTransactions();
        assertEquals(HttpStatus.OK.value(), responseDTO.getStatusCode());
    }

    @Test
    public void testGetChildTransactions() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Parent parentTransactions = objectMapper.readValue(new File(PARENT_JSON_PATH), Parent.class);
        Child childTransactions = objectMapper.readValue(new File(CHILD_JSON_PATH), Child.class);
        ChildController childController = new ChildController();

        childController.setParentTransactions(parentTransactions);
        childController.setChildTransactions(childTransactions);

        childController.setChildTransactions(childTransactions);

        ResponseEntity<ResponseDto> responseDTO = childController.getAllChildTransactions(1); // Pass the parent ID for testing
        assertEquals(HttpStatus.OK.value(), responseDTO.getStatusCode());

    }

}
