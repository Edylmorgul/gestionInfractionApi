package Model.BEANS;

import java.util.LinkedList;
import java.util.List;

public class C_ChefBrigade extends C_Utilisateur{
	
	private static final long serialVersionUID = 1L;
	
	// Attributs
	List<C_Policier> listePolicier = null;
	
	// Constructeurs
	public C_ChefBrigade() {
		super();
		this.listePolicier = new LinkedList<>();
	}
	
	public C_ChefBrigade(String mdp, String nom, String prenom, String email, int role, String brigade) {
		super(mdp, nom, prenom, email, role, brigade);
		this.listePolicier = new LinkedList<>();
	}
	
	public C_ChefBrigade(String mdp, String nom, String prenom, String email, String brigade) {
		super(mdp, nom, prenom, email, brigade);
		this.listePolicier = new LinkedList<>();
	}
	
	// GET - SET
	public List<C_Policier> getListePol(){
		return listePolicier;
	}
	
	public void setListePol(List<C_Policier> listePol) {
		this.listePolicier = listePol;
	}
	
	// Methodes
	@Override
	public boolean creer() {
		return Global.getFactory().getChefBrigadeDAO().creer(this);
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
	public C_ChefBrigade trouver(int id) {
		return Global.getFactory().getChefBrigadeDAO().rechercher(id);	
	}

	public static List<C_ChefBrigade> lister() {
		return Global.getFactory().getChefBrigadeDAO().lister();
	}
}
