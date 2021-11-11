package com.WorkoutPlanner.web;

import com.WorkoutPlanner.domain.User;
import com.WorkoutPlanner.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = userRepository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(
                    username,
                    curruser.getPasswordHash(),
                    AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }
}
