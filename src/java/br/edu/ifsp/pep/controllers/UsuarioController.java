/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.ContaDAO;
import br.edu.ifsp.pep.model.Conta;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author pedro
 */
@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	@EJB
	private ContaDAO contaDAO;
	
	private String nome;
	private String senha;
	
	private double valor;

	private Conta contaAutenticada;

	private Integer idContaDestino;
	
	public UsuarioController() {
		this.contaAutenticada = null;
		this.valor = 0d;
	}
	
	public void logar() {
		if (contaAutenticada == null) {
			if (nome.length() > 0 && senha.length() > 0) {
				contaAutenticada = contaDAO.selectByNomeAndSenha(nome, senha);

				if (contaAutenticada != null){
					System.out.println(contaAutenticada.getMovimentacoes().size());
					ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();

					try {
						ex.redirect("conta/conta.xhtml");
					} catch (IOException ex1) {
						System.out.println("Não foi possivel redirecionar para a conta");
					}
				} else {
					System.out.println("Usuario não encontrado");
				}

			} else {
				System.out.println("Nome e senha não podem ser menor que zero");
			}
		}
	}

	public void deslogar() {
		if (this.contaAutenticada != null) {
			this.contaAutenticada = null;

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			try {
				ec.redirect("../");
			} catch (IOException ex) {
				System.out.println("Não foi possivel redirecionar para login");
			}
		}
	}

	public void depositar() {
		if (this.valor > 0) {
			try {
				this.contaDAO.depoistar(contaAutenticada, valor);
				valor = 0d;
			} catch (Exception ex) {
				Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void sacar() {
		if (this.valor > 0) {
			try {
				this.contaDAO.sacar(contaAutenticada, this.valor);
				this.valor = 0d;
			} catch (Exception ex) {
				Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public void transferir() throws Exception {
		if (this.valor > 0) {
			this.contaDAO.transferir(contaAutenticada, valor, idContaDestino);
		}
	}

	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Conta getContaAutenticada() {
		return contaAutenticada;
	}

	public void setContaAutenticada(Conta contaAutenticada) {
		this.contaAutenticada = contaAutenticada;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getIdContaDestino() {
		return idContaDestino;
	}

	public void setIdContaDestino(Integer idContaDestino) {
		this.idContaDestino = idContaDestino;
	}
}
