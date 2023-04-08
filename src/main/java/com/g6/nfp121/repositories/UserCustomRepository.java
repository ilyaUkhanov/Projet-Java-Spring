package com.g6.nfp121.repositories;

import java.util.Optional;

import com.g6.nfp121.entities.User;

public interface UserCustomRepository {
    public Optional<User> findByEmail(String email);
}
