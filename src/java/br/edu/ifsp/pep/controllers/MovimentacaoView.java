/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.MovimentacaoDAO;
import br.edu.ifsp.pep.model.Movimentacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author pedro
 */
@Named("movimentacaoView")
@ViewScoped
public class MovimentacaoView implements Serializable {
	@EJB
	private	MovimentacaoDAO movimentacaoControllerAO;

	private List<Movimentacao> itens;

	public List<Movimentacao> getItens(Integer codigo) {
		return movimentacaoControllerAO.selectByContaOrigem(codigo);
	}

	public MovimentacaoDAO getMovimentacaoControllerAO() {
		return movimentacaoControllerAO;
	}

	public void setMovimentacaoControllerAO(MovimentacaoDAO movimentacaoControllerAO) {
		this.movimentacaoControllerAO = movimentacaoControllerAO;
	}
}
