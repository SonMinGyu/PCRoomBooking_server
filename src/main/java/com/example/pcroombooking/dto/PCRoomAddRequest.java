package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.PCRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PCRoomAddRequest {
    private String name;
    private int buildingNumber;
    private int layer;
    private int pcRoomLine;
    private int pcRoomRow;
    private String seatsStr;
    private int allSeatNumber;
    private int pcSeatNumber;
    private int notebookSeatNumber;
    private boolean enabled;

    public PCRoom toPCRoom() {
        return PCRoom.builder()
                .name(name)
                .buildingNumber(buildingNumber)
                .layer(layer)
                .pcRoomLine(pcRoomLine)
                .pcRoomRow(pcRoomRow)
                .seatsStr(seatsStr)
                .allSeatNumber(allSeatNumber)
                .pcSeatNumber(pcSeatNumber)
                .pcSeatBrokenNumber(0)
                .pcSeatInUseNumber(0)
                .pcSeatUseableNumber(pcSeatNumber)
                .notebookSeatNumber(notebookSeatNumber)
                .notebookSeatBrokenNumber(0)
                .notebookSeatInUseNumber(0)
                .notebookSeatUseableNumber(notebookSeatNumber)
                .enabled(enabled)
                .build();
    }
}
