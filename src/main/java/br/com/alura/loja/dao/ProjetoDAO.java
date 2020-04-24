package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Projeto;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ProjetoDAO {

    private static AtomicLong contador = new AtomicLong(1);
    private static Map<Long, Projeto> banco = new HashMap<Long, Projeto>();
    public static List<Projeto> projetos = new ArrayList<Projeto>();

    static {
        projetos.add(new Projeto(contador.getAndIncrement(), "Minha loja", 2014));
        projetos.add(new Projeto(contador.getAndIncrement(), "Alura", 2012));
    }

    public void adiciona(Projeto projeto) {
        projeto.setId(contador.getAndIncrement());
        projetos.add(projeto);
    }

    public List<Projeto> busca() {
        return projetos;
    }

    public Projeto busca(Long id) {
        for (Projeto projeto : projetos) {
            if (projeto.getId() == id) {
                return projeto;
            }
        }
        return null;
    }

    public void remove(long id) {
        Iterator<Projeto> iterator = projetos.iterator();
        while (iterator.hasNext()) {
            Projeto projeto = iterator.next();
            if (projeto.getId() == id) {
                iterator.remove();
            }
        }
    }

}