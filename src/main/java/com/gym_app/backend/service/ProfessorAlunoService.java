package com.gym_app.backend.service;
import com.gym_app.backend.domain.*;
import com.gym_app.backend.exception.*;
import com.gym_app.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class ProfessorAlunoService {
    private final ProfessorAlunoRepository links; private final UsuarioRepository usuarios;
    public ProfessorAlunoService(ProfessorAlunoRepository links, UsuarioRepository usuarios) { this.links=links; this.usuarios=usuarios; }
    @Transactional public void vincular(String professorId, String alunoId) { Usuario p=usuario(professorId); Usuario a=usuario(alunoId); if(p.getTipo()!=TipoUsuario.PROFESSOR || a.getTipo()!=TipoUsuario.ALUNO) throw new BusinessException("Os usuários não possuem os tipos esperados"); if(links.existsByProfessorIdAndAlunoId(professorId, alunoId)) throw new BusinessException("Vínculo já existe"); links.save(new ProfessorAluno(UUID.randomUUID().toString(),p,a)); }
    @Transactional(readOnly=true) public List<Usuario> alunos(String professorId) { usuario(professorId); return links.findByProfessorId(professorId).stream().map(ProfessorAluno::getAluno).toList(); }
    @Transactional public void remover(String professorId, String alunoId) { if(!links.existsByProfessorIdAndAlunoId(professorId,alunoId)) throw new ResourceNotFoundException("Vínculo não encontrado"); links.deleteByProfessorIdAndAlunoId(professorId,alunoId); }
    private Usuario usuario(String id) { return usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")); }
}
