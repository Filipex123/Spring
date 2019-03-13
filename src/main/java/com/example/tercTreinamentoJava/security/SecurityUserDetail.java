package com.example.tercTreinamentoJava.security;

import com.example.tercTreinamentoJava.model.User;
import com.example.tercTreinamentoJava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class SecurityUserDetail implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public SecurityUserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = Optional.ofNullable(userRepository.findByUserName(username))
                .orElseThrow(() ->new UsernameNotFoundException("Usuario nao existe"));
        List<GrantedAuthority> admin = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
        List<GrantedAuthority> user = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(
                dbUser.getUserName(),
                dbUser.getPassword(),
                dbUser.isAdmin()? admin : user
        );
    }
}
