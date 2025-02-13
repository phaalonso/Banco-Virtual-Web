package br.edu.ifsp.pep.controllers;

import br.edu.ifsp.pep.DAO.MovimentacaoDAO;
import br.edu.ifsp.pep.model.Movimentacao;
import br.edu.ifsp.pep.controllers.util.JsfUtil;
import br.edu.ifsp.pep.controllers.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("movimentacaoController")
@SessionScoped
public class MovimentacaoController implements Serializable {

	@EJB
	private br.edu.ifsp.pep.DAO.MovimentacaoDAO ejbFacade;
	private List<Movimentacao> items = null;
	private Movimentacao selected;

	public MovimentacaoController() {
	}

	public Movimentacao getSelected() {
		return selected;
	}

	public void setSelected(Movimentacao selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private MovimentacaoDAO getFacade() {
		return ejbFacade;
	}

	public Movimentacao prepareCreate() {
		selected = new Movimentacao();
		initializeEmbeddableKey();
		return selected;
	}



	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovimentacaoCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovimentacaoUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MovimentacaoDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null;    // Invalidate list of items to trigger re-query.
		}
	}

	public List<Movimentacao> getItems() {
		if (items == null) {
			items = getFacade().findAll();
		}
		return items;
	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				if (persistAction != PersistAction.DELETE) {
					getFacade().edit(selected);
				} else {
					getFacade().remove(selected);
				}
				JsfUtil.addSuccessMessage(successMessage);
			} catch (EJBException ex) {
				String msg = "";
				Throwable cause = ex.getCause();
				if (cause != null) {
					msg = cause.getLocalizedMessage();
				}
				if (msg.length() > 0) {
					JsfUtil.addErrorMessage(msg);
				} else {
					JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	public Movimentacao getMovimentacao(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Movimentacao> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Movimentacao> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@FacesConverter(forClass = Movimentacao.class)
	public static class MovimentacaoControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MovimentacaoController controller = (MovimentacaoController) facesContext.getApplication().getELResolver().
					getValue(facesContext.getELContext(), null, "movimentacaoController");
			return controller.getMovimentacao(getKey(value));
		}

		java.lang.Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(java.lang.Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Movimentacao) {
				Movimentacao o = (Movimentacao) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Movimentacao.class.getName()});
				return null;
			}
		}

	}

}
