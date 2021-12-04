package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.ConferenceRoomReservation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceRoomRequest {
    private String name;
    private Integer buildingNumber;
    private String locationName;
    private Integer layer;
    private Integer limit;
    private boolean allBooked;
    private boolean enabled;
}
