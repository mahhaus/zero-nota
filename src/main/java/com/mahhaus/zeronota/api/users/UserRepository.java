package com.mahhaus.zeronota.api.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
