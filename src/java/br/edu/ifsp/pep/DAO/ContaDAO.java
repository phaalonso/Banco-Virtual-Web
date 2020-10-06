/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.Conta;
import br.edu.ifsp.pep.model.Movimentacao;
import br.edu.ifsp.pep.model.TipoMovimentacao;
import java.util.Date;
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

	public void sacar(Conta conta, Double valor) throws Exception {
		if (valor > 0) {
			conta.sacar(valor);

			TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :saqueProcessado WHERE numero = :numero", Conta.class);

			query.setParameter("saqueProcessado", conta.getSaldo());
			query.setParameter("numero", conta.getNumero());
			query.executeUpdate();

			Movimentacao mv = new Movimentacao();
			mv.setContaDestino(conta);
			mv.setData(new Date());
			mv.setTipo(TipoMovimentacao.Saque);
			mv.setValor(valor);

			em.persist(mv);
			
			System.out.println("Saque realizado com sucesso");
		} else {
			System.out.println("Valor do saque negativo");
		}		
	}

	public void depoistar(Conta conta, Double valor) throws Exception {
		if (valor > 0) {
			conta.depositar(valor);

			TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :depositoProcessado WHERE numero = :numero", Conta.class);

			query.setParameter("depositoProcessado", conta.getSaldo());
			query.setParameter("numero", conta.getNumero());
			query.executeUpdate();

			Movimentacao mv = new Movimentacao();
			mv.setContaDestino(conta);
			mv.setData(new Date());
			mv.setTipo(TipoMovimentacao.Deposito);
			mv.setValor(valor);

			em.persist(mv);
			System.out.println("Deposito realizado com sucesso");
		} else {
			throw  new Exception("Valor de deposito negativo");
		}		
	}
}
