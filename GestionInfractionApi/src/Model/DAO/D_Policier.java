package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_Policier;
import Model.BEANS.Global;
import oracle.jdbc.OracleTypes;

public class D_Policier extends DAO<C_Policier>{
	
	public D_Policier(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_Policier obj) {
		return Global.getFactory().getUtilisateurDAO().creer(obj);
	}

	@Override
	public boolean modifier(C_Policier obj) {	
		return Global.getFactory().getUtilisateurDAO().modifier(obj);
	}

	@Override
	public boolean supprimer(C_Policier obj) {		
		return Global.getFactory().getUtilisateurDAO().supprimer(obj);
	}

	@Override
	public C_Policier rechercher(int id) {		
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_Policier pol = null;
		String sql = "{call P_UTILISATEUR.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				pol = new C_Policier();
				pol.setId(resultSet.getInt(1));
				pol.setMatricule(resultSet.getString(2));
				pol.setMdp(resultSet.getString(3));
				pol.setNom(resultSet.getString(4));
				pol.setPrenom(resultSet.getString(5));				
				pol.setEmail(resultSet.getString(6));
				pol.setRole(resultSet.getInt(7));
				pol.setBrigade(resultSet.getString(8));
				pol.setActif(resultSet.getInt(9));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch POLICIER rechercher " + e.getMessage());
			e.printStackTrace();
		}
				
		return pol;
	}

	@Override
	public List<C_Policier> lister() {		
		CallableStatement call = null;
		List<C_Policier> liste = new LinkedList<>();
		ResultSet resultSet = null;
		C_Policier pol = null;
		String sql = "{call P_UTILISATEUR.lister(?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					pol = new C_Policier();
					pol.setId(resultSet.getInt(1));
					pol.setMatricule(resultSet.getString(2));
					pol.setMdp(resultSet.getString(3));
					pol.setNom(resultSet.getString(4));
					pol.setPrenom(resultSet.getString(5));				
					pol.setEmail(resultSet.getString(6));
					pol.setRole(resultSet.getInt(7));
					pol.setBrigade(resultSet.getString(8));
					pol.setActif(resultSet.getInt(9));
					if(pol.getRole() == 1)
						liste.add(pol);				
				}
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch POLICIER lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
