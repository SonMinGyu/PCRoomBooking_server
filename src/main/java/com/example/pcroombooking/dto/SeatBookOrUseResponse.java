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
public class SeatBookOrUseResponse {
    private Seat seat;
    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;
}
