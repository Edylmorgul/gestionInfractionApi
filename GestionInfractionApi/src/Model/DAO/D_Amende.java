package Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import Model.BEANS.C_Amende;
import Model.BEANS.C_Policier;
import Model.BEANS.C_TypeVehicule;
import Model.BEANS.Global;
import oracle.jdbc.OracleTypes;

public class D_Amende extends DAO<C_Amende>{
	
	public D_Amende(Connection conn) {
		super(conn);
	}

	@Override
	public boolean creer(C_Amende obj) {
		CallableStatement call = null;
		String strIdInfra = "";
		String sql = "{call P_AMENDE.creer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getTypeVehi().getId());
			call.setString(2, obj.getImm());
			call.setString(3, obj.getVille());
			call.setInt(4, obj.getPolicier().getId());
			call.setDouble(5, obj.getMontant());
			call.setString(6, obj.getCommentaire());
			call.setString(7, obj.getContrevenant().getNom());
			call.setString(8, obj.getContrevenant().getPrenom());
			call.setString(9, obj.getContrevenant().getPays());
			call.setString(10, obj.getContrevenant().getVille());
			call.setInt(11, obj.getContrevenant().getCodePostal());
			call.setString(12, obj.getContrevenant().getRue());
			call.setInt(13, obj.getContrevenant().getNumero());
			call.setString(14, obj.getContrevenant().getBoite());
			for(int i = 0; i<obj.getListeInfra().size() ; i++) {
				int id = obj.getListeInfra().get(i).getId();
				strIdInfra += String.valueOf(id) + ";";
			}
			strIdInfra = strIdInfra.substring(0, strIdInfra.length() - 1);
			// 15 liste infraction
			call.setString(15, strIdInfra);
			
			call.registerOutParameter(16, Types.INTEGER); 
			call.execute();
			
			obj.setId(call.getInt(16));			
			call.close();			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch AMENDE creer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean modifier(C_Amende obj) {
		CallableStatement call = null;
		String sql = "{call P_AMENDE.modifier(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		ResultSet resultSet = null;
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.setInt(2, obj.getPolicier().getId());
			call.setString(3, obj.getCommentaire());
			call.setString(4, obj.getContrevenant().getNom());
			call.setString(5, obj.getContrevenant().getPrenom());
			call.setString(6, obj.getContrevenant().getPays());
			call.setString(7, obj.getContrevenant().getVille());
			call.setInt(8, obj.getContrevenant().getCodePostal());
			call.setString(9, obj.getContrevenant().getRue());
			call.setInt(10, obj.getContrevenant().getNumero());
			call.setString(11, obj.getContrevenant().getBoite());			
			call.setInt(12, obj.getValide());
			call.setInt(13, obj.getEnvoye());
			call.registerOutParameter(14, OracleTypes.CURSOR);
			call.executeUpdate();
			resultSet = (ResultSet) call.getObject(14);
						
			if(resultSet != null && resultSet.next()
				&& obj.getId() == resultSet.getInt(1)
				&& obj.getPolicier().getId() == resultSet.getInt(6)			
				&& obj.getCommentaire().equals(resultSet.getString(8))
				&& obj.getValide() == resultSet.getInt(17)
				&& obj.getEnvoye() == resultSet.getInt(19)
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
			System.out.println("Catch AMENDE modifier " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean supprimer(C_Amende obj) {
		CallableStatement call = null;
		C_TypeVehicule vehi = new C_TypeVehicule();
		C_Policier pol = new C_Policier();
		ResultSet resultSet = null;
		String sql = "{call P_AMENDE.supprimer(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, obj.getId());
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeUpdate();			
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				obj.setId(resultSet.getInt(1));
				vehi = Global.getFactory().getTypeVehiculeDAO().rechercher(resultSet.getInt(2));
				obj.setInstant(resultSet.getDate(4));
				obj.setVille(resultSet.getString(5));
				pol = Global.getFactory().getPolicierDAO().rechercher(resultSet.getInt(6));				
				obj.setMontant(resultSet.getDouble(7));
				obj.setCommentaire(resultSet.getString(8));
				obj.setValide(resultSet.getInt(17));
				obj.setEnvoye(resultSet.getInt(19));	
				obj.setTypeVehi(vehi);
				obj.setPolicier(pol);
			}
			
			resultSet.close();
			call.close();
			
			return true;
		}
		
		catch(SQLException e) {
			System.out.println("Catch AMENDE supprimer " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public C_Amende rechercher(int id) {
		CallableStatement call = null;
		ResultSet resultSet = null;
		C_TypeVehicule vehi = new C_TypeVehicule();
		C_Policier pol = new C_Policier();
		C_Amende amende = new C_Amende();
		C_Amende.C_Contrevenant con = null;
		String sql = "{call P_AMENDE.rechercher(?,?)}";
		
		try {
			call = connect.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(2);
			
			if(resultSet != null && resultSet.next()) {
				amende = new C_Amende();
				con = amende.new C_Contrevenant();
				vehi = new C_TypeVehicule();
				pol = new C_Policier();
				amende.setId(resultSet.getInt(1));					
				vehi = Global.getFactory().getTypeVehiculeDAO().rechercher(resultSet.getInt(2));
				amende.setImm(resultSet.getString(3));
				amende.setInstant(resultSet.getDate(4));
				amende.setVille(resultSet.getString(5));
				pol = Global.getFactory().getPolicierDAO().rechercher(resultSet.getInt(6));
				amende.setMontant(resultSet.getDouble(7));
				amende.setCommentaire(resultSet.getString(8));
				con.setNom(resultSet.getString(9));
				con.setPrenom(resultSet.getString(10));
				con.setPays(resultSet.getString(11));
				con.setVille(resultSet.getString(12));
				con.setCodePostal(resultSet.getInt(13));
				con.setRue(resultSet.getString(14));
				con.setNumero(resultSet.getInt(15));
				con.setBoite(resultSet.getString(16));
				amende.setValide(resultSet.getInt(17));
				amende.setdValide(resultSet.getDate(18));
				amende.setEnvoye(resultSet.getInt(19));		
				amende.setdEnvoye(resultSet.getDate(20));
				amende.setTypeVehi(vehi);
				amende.setPolicier(pol);
				amende.setContrevenant(con);
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch AMENDE rechercher " + e.getMessage());
			e.printStackTrace();
		}
		
		return amende;
	}

	@Override
	public List<C_Amende> lister() {
		CallableStatement call = null;
		ResultSet resultSet = null;
		List<C_Amende> liste = new LinkedList<>();
		C_Amende amende = null;
		C_TypeVehicule vehi = null;
		C_Policier pol = null;
		C_Amende.C_Contrevenant con = null;
		String sql = "{call P_AMENDE.lister(?)}";
		
		try {			
			call = connect.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.execute();
			resultSet = (ResultSet) call.getObject(1);
			
			if(resultSet != null) {
				while(resultSet.next()) {
					amende = new C_Amende();
					con = amende.new C_Contrevenant();
					vehi = new C_TypeVehicule();
					pol = new C_Policier();
					amende.setId(resultSet.getInt(1));					
					vehi = Global.getFactory().getTypeVehiculeDAO().rechercher(resultSet.getInt(2));
					amende.setImm(resultSet.getString(3));
					amende.setInstant(resultSet.getDate(4));
					amende.setVille(resultSet.getString(5));
					pol = Global.getFactory().getPolicierDAO().rechercher(resultSet.getInt(6));
					amende.setMontant(resultSet.getDouble(7));
					amende.setCommentaire(resultSet.getString(8));
					con.setNom(resultSet.getString(9));
					con.setPrenom(resultSet.getString(10));
					con.setPays(resultSet.getString(11));
					con.setVille(resultSet.getString(12));
					con.setCodePostal(resultSet.getInt(13));
					con.setRue(resultSet.getString(14));
					con.setNumero(resultSet.getInt(15));
					con.setBoite(resultSet.getString(16));
					amende.setValide(resultSet.getInt(17));
					amende.setdValide(resultSet.getDate(18));
					amende.setEnvoye(resultSet.getInt(19));		
					amende.setdEnvoye(resultSet.getDate(20));
					amende.setTypeVehi(vehi);
					amende.setPolicier(pol);
					amende.setContrevenant(con);
														
					liste.add(amende);
				}				
			}
			
			resultSet.close();
			call.close();
		}
		
		catch(SQLException e) {
			System.out.println("Catch AMENDE lister " + e.getMessage());
			e.printStackTrace();
		}
		
		return liste;
	}
	
	// Methode pour vérifier si assurance en ordre ou pas
	public boolean verifierAssurance(String immatriculation) {
		CallableStatement call = null;
		String sql = "{call P_AMENDE.verifierAssurabilite(?,?)}";
		int assurance = 0;
		
		try {
			call = connect.prepareCall(sql);
			call.setString(1, immatriculation);
			
			call.registerOutParameter(2, Types.INTEGER); 
			call.execute();
			
			assurance = call.getInt(2);	
			call.close();
			
			// Si assurée
			if(assurance == 1)
				return true;
			else
				return false;
		}
		
		catch(SQLException e) {
			System.out.println("Catch AMENDE vérifier assurance " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
}
