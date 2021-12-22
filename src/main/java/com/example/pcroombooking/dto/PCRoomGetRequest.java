package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PCRoomGetRequest {
    private String pcroomName;
//    private Integer buildingNumber;
//    private Integer layer;
}
