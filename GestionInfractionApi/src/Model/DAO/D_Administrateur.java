package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_Administrateur;
import Model.BEANS.Global;
import oracle.jdbc.OracleTypes;

public class D_Administrateur extends DAO<C_Administrateur>{
	
	public D_Administrateur(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_Administrateur obj) {
		return Global.getFactory().getUtilisateurDAO().creer(obj);
	}

	@Override
	public boolean modifier(C_Administrateur obj) {		
		return Global.getFactory().getUtilisateurDAO().modifier(obj);
	}

	@Override
	public boolean supprimer(C_Administrateur obj) {		
		return Global.getFactory().getUtilisateurDAO().supprimer(obj);
	}

	@Override
	public C_Administrateur rechercher(int id) {		
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_Administrateur admin = null;
		String sql = "{call P_UTILISATEUR.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				admin = new C_Administrateur();
				admin.setId(resultSet.getInt(1));
				admin.setMatricule(resultSet.getString(2));
				admin.setMdp(resultSet.getString(3));
				admin.setNom(resultSet.getString(4));
				admin.setPrenom(resultSet.getString(5));				
				admin.setEmail(resultSet.getString(6));
				admin.setRole(resultSet.getInt(7));
				admin.setBrigade(resultSet.getString(8));	
				admin.setActif(resultSet.getInt(9));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch ADMINISTRATEUR rechercher " + e.getMessage());
			e.printStackTrace();
		}
		
		return admin;
	}

	@Override
	public List<C_Administrateur> lister() {		
		CallableStatement call = null;
		List<C_Administrateur> liste = new LinkedList<>();
		ResultSet resultSet = null;
		C_Administrateur admin = null;
		String sql = "{call P_UTILISATEUR.lister(?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					admin = new C_Administrateur();
					admin.setId(resultSet.getInt(1));
					admin.setMatricule(resultSet.getString(2));
					admin.setMdp(resultSet.getString(3));
					admin.setNom(resultSet.getString(4));
					admin.setPrenom(resultSet.getString(5));				
					admin.setEmail(resultSet.getString(6));
					admin.setRole(resultSet.getInt(7));
					admin.setBrigade(resultSet.getString(8));
					admin.setActif(resultSet.getInt(9));
					if(admin.getRole() == 4)
						liste.add(admin);					
				}
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch ADMINISTRATEUR lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
