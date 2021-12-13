package com.example.pcroombooking.domain;

import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PCRoom extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private Integer buildingNumber; // PC실 있는 건물 번호
    private Integer layer; // PC실 있는 층
    private Integer pcRoomLine;
    private Integer pcRoomRow;
    private String seatsStr;
    @NonNull
    private Integer allSeatNumber; // PC실의 모든 좌석개수
    private Integer pcSeatNumber;
    private Integer pcSeatBrokenNumber;
    private Integer pcSeatInUseNumber;
    private Integer pcSeatUseableNumber;
    private Integer notebookSeatNumber;
    private Integer notebookSeatBrokenNumber;
    private Integer notebookSeatInUseNumber;
    private Integer notebookSeatUseableNumber;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "seatId"))
    @JsonManagedReference
    @ToString.Exclude
    private Set<Seat> seats;
    private boolean enabled; // 사용 가능 여부(교수님의 수업, 공사중, 집합금지로 인한 이유 등일 때 false)
}
