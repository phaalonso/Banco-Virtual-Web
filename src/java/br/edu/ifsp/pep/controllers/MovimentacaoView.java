/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.MovimentacaoDAO;
import br.edu.ifsp.pep.model.Movimentacao;
import br.edu.ifsp.pep.model.TipoMovimentacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author pedro
 */
@Named("movimentacaoView")
@RequestScoped
public class MovimentacaoView implements Serializable {
	@EJB
	private	MovimentacaoDAO movimentacaoControllerAO;

	private BarChartModel model;

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

public BarChartModel getModel(Integer idContaOrigem) {
		this.model = new BarChartModel();

		model.setTitle("Montante de movimentações por operação");
		model.setLegendPosition("e");
		model.setLegendPlacement(LegendPlacement.OUTSIDE);
		
		List<Movimentacao> movimentacoes = movimentacaoControllerAO.selectByContaOrigem(idContaOrigem);

		BarChartSeries depositosS = new BarChartSeries();
		BarChartSeries saqueS = new BarChartSeries();
		BarChartSeries transferenciaS = new BarChartSeries();

		depositosS.setLabel("Depósito");
		saqueS.setLabel("Saque");
		transferenciaS.setLabel("Transferencia");
		
		//TODO ARRUMAR GRAFICO
		int deposito = 0;
		int saque = 0;
		int transferencia = 0;
		for(Movimentacao mv: movimentacoes) {
			switch(mv.getTipo()) {
				case Deposito:
					depositosS.set(++deposito, mv.getValor());
					break;
				case Saque:
					saqueS.set(++saque, mv.getValor());
					break;
				case Transferencia:
					transferenciaS.set(++transferencia, mv.getValor());
					break;
			}
		}

		model.addSeries(saqueS);
		model.addSeries(depositosS);
		model.addSeries(transferenciaS);
		
		return model;
	}
	public void setModel(BarChartModel model) {
		this.model = model;
	}
}
