package com.alurachallenge.ForoHub.domain.controller;

import com.alurachallenge.ForoHub.domain.comentario.Comentario;
import com.alurachallenge.ForoHub.DTO.DatosListadoUsuarioUno;
import com.alurachallenge.ForoHub.DTO.DatosRegistroUsuarioUno;
import com.alurachallenge.ForoHub.DTO.DatosActualizarUsuarioUno;
import com.alurachallenge.ForoHub.domain.comentario.DatosRegistroComentario;
import com.alurachallenge.ForoHub.repository.UsuarioUnoRepository;
import com.alurachallenge.ForoHub.repository.ComentarioRepository;
import com.alurachallenge.ForoHub.usuarios.UsuarioUno;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/contenido")
public class ForoController {

    private final UsuarioUnoRepository usuarioUnoRepository;
    private final ComentarioRepository comentarioRepository;

    @Autowired
    public ForoController(UsuarioUnoRepository usuarioUnoRepository, ComentarioRepository comentarioRepository) {
        this.usuarioUnoRepository = usuarioUnoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @PostMapping
    public ResponseEntity<Comentario> crearComentario(@RequestBody @Valid DatosRegistroComentario datos, UriComponentsBuilder uriBuilder) {
        Comentario comentario = new Comentario();
        comentario.setTitulo(datos.getTitulo());
        comentario.setContenido(datos.getContenido());
        comentario.setFecha(datos.getFecha());
        comentario.setEstado(datos.getEstado());
        comentario.setUsuarioUno(usuarioUnoRepository.findById(datos.getUsuarioUnoId()).orElse(null));
        comentario.setNombreCurso(datos.getNombreCurso());

        comentarioRepository.save(comentario);

        URI uri = uriBuilder.path("/contenido/{id}").buildAndExpand(comentario.getId()).toUri();
        return ResponseEntity.created(uri).body(comentario);
    }

    @GetMapping("/usuario_comenta")
    public Page<DatosListadoUsuarioUno> listarUsuarios(Pageable pageable) {
        return usuarioUnoRepository.findByUsuarioActivoTrue(pageable).map(DatosListadoUsuarioUno::new);
    }

    @PostMapping("/usuarios")
    @Transactional
    @Operation(
            summary = "Crea un usuario",
            description = "Requiere nombre, email y clave.",
            tags = {"post"}
    )
    public ResponseEntity<DatosListadoUsuarioUno> crearUsuario(@RequestBody @Valid DatosRegistroUsuarioUno datosRegistroUsuario,
                                                               UriComponentsBuilder uriComponentsBuilder) {
        UsuarioUno nuevoUsuario = new UsuarioUno();
        nuevoUsuario.setLogin(datosRegistroUsuario.getLogin());
        nuevoUsuario.setNombre(datosRegistroUsuario.getNombre());
        nuevoUsuario.setEmail(datosRegistroUsuario.getEmail());
        nuevoUsuario.setContrasena(datosRegistroUsuario.getClave());
        nuevoUsuario.setUsuarioActivo(true);
        usuarioUnoRepository.save(nuevoUsuario);

        URI url = uriComponentsBuilder.path("/contenido/usuarios/{id}").buildAndExpand(nuevoUsuario.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoUsuarioUno(nuevoUsuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosActualizarUsuarioUno> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuarioUno datosActualizarUsuarioUno) {
        UsuarioUno usuarioUno = usuarioUnoRepository.findById(datosActualizarUsuarioUno.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuarioUno.actualizarDatos(datosActualizarUsuarioUno);
        return ResponseEntity.ok(datosActualizarUsuarioUno);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuarioUno(@PathVariable Long id) {
        UsuarioUno usuarioUno = usuarioUnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuarioUno.desactivaUsuario();
        return ResponseEntity.noContent().build();
    }
}