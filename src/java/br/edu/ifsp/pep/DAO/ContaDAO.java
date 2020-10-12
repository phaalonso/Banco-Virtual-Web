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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author pedro
 */
@Stateless
public class ContaDAO extends GenericoDAO<Conta> {

	public ContaDAO() {
		super(Conta.class);
	}
	
	public Conta selectByNomeAndSenha(String nome, String senha) {
		try {
			EntityManager em = getEntityManager();
			
			TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.nome = :nome AND c.senha = :senha", Conta.class);
			query.setParameter("nome", nome);
			query.setParameter("senha", senha);
			
			Conta c = query.getSingleResult();
			
			return c;
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Conta selectById(Integer id) {

		TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.numero = :numero", Conta.class);
		query.setParameter("numero", id);
		
		return query.getSingleResult();
	}

	public void adicionarMovimentacao(Conta destino, Double valor, TipoMovimentacao tipo)  throws Exception{
		Movimentacao mv = new Movimentacao();
		destino.addMovimentacao(mv);
		mv.setContaDestino(destino);
		mv.setContaOrigem(destino);
		mv.setData(new Date());
		mv.setTipo(tipo);
		mv.setValor(valor);

		em.persist(mv);
	}

	public void adicionarMovimentacao(Conta origem, Conta destino, Double valor, TipoMovimentacao tipo)  throws Exception{
		Movimentacao mv = new Movimentacao();
		destino.addMovimentacao(mv);
		mv.setContaOrigem(origem);
		mv.setContaDestino(destino);
		mv.setData(new Date());
		mv.setTipo(tipo);
		mv.setValor(valor);

		em.persist(mv);
	}

	public void sacar(Conta conta, Double valor) throws Exception {
		if (valor > 0) {
			conta = selectById(conta.getNumero());
			
			conta.sacar(valor);

			TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :saqueProcessado WHERE numero = :numero", Conta.class);
			query.setParameter("saqueProcessado", conta.getSaldo());
			query.setParameter("numero", conta.getNumero());
			query.executeUpdate();

			adicionarMovimentacao(conta, valor, TipoMovimentacao.Saque);
			
			System.out.println("Saque realizado com sucesso");
		} else {
			System.out.println("Valor do saque negativo");
		}		
	}

	public void depoistar(Conta conta, Double valor) throws Exception {
		if (valor > 0) {
			conta = selectById(conta.getNumero());

			conta.depositar(valor);
			System.out.println(conta);

			TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :depositoProcessado WHERE numero = :numero", Conta.class)
			.setParameter("depositoProcessado", conta.getSaldo())
			.setParameter("numero", conta.getNumero());
			query.executeUpdate();

			adicionarMovimentacao(conta, valor, TipoMovimentacao.Deposito);

			System.out.println("Deposito realizado com sucesso");
		} else {
			throw  new Exception("Valor de deposito negativo");
		}		
	}

	public void transferir(Conta contaOrigem, double valor, Integer idContaDestino) throws Exception {
		if (contaOrigem.getNumero() != idContaDestino) {
			if (valor > 0) {
				contaOrigem = selectById(contaOrigem.getNumero());
				
				Conta cD = this.selectById(idContaDestino);

				contaOrigem.retirarSaldo(valor);
				cD.depositar(valor);

				System.out.println("Saldo do destiono carai " + cD.getSaldo());

				TypedQuery<Conta> query = em.createQuery("UPDATE Conta SET saldo = :depositoProcessado WHERE numero = :numero", Conta.class);

				query.setParameter("depositoProcessado", contaOrigem.getSaldo());
				query.setParameter("numero", contaOrigem.getNumero());
				query.executeUpdate();

				TypedQuery<Conta> query2 = em.createQuery("UPDATE Conta SET saldo = :depositoProcessado WHERE numero = :numero", Conta.class);

				query2.setParameter("depositoProcessado", cD.getSaldo());
				query2.setParameter("numero", cD.getNumero());
				query2.executeUpdate();

				adicionarMovimentacao(contaOrigem, cD, valor, TipoMovimentacao.Transferencia);
			} else {
				System.out.println("Valor não é maior que 0");
			}
		} else {
			System.out.println("Nao pode transferir para si mesmo");
		}
	}
}
