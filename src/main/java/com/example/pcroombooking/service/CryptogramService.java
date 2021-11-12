package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.Cryptogram;
import com.example.pcroombooking.repository.CryptogramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
        Cryptogram crt = cryptogramRepository.findByCryptogramAndTargetEmail(inputCryptogram, inputEmail)
        .orElseThrow(() -> new UsernameNotFoundException(inputCryptogram));

        if(!LocalDateTime.now().isBefore(crt.getCreatedAt().plusMinutes(3))) {
            // 유효기간 만료된 토큰
            // 추후 exception 발생시키자
            System.out.println("This cryptogram is expired!!!");

            return crt;
        } else {
            Long id = crt.getId();
            Cryptogram findByIdCrt = cryptogramRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
            findByIdCrt.setVerified(true);

            return cryptogramRepository.save(findByIdCrt);
        }
    }

    public Optional<Cryptogram> getCryptogram(String cryptogram, String email) {
        return cryptogramRepository.findByCryptogramAndTargetEmail(cryptogram, email);
    }

    public void deleteCryptogram(String cryptogram, String email) {
        Long id = cryptogramRepository.findByCryptogramAndTargetEmail(cryptogram, email).get().getId();
        cryptogramRepository.deleteById(id);
    }
}
