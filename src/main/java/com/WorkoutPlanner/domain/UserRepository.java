package com.WorkoutPlanner.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // Find user by username
    User findByUsername(String username);

    // Find user by email
    User findByEmail(String email);
}
