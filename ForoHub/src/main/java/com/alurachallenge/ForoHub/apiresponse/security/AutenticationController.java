package com.alurachallenge.ForoHub.apiresponse.security;


import com.alurachallenge.ForoHub.DTO.DatosAutenticacion;
import com.alurachallenge.ForoHub.usuarios.UsuarioUno;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {


    public record DatosTokenJWT(String token) {}

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody @Valid DatosAutenticacion datos) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena());
        var authentication = manager.authenticate(authenticationToken);

//        var tokenJWT = tokenService.generarToken((UsuarioUno) authentication);
         var tokenJWT = tokenService.generarToken((UsuarioUno) authentication.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

}
