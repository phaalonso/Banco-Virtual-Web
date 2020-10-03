/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.Movimentacao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pedro
 */
@Stateless
public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

	@PersistenceContext(unitName = "BancoVirtualWebPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MovimentacaoDAO() {
		super(Movimentacao.class);
	}
	
}
