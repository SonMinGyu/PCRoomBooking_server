package com.example.pcroombooking.domain;

import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = {"conferenceRoom"})
public class ConferenceRoomReservation extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;
    private String conferenceRoomName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @ToString.Exclude
    private ConferenceRoom conferenceRoom;
    private Integer buildingNumber;
    private String locationName;
    private String date;
    private Integer startTime;
    private Integer endTime;
    private String reservationEmail;
    private boolean reserved;
    private boolean enabled;

}
