package Model.BEANS;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C_Utilisateur implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Attributs
	protected int id = 0;
	protected String matricule = "";
	protected String mdp = "";
	protected String nom ="";
	protected String prenom ="";
	protected String email = "";
	protected int role = 0;
	protected String brigade = "";
	protected int actif = 0;
	
	// Constructeurs
	public C_Utilisateur() {}
	
	public C_Utilisateur(String mdp, String nom, String prenom, String email, int role, String brigade) {
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
		this.brigade = brigade;
		this.actif = 1; // Par défaut compte actif
	}
	
	public C_Utilisateur(String mdp, String nom, String prenom, String email, String brigade) {
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.brigade = brigade;
		this.actif = 1; // Par défaut compte actif
	}
	
	public C_Utilisateur(int id, String matricule, String mdp, String nom, String prenom, String email, int role, String brigade) {
		this.id = id;
		this.matricule = matricule;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
		this.brigade = brigade;
		this.actif = 1; // Par défaut compte actif
	}

	// GET - SET
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	
	public String getBrigade() {
		return brigade;
	}
	
	public void setBrigade(String brigade) {
		this.brigade = brigade;
	}
	
	public int getActif() {
		return actif;
	}
	
	public void setActif(int actif) {
		this.actif = actif;
	}
	
	// Methodes
	public boolean creer() {
		this.email = this.email.toLowerCase();
		return Global.getFactory().getUtilisateurDAO().creer(this);
	}
		
	public boolean modifier() {
		return Global.getFactory().getUtilisateurDAO().modifier(this);
	}
		
	public boolean supprimer() {
		return Global.getFactory().getUtilisateurDAO().supprimer(this);
	}
		
	public C_Utilisateur trouver(int id) {
		return Global.getFactory().getUtilisateurDAO().rechercher(id);
	}
		
	public static List<? extends C_Utilisateur> lister(){
		return Global.getFactory().getUtilisateurDAO().lister();
	}
	
	@Override
    public String toString() {
        return this.matricule + " - " + this.nom + " - " + this.prenom + " - " + this.email
        		+ " - " + this.brigade + " - " + this.role + " - " + this.actif;
    }
}
