package com.gym_app.backend.service;
import com.gym_app.backend.domain.*;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.exception.*;
import com.gym_app.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class TreinoService {
    private final TreinoRepository treinos; private final UsuarioRepository usuarios; private final ExercicioRepository exercicios; private final ProfessorAlunoRepository links;
    public TreinoService(TreinoRepository t, UsuarioRepository u, ExercicioRepository e, ProfessorAlunoRepository l) { treinos=t; usuarios=u; exercicios=e; links=l; }
    @Transactional public TreinoResponse criar(String alunoId, String professorId, TreinoRequest r) {
        Usuario aluno=usuario(alunoId), professor=usuario(professorId);
        if(aluno.getTipo()!=TipoUsuario.ALUNO || professor.getTipo()!=TipoUsuario.PROFESSOR) throw new BusinessException("Professor ou aluno inválido");
        if(!links.existsByProfessorIdAndAlunoId(professorId,alunoId)) throw new BusinessException("Professor não está vinculado ao aluno");
        Treino treino=new Treino(UUID.randomUUID().toString(),r.nome(),r.descricao(),aluno,professor);
        r.sessoes().forEach(sr -> { SessaoTreino sessao=new SessaoTreino(UUID.randomUUID().toString(),treino,sr.nome(),sr.descricao(),sr.ordem()); sr.exercicios().forEach(ir -> sessao.adicionarExercicio(new SessaoExercicio(UUID.randomUUID().toString(),sessao,exercicio(ir.exercicioId()),ir.ordem(),ir.series(),ir.repeticoes(),ir.cargaSugerida(),ir.descansoSegundos(),ir.observacao()))); treino.adicionarSessao(sessao); });
        return TreinoResponse.from(treinos.save(treino));
    }
    @Transactional(readOnly=true) public TreinoResponse buscar(String id) { return TreinoResponse.from(treinos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado"))); }
    @Transactional(readOnly=true) public List<TreinoResponse> porAluno(String alunoId) { usuario(alunoId); return treinos.findByAlunoId(alunoId).stream().map(TreinoResponse::from).toList(); }
    @Transactional public void remover(String id) { treinos.delete(treinos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado"))); }
    private Usuario usuario(String id) { return usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")); }
    private Exercicio exercicio(String id) { return exercicios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exercício não encontrado: "+id)); }
}
