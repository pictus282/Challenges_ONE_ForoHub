package com.alurachallenge.ForoHub.apiresponse.security;


import com.alurachallenge.ForoHub.repository.UsuarioUnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService
        implements UserDetailsService {

    @Autowired
    private UsuarioUnoRepository usuarioUnoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioUnoRepository.findByLogin(username);
    }

}