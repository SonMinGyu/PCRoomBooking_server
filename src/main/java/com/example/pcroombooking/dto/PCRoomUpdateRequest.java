package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PCRoomUpdateRequest {
    private String pcroomName;
    private String seatsStr;
    private List<Seat> seats;
}
