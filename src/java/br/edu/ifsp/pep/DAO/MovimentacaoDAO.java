/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.Movimentacao;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author pedro
 */
@Stateless
public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

	public MovimentacaoDAO() {
		super(Movimentacao.class);
	}

	public List<Movimentacao> selectByContaOrigem(Integer codigo) {
		try {
			TypedQuery<Movimentacao> query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.contaOrigem.numero = :codigo", Movimentacao.class);
			query.setParameter("codigo", codigo);

			return query.getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<>();
		}
	}
	
}
