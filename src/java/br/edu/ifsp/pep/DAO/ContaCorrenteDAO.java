/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.ContaCorrente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pedro
 */
@Stateless
public class ContaCorrenteDAO extends GenericoDAO<ContaCorrente> {

	public ContaCorrenteDAO() {
		super(ContaCorrente.class);
	}
	
}
