/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.ContaDAO;
import br.edu.ifsp.pep.model.Admin;
import br.edu.ifsp.pep.model.ContaCorrente;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author pedro
 */
@Named(value = "ademirController")
@RequestScoped
public class AdemirController implements Serializable{
	
	@Inject
	private UsuarioController usuarioC;

	@EJB
	private ContaDAO db;

	private Admin conta;
	
	public AdemirController() {
		this.conta = new Admin();
	}

	public void cadastrarAdemir() {
		db.cadastrarAdemir(conta);
	}	
	
	public UsuarioController getUsuarioC() {
		return usuarioC;
	}

	public void setUsuarioC(UsuarioController usuarioC) {
		this.usuarioC = usuarioC;
	}

	public Admin getConta() {
		return conta;
	}

	public void setConta(Admin conta) {
		this.conta = conta;
	}

}
