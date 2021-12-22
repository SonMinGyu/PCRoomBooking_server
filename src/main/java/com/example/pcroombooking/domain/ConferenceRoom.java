package com.example.pcroombooking.domain;

import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConferenceRoom extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer buildingNumber;
    private String locationName;
    private Integer layer;
    private Integer peopleLimit;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "reservationId"))
    @JsonManagedReference
    private List<ConferenceRoomReservation> conferenceRoomReservations;
    private boolean allBooked;
    private boolean enable;

}
