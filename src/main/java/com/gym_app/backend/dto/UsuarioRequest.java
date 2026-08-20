package com.gym_app.backend.dto;
import com.gym_app.backend.domain.TipoUsuario;
import jakarta.validation.constraints.*;
public record UsuarioRequest(@NotBlank @Size(max=150) String nome, @NotBlank @Email String email, @NotBlank @Size(min=6) String senha, @NotNull TipoUsuario tipo) { }
