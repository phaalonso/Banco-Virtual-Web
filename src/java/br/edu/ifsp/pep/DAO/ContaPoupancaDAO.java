/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.ContaPoupanca;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pedro
 */
@Stateless
public class ContaPoupancaDAO extends GenericoDAO<ContaPoupanca> {

	public ContaPoupancaDAO() {
		super(ContaPoupanca.class);
	}
	
}
