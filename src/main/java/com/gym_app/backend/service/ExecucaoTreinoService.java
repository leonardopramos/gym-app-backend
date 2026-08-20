package com.gym_app.backend.service;

import com.gym_app.backend.domain.*;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.exception.*;
import com.gym_app.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExecucaoTreinoService {
    private final ExecucaoTreinoRepository execucoes;
    private final ExecucaoExercicioRepository series;
    private final SessaoTreinoRepository sessoes;
    private final SessaoExercicioRepository exercicios;
    private final UsuarioRepository usuarios;

    public ExecucaoTreinoService(ExecucaoTreinoRepository e, ExecucaoExercicioRepository s, SessaoTreinoRepository st, SessaoExercicioRepository se, UsuarioRepository u) { execucoes=e; series=s; sessoes=st; exercicios=se; usuarios=u; }

    @Transactional
    public ExecucaoTreinoResponse iniciar(String alunoId, ExecucaoTreinoRequest request) {
        Usuario aluno = aluno(alunoId);
        SessaoTreino sessao = sessao(request.sessaoTreinoId());
        if (!sessao.getTreino().getAluno().getId().equals(alunoId)) throw new BusinessException("Sessão não pertence ao aluno");
        return ExecucaoTreinoResponse.from(execucoes.findByAlunoIdAndSessaoIdAndFinalizadoEmIsNull(alunoId,sessao.getId()).orElseGet(() -> execucoes.save(new ExecucaoTreino(UUID.randomUUID().toString(),aluno,sessao))));
    }

    @Transactional
    public ExecucaoTreinoResponse registrar(String alunoId, String id, SerieRequest request) {
        ExecucaoTreino execucao = propria(id, alunoId); aberta(execucao);
        SessaoExercicio item = exercicio(request.sessaoExercicioId());
        validarItem(execucao,item);
        if (series.existsByExecucaoIdAndSessaoExercicioIdAndSerie(id,item.getId(),request.serie())) throw new BusinessException("Série já registrada");
        execucao.adicionarSerie(new ExecucaoExercicio(UUID.randomUUID().toString(),execucao,item,request.serie(),request.repeticoesRealizadas(),request.cargaUtilizada(),request.concluida()));
        execucoes.save(execucao);
        return ExecucaoTreinoResponse.from(execucao);
    }

    @Transactional
    public ExecucaoTreinoResponse atualizar(String alunoId,String id,String serieId,SerieAtualizacaoRequest request) {
        ExecucaoTreino execucao=propria(id,alunoId); aberta(execucao);
        ExecucaoExercicio serie=series.findByIdAndExecucaoId(serieId,id).orElseThrow(() -> new ResourceNotFoundException("Série não encontrada"));
        serie.atualizar(request.repeticoesRealizadas(),request.cargaUtilizada(),request.concluida());
        return ExecucaoTreinoResponse.from(execucao);
    }

    @Transactional
    public ExecucaoTreinoResponse finalizar(String alunoId,String id) { ExecucaoTreino e=propria(id,alunoId); aberta(e); e.finalizar(); return ExecucaoTreinoResponse.from(e); }

    @Transactional(readOnly=true)
    public ExecucaoTreinoResponse buscar(String alunoId,String id) { return ExecucaoTreinoResponse.from(propria(id,alunoId)); }

    @Transactional(readOnly=true)
    public List<ExecucaoHistoricoResponse> historico(String alunoId) { aluno(alunoId); return execucoes.findHistorico(alunoId).stream().map(ExecucaoHistoricoResponse::from).toList(); }

    @Transactional(readOnly=true)
    public List<ExercicioHistoricoResponse> historicoExercicio(String alunoId,String exercicioId) {
        aluno(alunoId);
        return execucoes.findHistorico(alunoId).stream().map(ExecucaoTreino::getSeries).flatMap(Collection::stream)
            .filter(s -> s.getSessaoExercicio().getExercicio().getId().equals(exercicioId))
            .collect(Collectors.groupingBy(s -> s.getExecucaoData(), LinkedHashMap::new, Collectors.toList())).entrySet().stream()
            .map(x -> new ExercicioHistoricoResponse(x.getKey(), x.getValue().stream().map(ExercicioHistoricoResponse.SerieHistoricoResponse::from).toList())).toList();
    }

    private ExecucaoTreino propria(String id,String alunoId) { ExecucaoTreino e=execucoes.findById(id).orElseThrow(() -> new ResourceNotFoundException("Execução não encontrada")); if(!e.getAluno().getId().equals(alunoId)) throw new BusinessException("Execução não pertence ao aluno"); return e; }
    private void aberta(ExecucaoTreino e) { if(e.getFinalizadoEm()!=null) throw new BusinessException("Execução já finalizada"); }
    private void validarItem(ExecucaoTreino e,SessaoExercicio item) { if(!item.getSessao().getId().equals(e.getSessao().getId())) throw new BusinessException("Exercício não pertence à sessão da execução"); }
    private Usuario aluno(String id) { Usuario u=usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")); if(u.getTipo()!=TipoUsuario.ALUNO) throw new BusinessException("Usuário não é aluno"); return u; }
    private SessaoTreino sessao(String id) { return sessoes.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada")); }
    private SessaoExercicio exercicio(String id) { return exercicios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exercício da sessão não encontrado")); }
}
