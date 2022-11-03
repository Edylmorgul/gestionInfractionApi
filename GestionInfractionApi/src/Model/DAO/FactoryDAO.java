package Model.DAO;

import java.sql.Connection;

import Model.BEANS.*;

public class FactoryDAO {
	
	protected static final Connection conn = ConnectDatabase.getInstance();
	
	// Administrateur
	public DAO<C_Administrateur> getAdministrateurDAO(){
		return new D_Administrateur(conn);
	}
	
	// Amende
	public DAO<C_Amende> getAmendeDAO(){
		return new D_Amende(conn);
	}
	
	// Chef de brigade
	public DAO<C_ChefBrigade> getChefBrigadeDAO(){
		return new D_ChefBrigade(conn);
	}
	
	// Percepteur
	public DAO<C_Percepteur> getPercepteurDAO(){
		return new D_Percepteur(conn);
	}

	// Personne
	public DAO<C_Utilisateur> getUtilisateurDAO(){
		return new D_Utilisateur(conn);
	}
	
	// Policier
	public DAO<C_Policier> getPolicierDAO(){
		return new D_Policier(conn);
	}
	
	// Type infraction
	public DAO<C_TypeInfraction> getTypeInfractionDAO(){
		return new D_TypeInfraction(conn);
	}
	
	// Type vehicule
	public DAO<C_TypeVehicule> getTypeVehiculeDAO(){
		return new D_TypeVehicule(conn);
	}
}
