package controller;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Usuarios;

public class UsuariosJpaController {
    private EntityManager em;

    public UsuariosJpaController(EntityManager em) {
        this.em = em;
    }

    public Usuarios findUsuario(String login, String senha) {
        try {
            TypedQuery<Usuarios> query = em.createQuery("SELECT u FROM Usuarios u WHERE u.login = :login AND u.senha = :senha", Usuarios.class);
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
