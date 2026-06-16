package com.portfolio.campanario.service;

import com.portfolio.campanario.model.Usuario;
import com.portfolio.campanario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // READ (All)
    public Iterable<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // READ (One)
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // UPDATE (Ejemplo: cambiar rol a ADMIN)
    public Usuario actualizarRol(Long id, Usuario.Rol nuevoRol) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }

    // DELETE
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}