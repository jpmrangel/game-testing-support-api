package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class SessaoDetalhesResponseDTO extends SessaoResponseDTO {
    
    private final List<BugResponseDTO> bugs;
    private Long projetoId;
    private String projetoNome;
    private Timestamp dataInicio;
    private Timestamp dataFim;

    public SessaoDetalhesResponseDTO(SessaoTeste sessao) {
        super(sessao);

        this.bugs = sessao.getBugs().stream()
                .map(BugResponseDTO::new)
                .collect(Collectors.toList());

        if (sessao.getProjeto() != null) {
            this.projetoId = sessao.getProjeto().getId_projeto();
            this.projetoNome = sessao.getProjeto().getNome();
        }

        this.dataInicio = sessao.getData_inicio();
        this.dataFim = sessao.getData_fim();
    }

    public List<BugResponseDTO> getBugs() { return bugs; }
    public Long getProjetoId() { return projetoId; }
    public String getProjetoNome() { return projetoNome; }
    public Timestamp getDataInicio() { return dataInicio; }
    public Timestamp getDataFim() { return dataFim; }
}