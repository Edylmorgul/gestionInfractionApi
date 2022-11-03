package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_ChefBrigade;
import Model.BEANS.Global;
import oracle.jdbc.OracleTypes;

public class D_ChefBrigade extends DAO<C_ChefBrigade>{
	
	public D_ChefBrigade(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_ChefBrigade obj) {
		return Global.getFactory().getUtilisateurDAO().creer(obj);
	}

	@Override
	public boolean modifier(C_ChefBrigade obj) {	
		return Global.getFactory().getUtilisateurDAO().modifier(obj);
	}

	@Override
	public boolean supprimer(C_ChefBrigade obj) {		
		return Global.getFactory().getUtilisateurDAO().supprimer(obj);
	}

	@Override
	public C_ChefBrigade rechercher(int id) {		
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_ChefBrigade chef = null;
		String sql = "{call P_UTILISATEUR.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				chef = new C_ChefBrigade();
				chef.setId(resultSet.getInt(1));
				chef.setMatricule(resultSet.getString(2));
				chef.setMdp(resultSet.getString(3));
				chef.setNom(resultSet.getString(4));
				chef.setPrenom(resultSet.getString(5));				
				chef.setEmail(resultSet.getString(6));
				chef.setRole(resultSet.getInt(7));
				chef.setBrigade(resultSet.getString(8));	
				chef.setActif(resultSet.getInt(9));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch CHEFBRIGADE rechercher " + e.getMessage());
			e.printStackTrace();
		}		
		
		return chef;
	}

	@Override
	public List<C_ChefBrigade> lister() {		
		CallableStatement call = null;
		List<C_ChefBrigade> liste = new LinkedList<>();
		ResultSet resultSet = null;
		C_ChefBrigade chef = null;
		String sql = "{call P_UTILISATEUR.lister(?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					chef = new C_ChefBrigade();
					chef.setId(resultSet.getInt(1));
					chef.setMatricule(resultSet.getString(2));
					chef.setMdp(resultSet.getString(3));
					chef.setNom(resultSet.getString(4));
					chef.setPrenom(resultSet.getString(5));				
					chef.setEmail(resultSet.getString(6));
					chef.setRole(resultSet.getInt(7));
					chef.setBrigade(resultSet.getString(8));
					chef.setActif(resultSet.getInt(9));
					if(chef.getRole() == 2)
						liste.add(chef);					
				}
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch CHEFBRIGADE lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
