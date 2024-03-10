import Tools.CategoriaEvento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private final String nome, descricao, estado;
    private final int classificacao;
    private final LocalDate data;
    private final CategoriaEvento categoria;
    private final List<String> participantes = new ArrayList<>();


    public Evento(String nome, String estado, CategoriaEvento categoria, LocalDate data, String descricao, int classificacao) {
        this.nome = nome;
        this.estado = estado;
        this.categoria = categoria;
        this.data = data;
        this.descricao = descricao;
        this.classificacao = classificacao;
    }

    public void addParticipante(String usuario) {
        participantes.add(usuario);
    }

    public void removeParticipantes(String usuario){
        participantes.remove(usuario);
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getFormatedData() {
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(df);
    }

    public String getParticipante() {
        return participantes.toString();
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getData() {
        return data;
    }

    public int getClassificacao() {
        return classificacao;
    }
}
