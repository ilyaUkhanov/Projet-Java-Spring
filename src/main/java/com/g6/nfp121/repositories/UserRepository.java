package com.g6.nfp121.repositories;

import java.util.List;

import com.g6.nfp121.entities.Role;
import com.g6.nfp121.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    public List<User> findAllByRoles(Role role);
}