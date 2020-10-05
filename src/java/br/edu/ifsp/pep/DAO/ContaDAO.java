/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.Conta;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author pedro
 */
@Stateless
public class ContaDAO extends GenericoDAO<Conta> {

	@PersistenceContext(unitName = "BancoVirtualWebPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ContaDAO() {
		super(Conta.class);
	}
	
	public Conta selectByNomeAndSenha(String nome, String senha) {
		EntityManager em = getEntityManager();
		
		TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.nome = :nome AND c.senha = :senha", Conta.class);
		query.setParameter("nome", nome);
		query.setParameter("senha", senha);
		
		Conta c = query.getSingleResult();
		
		return c;
	}

	public void depoistar(Conta conta, Double valor) throws Exception {
		if (valor > 0) {
			conta.depositar(valor);

			TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :depositoProcessado WHERE numero = :numero", Conta.class);

			query.setParameter("depositoProcessado", conta.getSaldo());
			query.setParameter("numero", conta.getNumero());
			query.executeUpdate();
		} else {
			throw  new Exception("Valor de deposito negativo");
		}		
	}
}
