package com.gym_app.backend.controller;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service=service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest r) { return service.criar(r); }
    @GetMapping public List<UsuarioResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public UsuarioResponse buscar(@PathVariable String id) { return service.buscar(id); }
}
