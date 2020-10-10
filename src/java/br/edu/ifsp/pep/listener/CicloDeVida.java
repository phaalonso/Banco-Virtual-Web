/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.listener;

import br.edu.ifsp.pep.controllers.UsuarioController;
import br.edu.ifsp.pep.model.Admin;
import br.edu.ifsp.pep.model.Conta;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pedro
 */
public class CicloDeVida implements PhaseListener {

	@Inject
	private UsuarioController usuarioController;

	@Override
	public void beforePhase(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
			HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();

			String path = request.getServletPath();
			System.out.println(path);
			ExternalContext externalContext = event.getFacesContext().getExternalContext();
			Conta usuarioLogado = usuarioController.getContaAutenticada();

			try {
				if (usuarioLogado != null) {
					if (usuarioLogado instanceof Admin) {
						if (path.contains("conta") || path.contains("index.xhtml"))
							externalContext.redirect("adminTemplate.xhtml");
					} else {
						if (path.contains("admin") || path.contains("index.xhtml")) {
							externalContext.redirect("conta/conta.xhtml");
						}
					}
				} else {
					// Mantem usuario não logados na pagina index
					if (!path.equals("/index.xhtml")) {
						externalContext.redirect("index.xhtml");
					}
				} 
			} catch (IOException ex) {
				System.out.println("Erro ao redirecionar para a pagina da conta");
			}
		}
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}
	
	
}
