package com.example.springoauthdemo.service;

import com.example.springoauthdemo.UserPrincipal;
import com.example.springoauthdemo.dao.UserRepository;
import com.example.springoauthdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        System.out.print("okkkkkkkkkkkkkkkkkkkkkkkk" + user);
        if (user != null) {
            return new UserPrincipal(user);
        }
        throw new UsernameNotFoundException(userName);
    }
}
