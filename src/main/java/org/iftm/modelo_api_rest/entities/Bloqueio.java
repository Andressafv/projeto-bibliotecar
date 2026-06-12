package org.iftm.modelo_api_rest.entities;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_bloqueio")

public class Bloqueio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name="codigo_bloqueio")
    private long codigoBloqueio;
    private String motivo;
    @Column(name="data_inicio")
    private Date dataInicio;
    @Column(name="data_fim")
    private Date dataFim;

    @JsonIgnore
    @OneToMany(mappedBy = "bloqueio", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    public Bloqueio() {
    }

    public Bloqueio(Long codigoBloqueio, String motivo, Date dataInicio, Date dataFim) {
        this.codigoBloqueio = codigoBloqueio;
        this.motivo = motivo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public long getCodigoBloqueio() {
        return codigoBloqueio;
    }

    public String getMotivo() {
        return motivo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setCodigoBloqueio(long codigoBloqueio) {
        this.codigoBloqueio = codigoBloqueio;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
  
    @JsonIgnore
    public List<Usuario> getUsuario() {
        return usuarios;
    }

    @JsonIgnore
    public void setUsuario(List<Usuario> usuario) {
        this.usuarios = usuario;
    }
}
