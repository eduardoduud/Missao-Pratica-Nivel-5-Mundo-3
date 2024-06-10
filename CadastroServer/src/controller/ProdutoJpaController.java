package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Produto;

public class ProdutoJpaController {
    private EntityManager em;

    public ProdutoJpaController(EntityManager em) {
        this.em = em;
    }

    public List<Produto> findProdutoEntities() {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p", Produto.class);
        return query.getResultList();
    }
}
