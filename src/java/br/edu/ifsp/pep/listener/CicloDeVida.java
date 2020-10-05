/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.listener;

import br.edu.ifsp.pep.controllers.UsuarioController;
import java.io.IOException;
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

			System.out.println(request.getServletPath());

			if(request.getServletPath().contains("conta") && usuarioController.getContaAutenticada() == null) {
				System.out.println("Redirecionando");
				
				try {
					event.getFacesContext().getExternalContext().redirect("../index.xhtml");
				} catch(IOException ex) {
					System.out.println("Erro ao redirecionar o usuário");
				}
			}
		}
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("Depois da fase -----------------");
	}
	
	
}
