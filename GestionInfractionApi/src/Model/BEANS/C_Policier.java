package Model.BEANS;

import java.util.LinkedList;
import java.util.List;

public class C_Policier extends C_Utilisateur{
	
	private static final long serialVersionUID = 1L;
	
	// Attributs
	C_ChefBrigade chef = null;
	List<C_Amende> listeAmende = null;
	
	// Constructeurs
	public C_Policier() {
		super();
	}
	
	public C_Policier(String mdp, String nom, String prenom, String email, int role, String brigade) {
		super(mdp, nom, prenom, email, role, brigade);
		this.listeAmende = new LinkedList<>();
	}
	
	public C_Policier(String mdp, String nom, String prenom, String email, String brigade) {
		super(mdp, nom, prenom, email, brigade);
		this.listeAmende = new LinkedList<>();
	}
	
	public C_Policier(C_ChefBrigade chef, int id, String matricule, String mdp, String nom, String prenom, String email, int role) {
		super(id, matricule, mdp, nom, prenom, email, role, chef.getMatricule());
		this.chef = chef;
		this.listeAmende = new LinkedList<>();
	}
	
	// GET - SET
	C_ChefBrigade getChef() {
		return chef;
	}
	
	public void setChef(C_ChefBrigade chef) {
		this.chef = chef;
	}
	
	List<C_Amende> getListeAmende(){
		return listeAmende;
	}
	
	public void setListeAmende(List<C_Amende> listeAmende) {
		this.listeAmende = listeAmende;
	}
	
	// Methodes
	@Override
	public boolean creer() {
		return Global.getFactory().getPolicierDAO().creer(this);
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
	public C_Policier trouver(int id) {
		return Global.getFactory().getPolicierDAO().rechercher(id);	
	}

	public static List<C_Policier> lister() {
		return Global.getFactory().getPolicierDAO().lister();
	}
}
