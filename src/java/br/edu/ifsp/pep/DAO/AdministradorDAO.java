/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.DAO;

import br.edu.ifsp.pep.model.Admin;
import javax.ejb.Stateless;

/**
 *
 * @author pedro
 */
@Stateless
public class AdministradorDAO extends GenericoDAO<Admin> {

	public AdministradorDAO() {
		super(Admin.class);
	}

	public void cadastrar(Admin admin) {
		try {
			em.persist(admin);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}
