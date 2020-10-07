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

	private LineChartModel model;

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

	public LineChartModel getModel(Integer idContaOrigem) {
		this.model = new LineChartModel();

		model.setTitle("Valores de transações e seus tipos");
		model.setLegendPosition("e");
		model.setLegendPlacement(LegendPlacement.OUTSIDE);
		
		List<Movimentacao> movimentacoes = movimentacaoControllerAO.selectByContaOrigem(idContaOrigem);

		LineChartSeries depositosS = new LineChartSeries();
		LineChartSeries saqueS = new LineChartSeries();
		LineChartSeries transferenciaS = new LineChartSeries();

		depositosS.setLabel("Depósitos");
		saqueS.setLabel("Saque");
		transferenciaS.setLabel("Transferencia");
		
		//TODO ARRUMAR GRAFICO

		model.addSeries(saqueS);
		model.addSeries(depositosS);
		model.addSeries(transferenciaS);
		
		return model;
	}

	public void setModel(LineChartModel model) {
		this.model = model;
	}
}
