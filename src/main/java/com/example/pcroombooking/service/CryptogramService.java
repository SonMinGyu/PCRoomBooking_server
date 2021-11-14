package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.Cryptogram;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.CryptogramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptogramService {

    private final CryptogramRepository cryptogramRepository;

    public Cryptogram addCryptogram(String cryptogram, String targetEmail) {

        Cryptogram newCrytogram = Cryptogram.builder()
                .cryptogram(cryptogram)
                .targetEmail(targetEmail)
                .verified(false)
                .createdAt(LocalDateTime.now())
                .build();

        return cryptogramRepository.save(newCrytogram);
    }

    public Cryptogram vefiryCryptogram(String inputCryptogram, String inputEmail) {
        Cryptogram crt = cryptogramRepository.findByCryptogramAndTargetEmailOrderByCreatedAtDesc(inputCryptogram, inputEmail)
        .orElseThrow(() -> new SuperException(CustomExceptionType.CRYPTOGRAM_NOT_FOUNT_EXCEPTION));

        if(!LocalDateTime.now().isBefore(crt.getCreatedAt().plusMinutes(1))) {
            // 유효기간 만료된 토큰
            System.out.println("This cryptogram is expired!!!");
            throw new SuperException(CustomExceptionType.CRYPTOGRAM_EXPIRED_EXCEPTION);
        } else {
            Long id = crt.getId();
            Cryptogram findByIdCrt = cryptogramRepository.findById(id).orElseThrow(() -> new SuperException(CustomExceptionType.CRYPTOGRAM_NOT_FOUNT_EXCEPTION));
            findByIdCrt.setVerified(true);

            return cryptogramRepository.save(findByIdCrt);
        }
    }

    public Optional<Cryptogram> getCryptogram(String cryptogram, String email) {
        return cryptogramRepository.findByCryptogramAndTargetEmailOrderByCreatedAtDesc(cryptogram, email);
    }

    // 수정필요
    public void deleteCryptogram(String cryptogram, String email) {
        Long id = cryptogramRepository.findByCryptogramAndTargetEmailOrderByCreatedAtDesc(cryptogram, email).get().getId();
        cryptogramRepository.deleteById(id);
    }
}
