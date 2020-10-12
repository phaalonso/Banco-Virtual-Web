/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.ContaDAO;
import br.edu.ifsp.pep.model.Admin;
import br.edu.ifsp.pep.model.Conta;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
			contaAutenticada = contaDAO.selectByNomeAndSenha(nome, senha);

			if (nome.equals("admin") && senha.equals("admin") && contaAutenticada == null) {
				Admin adm = new Admin();
				adm.setNome("admin");
				adm.setSenha("admin");
				contaDAO.create(adm);
				contaAutenticada = adm;
			}
			
			if (contaAutenticada != null){
				System.out.println(contaAutenticada.getMovimentacoes().size());
				ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();

				try {
					if (contaAutenticada instanceof Admin)
						ex.redirect("adminTemplate.xhtml");
					else
						ex.redirect("conta/conta.xhtml");
				} catch (IOException ex1) {
					System.out.println("Não foi possivel redirecionar para a conta");
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuario nao encontrado", ""));
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
				// Estou tendo algum problema estranho de escopo, o saldo do objeto dentro do método é atualizado
				// no entando nesse escopo exterior ele permanece o mesmo
				contaAutenticada = contaDAO.find(contaAutenticada.getNumero());
				System.out.println(contaAutenticada);
				valor = 0d;
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor invalido", "Valor invalido"));
			}
		}
	}

	public void sacar() {
		if (this.valor > 0) {
			try {
				this.contaDAO.sacar(contaAutenticada, this.valor);
				contaAutenticada = contaDAO.find(contaAutenticada.getNumero());
				this.valor = 0d;
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor invalido", "Valor invalido"));
			}
		}
	}
	
	public void transferir() throws Exception {
		if (this.valor > 0) {
			this.contaDAO.transferir(contaAutenticada, valor, idContaDestino);
			contaAutenticada = contaDAO.find(contaAutenticada.getNumero());
			valor = 0d;
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
