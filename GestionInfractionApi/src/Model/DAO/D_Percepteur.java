package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_Percepteur;
import Model.BEANS.Global;
import oracle.jdbc.OracleTypes;

public class D_Percepteur extends DAO<C_Percepteur>{
	
	public D_Percepteur(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_Percepteur obj) {
		return Global.getFactory().getUtilisateurDAO().creer(obj);
	}

	@Override
	public boolean modifier(C_Percepteur obj) {		
		return Global.getFactory().getUtilisateurDAO().modifier(obj);
	}

	@Override
	public boolean supprimer(C_Percepteur obj) {		
		return Global.getFactory().getUtilisateurDAO().supprimer(obj);
	}

	@Override
	public C_Percepteur rechercher(int id) {		
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_Percepteur percept = null;
		String sql = "{call P_UTILISATEUR.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				percept = new C_Percepteur();
				percept.setId(resultSet.getInt(1));
				percept.setMatricule(resultSet.getString(2));
				percept.setMdp(resultSet.getString(3));
				percept.setNom(resultSet.getString(4));
				percept.setPrenom(resultSet.getString(5));				
				percept.setEmail(resultSet.getString(6));
				percept.setRole(resultSet.getInt(7));
				percept.setBrigade(resultSet.getString(8));
				percept.setActif(resultSet.getInt(9));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch PERCEPTEUR rechercher " + e.getMessage());
			e.printStackTrace();
		}
				
		return percept;
	}

	@Override
	public List<C_Percepteur> lister() {		
		CallableStatement call = null;
		List<C_Percepteur> liste = new LinkedList<>();
		ResultSet resultSet = null;
		C_Percepteur percept = null;
		String sql = "{call P_UTILISATEUR.lister(?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					percept = new C_Percepteur();
					percept.setId(resultSet.getInt(1));
					percept.setMatricule(resultSet.getString(2));
					percept.setMdp(resultSet.getString(3));
					percept.setNom(resultSet.getString(4));
					percept.setPrenom(resultSet.getString(5));				
					percept.setEmail(resultSet.getString(6));
					percept.setRole(resultSet.getInt(7));
					percept.setBrigade(resultSet.getString(8));
					percept.setActif(resultSet.getInt(9));
					if(percept.getRole() == 3)
						liste.add(percept);					
				}
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch PERCEPTEUR lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
