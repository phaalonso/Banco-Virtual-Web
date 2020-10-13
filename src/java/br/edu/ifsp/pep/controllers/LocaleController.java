/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.pep.controllers;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author pedro
 */
@Named(value = "localeControllers")
@SessionScoped
public class LocaleController implements Serializable {

	private Locale locale = new Locale("pt", "BR");

	public LocaleController() {
	}

	public void englishLocale() {
		System.out.println("Locale US");
		this.setLocale(Locale.US);
	}

	public void portugueseLocale() {
		System.out.println("Locale portugues");
		this.setLocale(new Locale("pt", "BR"));
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

		if (viewRoot != null) {
			viewRoot.setLocale(this.locale);
		}
	}

}
