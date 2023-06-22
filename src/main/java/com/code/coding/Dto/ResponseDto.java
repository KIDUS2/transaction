package com.code.coding.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
//    private String msg;
    private boolean status1;
    private Object data;
    private String message;
    private int status;
}
