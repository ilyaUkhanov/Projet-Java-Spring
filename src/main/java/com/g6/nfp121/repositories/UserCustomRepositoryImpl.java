package com.g6.nfp121.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.g6.nfp121.entities.User;

import org.springframework.beans.factory.annotation.Autowired;

public class UserCustomRepositoryImpl implements UserCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = (List<User>) entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :pEmail", User.class).setParameter("pEmail", email)
                .getResultList();
        if (users.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

}
