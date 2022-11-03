package Model.BEANS;

import java.util.List;

public class C_Administrateur extends C_Utilisateur{
	
	private static final long serialVersionUID = 1L;
	
	// Attributs
	
	// Constructeurs
	public C_Administrateur() {
		super();
	}
	
	public C_Administrateur(String mdp, String nom, String prenom, String email, int role, String brigade) {
		super(mdp, nom, prenom, email, role, brigade);
	}
	
	public C_Administrateur(String mdp, String nom, String prenom, String email, String brigade) {
		super(mdp, nom, prenom, email, brigade);
	}
	
	// GET - SET
	
	// Methode
	@Override
	public boolean creer() {
		return Global.getFactory().getAdministrateurDAO().creer(this);
	}

	@Override
	public boolean supprimer() {
		return super.supprimer();
	}

	@Override
	public boolean modifier() {
		return super.modifier();
	}
		
	@Override
	public C_Administrateur trouver(int id) {
		return Global.getFactory().getAdministrateurDAO().rechercher(id);	
	}

	public static List<C_Administrateur> lister() {
		return Global.getFactory().getAdministrateurDAO().lister();
	}	
}
