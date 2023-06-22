package com.code.coding.Service;

import com.code.coding.Dto.ResponseDto;
import com.code.coding.Model.Child;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChildService {

    //this code make the child json file read file form the recourse i put so after reading the file path and
// jsonNode is a class in the Jackson library,
// which is a popular Java library for working with JSON data.after that Extract the child transactions from the "data" array in the JSON
    public ResponseEntity<ResponseDto> getAllChildTransactions() {

        try {

            ClassPathResource resource = new ClassPathResource("Child.json");
            byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);


            List<Child> childTransactions = new ArrayList<>();
            JsonNode dataNode = jsonNode.get("data");
            if (dataNode.isArray()) {
                for (JsonNode childNode : dataNode) {
                    Child childTransaction = new Child();
                    childTransaction.setId(childNode.get("id").asLong());
                    childTransaction.setParentId(childNode.get("parentId").asInt());
                    childTransaction.setPaidAmount(childNode.get("paidAmount").asInt());
                    childTransactions.add(childTransaction);
                }
            }
            childTransactions.sort(Comparator.comparingLong(Child::getId));
            ResponseDto responseDTO = new ResponseDto();
            responseDTO.setData(childTransactions);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseDto responseDTO = new ResponseDto();
            responseDTO.setData(null);
            responseDTO.setMessage("Error occurred while retrieving child transactions");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);

        }
    }
}

