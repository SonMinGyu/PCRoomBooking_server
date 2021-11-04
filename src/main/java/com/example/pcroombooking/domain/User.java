package com.example.pcroombooking.domain;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private String studentNumber; // 학번, 교수님일 경우 X

    private String major;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
//    private Set<Authority> authorities; // 학생, 교수님 구분

    private boolean enabled;

    @OneToOne(mappedBy = "user")
    private Seat seat; // 사용중인 좌석, 읽기 전용

}
