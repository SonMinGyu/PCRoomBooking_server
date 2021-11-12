package com.example.pcroombooking.domain;

import com.example.pcroombooking.dto.CryptogramResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cryptogram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cryptogram;

    private String targetEmail;

    private LocalDateTime createdAt;

    private boolean verified;

    public CryptogramResponse toCryptogramResponse() {
        return CryptogramResponse.builder()
                .verified(isVerified())
                .build();
    }

}
