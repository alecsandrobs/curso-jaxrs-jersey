package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement
@XmlAccessorType(FIELD)
public class Projeto {
    private long id;
    private String nome;
    private int anoDeInicio;

    public Projeto(long id, String nome, int anoDeInicio) {
        super();
        this.id = id;
        this.nome = nome;
        this.anoDeInicio = anoDeInicio;
    }

    public Projeto() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoDeInicio() {
        return anoDeInicio;
    }

    public String toXML() {
        return new XStream().toXML(this);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
