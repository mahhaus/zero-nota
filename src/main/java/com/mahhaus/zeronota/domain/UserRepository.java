package com.mahhaus.zeronota.domain;

import com.mahhaus.zeronota.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
