package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_TypeInfraction;
import oracle.jdbc.OracleTypes;

public class D_TypeInfraction extends DAO<C_TypeInfraction>{
	
	public D_TypeInfraction(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_TypeInfraction obj) {
		CallableStatement call = null;
		String sql = "{call P_TYPEINFRACTION.creer(?,?,?,?,?)}";
		try {
			call = connect.prepareCall(sql);
			call.setString(1, obj.getLibelle());
			call.setDouble(2, obj.getMontant());
			call.setString(3, obj.getDescription());
			call.setInt(4, obj.getuCrea());
			
			call.registerOutParameter(5, Types.INTEGER); 
			call.execute();
			
			obj.setId(call.getInt(5));			
			call.close();			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEINFRACTION creer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean modifier(C_TypeInfraction obj) {
		CallableStatement call = null;
		String sql = "{call P_TYPEINFRACTION.modifier(?,?,?,?,?,?)}";
		ResultSet resultSet = null;
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.setString(2, obj.getLibelle());
			call.setDouble(3, obj.getMontant());
			call.setString(4, obj.getDescription());
			call.setInt(5, obj.getuSupp());
			call.registerOutParameter(6, OracleTypes.CURSOR);
			call.executeUpdate();
			resultSet = (ResultSet) call.getObject(6);
						
			if(resultSet != null && resultSet.next()
				&& obj.getId() != resultSet.getInt(1)
				&& obj.getLibelle().equals(resultSet.getString(2))
				&& obj.getDescription().equals(resultSet.getString(3))
				&& obj.getMontant() == resultSet.getDouble(4)							
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
			System.out.println("Catch TYPEINFRACTION modifier " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean supprimer(C_TypeInfraction obj) {
		CallableStatement call = null;
		ResultSet resultSet = null;
		String sql = "{call P_TYPEINFRACTION.supprimer(?,?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.setInt(2, obj.getuSupp());
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.executeUpdate();			
			resultSet = (ResultSet) call.getObject(3);
			
			if(resultSet != null && resultSet.next()) {
				obj.setId(resultSet.getInt(1));
				obj.setLibelle(resultSet.getString(2));
				obj.setDescription(resultSet.getString(3));
				obj.setMontant(resultSet.getDouble(4));								
			}
			
			resultSet.close();
			call.close();
			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEINFRACTION supprimer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public C_TypeInfraction rechercher(int id) {
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_TypeInfraction infra = new C_TypeInfraction();
		String sql = "{call P_TYPEINFRACTION.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				infra.setId(resultSet.getInt(1));
				infra.setLibelle(resultSet.getString(2));
				infra.setDescription(resultSet.getString(3));
				infra.setMontant(resultSet.getDouble(4));	
				infra.setdDeb(resultSet.getDate(5));
				infra.setdFin(resultSet.getDate(6));
				infra.setuCrea(resultSet.getInt(7));
				infra.setuSupp(resultSet.getInt(8));
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEINFRACTION rechercher " + e.getMessage());
			e.printStackTrace();
		}
		
		return infra;
	}

	@Override
	public List<C_TypeInfraction> lister() {
		CallableStatement call = null;
		ResultSet resultSet = null;
		List<C_TypeInfraction> liste = new LinkedList<>();
		C_TypeInfraction infra = null;
		String sql = "{call P_TYPEINFRACTION.lister(?)}";
		
		try {			
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					infra = new C_TypeInfraction();
					infra.setId(resultSet.getInt(1));
					infra.setLibelle(resultSet.getString(2));
					infra.setDescription(resultSet.getString(3));
					infra.setMontant(resultSet.getDouble(4));
					infra.setdDeb(resultSet.getDate(5));
					infra.setdFin(resultSet.getDate(6));
					infra.setuCrea(resultSet.getInt(7));
					infra.setuSupp(resultSet.getInt(8));
					liste.add(infra);
				}				
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch TYPEINFRACTION lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
}
