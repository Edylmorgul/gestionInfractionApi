package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_TypeVehicule;
import oracle.jdbc.OracleTypes;

public class D_TypeVehicule extends DAO<C_TypeVehicule>{
	
	public D_TypeVehicule(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_TypeVehicule obj) {
		CallableStatement call = null;
		String sql = "{call P_TYPEVEHICULE.creer(?,?,?)}";
		try {
			call = connect.prepareCall(sql);
			call.setString(1, obj.getLibelle());
			call.setString(2, obj.getDescription());
			call.registerOutParameter(3, Types.INTEGER); 
			call.execute();
			
			obj.setId(call.getInt(3));			
			call.close();			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEVEHICULE creer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean modifier(C_TypeVehicule obj) {
		CallableStatement call = null;
		String sql = "{call P_TYPEVEHICULE.modifier(?,?,?,?)}";
		ResultSet resultSet = null;
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.setString(2, obj.getLibelle());
			call.setString(3, obj.getDescription());
			call.registerOutParameter(4, OracleTypes.CURSOR);
			call.executeUpdate();
			resultSet = (ResultSet) call.getObject(4);
						
			if(resultSet != null && resultSet.next()
				&& obj.getId() == resultSet.getInt(1)
				&& obj.getLibelle().equals(resultSet.getString(2))
				&& obj.getDescription().equals(resultSet.getString(3))
				) {
				resultSet.close();
				call.close();
				return true;
			}
			
			resultSet.close();
			call.close();
			return false;
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEVEHICULE modifier " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean supprimer(C_TypeVehicule obj) {
		CallableStatement call = null;
		ResultSet resultSet = null;
		String sql = "{call P_TYPEVEHICULE.supprimer(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeUpdate();			
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				obj.setId(resultSet.getInt(1));
				obj.setLibelle(resultSet.getString(2));
				obj.setDescription(resultSet.getString(3));
			}
			
			resultSet.close();
			call.close();
			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEVEHICULE supprimer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public C_TypeVehicule rechercher(int id) {
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_TypeVehicule vehi = new C_TypeVehicule();
		String sql = "{call P_TYPEVEHICULE.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				vehi.setId(resultSet.getInt(1));
				vehi.setLibelle(resultSet.getString(2));
				vehi.setDescription(resultSet.getString(3));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEVEHICULE rechercher " + e.getMessage());
			e.printStackTrace();
		}
		
		return vehi;
	}

	@Override
	public List<C_TypeVehicule> lister() {
		CallableStatement call = null;
		ResultSet resultSet = null;
		List<C_TypeVehicule> liste = new LinkedList<>();
		C_TypeVehicule vehi = null;
		String sql = "{call P_TYPEVEHICULE.lister(?)}";
		
		try {			
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					vehi = new C_TypeVehicule();
					vehi.setId(resultSet.getInt(1));
					vehi.setLibelle(resultSet.getString(2));
					vehi.setDescription(resultSet.getString(3));
					liste.add(vehi);
				}				
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEVEHICULE lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
