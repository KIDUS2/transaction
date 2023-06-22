package com.code.coding.Service;

import com.code.coding.Dto.ResponseDto;
import com.code.coding.Model.Child;
import com.code.coding.Model.Parent;
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
import java.util.stream.Collectors;

@Service
public class ParentService {
    //this code make the parent json file read file form the recourse i put so after reading the file path and
// jsonNode is a class in the Jackson library,
// which is a popular Java library for working with JSON data and Extract the parent transactions from the "data" array in the JSON
    // Read and parse the Child.json file and  Update the totalPaidAmount in each parent transaction and  Return the response entity with the response DTO and bad request status
    public ResponseEntity<ResponseDto> getAllParentsTransactions() {
        try {
            ClassPathResource resource = new ClassPathResource("Parent.json");
            byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            List<Parent> parents = new ArrayList<>();
            JsonNode dataNode = jsonNode.get("data");
            if (dataNode.isArray()) {
                for (JsonNode parentNode : dataNode) {
                    Parent parent = new Parent();
                    parent.setId(parentNode.get("id").asLong());
                    parent.setSender(parentNode.get("sender").asText());
                    parent.setReceiver(parentNode.get("receiver").asText());
                    parent.setTotalAmount(parentNode.get("totalAmount").asInt());
                    parent.setTotalPaidAmount(0); // Initialize totalPaidAmount as 0
                    parents.add(parent);
                }
            }

            resource = new ClassPathResource("Child.json");
            jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            jsonNode = objectMapper.readTree(jsonData);


            JsonNode childDataNode = jsonNode.get("data");
            if (childDataNode.isArray()) {
                for (JsonNode childNode : childDataNode) {
                    int parentId = childNode.get("parentId").asInt();
                    int paidAmount = childNode.get("paidAmount").asInt();

                    // Find the corresponding parent transaction and update totalPaidAmount
                    for (Parent Parent : parents) {
                        if (Parent.getId() == parentId) {
                            Parent.setTotalPaidAmount(Parent.getTotalPaidAmount() + paidAmount);
                            break;
                        }
                    }
                }
            }
            long page = 1;
            long pageSize = 2;
            List<Parent> paginated = parents.stream()
                    .sorted(Comparator.comparingLong((Parent p) -> p.getId()))
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
            ResponseDto responseDTO = new ResponseDto();
            responseDTO.setData(paginated);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.OK.value());


            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            e.printStackTrace();}
        ResponseDto responseDTO = new ResponseDto();
        responseDTO.setData(null);
        responseDTO.setMessage("Error occurred while retrieving parent transactions");
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(responseDTO);

    }
}
