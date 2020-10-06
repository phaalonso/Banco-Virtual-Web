/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.ContaDAO;
import br.edu.ifsp.pep.model.Conta;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
	
	private Double valor;

	private Conta contaAutenticada;

	public UsuarioController() {
		this.contaAutenticada = null;
		this.valor = 0d;
	}
	
	public void logar() {
		if (contaAutenticada == null) {
			if (nome.length() > 0 && senha.length() > 0) {
				contaAutenticada = contaDAO.selectByNomeAndSenha(nome, senha);
				System.out.println("Usuario logado!");
			} else {
				System.out.println("Nome e senha não podem ser menor que zero");
			}
		}
	}

	public void deslogar() {
		if (this.contaAutenticada != null) {
			this.contaAutenticada = null;
		}
	}

	public void depositar() {
		if (this.valor > 0) {
			this.contaAutenticada.depositar(valor);

			this.contaDAO.edit(contaAutenticada);
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
}
