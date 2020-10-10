
import br.edu.ifsp.pep.DAO.AdministradorDAO;
import br.edu.ifsp.pep.model.Admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class InitDB {
	public static void main(String[] args) {
		Admin admin = new Admin();
		admin.setNome("admin");
		admin.setSenha("admin");

		AdministradorDAO controller = new AdministradorDAO();
		controller.cadastrar(admin);
	}
	
}
