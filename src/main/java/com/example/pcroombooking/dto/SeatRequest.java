package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.User;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequest {

    private String pcRoomName; // 좌석이 있는 pc실 이름

    private String seatName; // 좌석 이름(ex - A15)

    private boolean enabled; // 고장나면 false
}
