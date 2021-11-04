package com.example.pcroombooking.domain;

import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Seat extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.EAGER)
    private PCRoom pcRoom; // 좌석이 어느 PC실에 있는가

    @NonNull
    private String seatName; // 좌석 이름(ex - A15)

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    @ToString.Exclude
    private User user; // seat에서 사용하는 user 수정

    @NonNull
    private boolean isUsing; // 좌석 사용 중 여부

    @NonNull
    private boolean enabled; // 고장나면 false


}
