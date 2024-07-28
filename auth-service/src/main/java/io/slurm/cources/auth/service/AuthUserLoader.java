package io.slurm.cources.auth.service;

import io.slurm.cources.auth.model.UserEntity;
import io.slurm.cources.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserLoader implements CommandLineRunner {

    private final AuthUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        repository.save(
                UserEntity.
                        builder()
                        .userId(1)
                        .password(passwordEncoder.encode("bob123"))
                        .username("bob")
                        .build()
        );
        repository.save(
                UserEntity.
                        builder()
                        .userId(2)
                        .password(passwordEncoder.encode("456"))
                        .username("aleks1")
                        .build()
        );
    }
}
