package com.alurachallenge.ForoHub.service;

import com.alurachallenge.ForoHub.domain.comentario.Comentario;
import com.alurachallenge.ForoHub.domain.comentario.DatosRegistroComentario;
import com.alurachallenge.ForoHub.repository.ComentarioRepository;
import com.alurachallenge.ForoHub.repository.UsuarioUnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    private final UsuarioUnoRepository usuarioUnoRepository;
    private final ComentarioRepository comentarioRepository;

    @Autowired
    public ComentarioService(UsuarioUnoRepository usuarioUnoRepository, ComentarioRepository comentarioRepository) {
        this.usuarioUnoRepository = usuarioUnoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario crear(DatosRegistroComentario datos) {
        Comentario comentario = convertirAComentario(datos);
        comentarioRepository.save(comentario);
        return comentario;
    }

    private Comentario convertirAComentario(DatosRegistroComentario datos) {
        Comentario comentario = new Comentario();
        comentario.setTitulo(datos.getTitulo());
        comentario.setContenido(datos.getContenido());
        comentario.setFecha(datos.getFecha());
        comentario.setEstado(datos.getEstado());
        comentario.setUsuarioUno(usuarioUnoRepository.findById(datos.getUsuarioUno().getId()).orElse(null));
        comentario.setNombreCurso(datos.getNombreCurso());
        return comentario;
    }
}