package Model.BEANS;

import java.util.List;

public class C_Percepteur extends C_Utilisateur{
	
	private static final long serialVersionUID = 1L;
	
	// Attributs
	
	// Constructeurs
	public C_Percepteur() {
		super();
	}
	
	public C_Percepteur(String mdp, String nom, String prenom, String email, int role, String brigade) {
		super(mdp, nom, prenom, email, role, brigade);
	}
	
	public C_Percepteur(String mdp, String nom, String prenom, String email, String brigade) {
		super(mdp, nom, prenom, email, brigade);
	}
	
	// GET - SET
	
	// Methodes
	@Override
	public boolean creer() {
		return Global.getFactory().getPercepteurDAO().creer(this);
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
	public C_Percepteur trouver(int id) {
		return Global.getFactory().getPercepteurDAO().rechercher(id);	
	}

	public static List<C_Percepteur> lister() {
		return Global.getFactory().getPercepteurDAO().lister();
	}	
}
