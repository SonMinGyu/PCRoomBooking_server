package com.example.pcroombooking.domain;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.baseEntity.TimeBaseEntity;
import com.example.pcroombooking.dto.UserLoginResponse;
import com.example.pcroombooking.dto.UserRegisterResponse;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends TimeBaseEntity implements UserDetails {

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

    @OneToOne(mappedBy = "user")
    private Seat seat; // 사용중인 좌석, 읽기 전용

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
    @ToString.Exclude
    private Set<Authority> authorities;

    private boolean enabled;

    public UserRegisterResponse toUserRegisterResponse() {
        return UserRegisterResponse.builder()
                .name(getName())
                .email(getEmail())
                .build();
    }

//    public UserLoginResponse toUserLoginResponse() {
//        return UserLoginResponse.builder()
//                .email(getEmail())
//                .build();
//    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
