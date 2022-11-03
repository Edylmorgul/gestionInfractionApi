package Model.BEANS;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C_TypeInfraction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Attributs
	private int id = 0;
	private List<C_Amende> listeAmende = null;
	private String libelle = "";
	private String description = "";
	private double montant = 0.0;
	private Date dDeb = null;
	private Date dFin = null;
	private int uCrea = 0;
	private int uSupp = 0;
	
	// Constructeurs
	public C_TypeInfraction() {
		listeAmende = new LinkedList<>();
	}
	
	public C_TypeInfraction(String libelle, String description, Double montant) {
		this.montant = montant;
		this.libelle = libelle;
		this.description = description;
		listeAmende = new LinkedList<>();
	}
	
	public C_TypeInfraction(String libelle, String description, Double montant, int uCrea) {
		this.montant = montant;
		this.libelle = libelle;
		this.description = description;
		this.uCrea = uCrea;
		listeAmende = new LinkedList<>();
	}
	
	public C_TypeInfraction(C_Amende amende, int id,  String libelle, String description, Double montant
			,Date dDeb, Date dFin, int uCrea, int uSupp) {
		this.id = id;
		this.montant = montant;
		this.libelle = libelle;
		this.description = description;
		this.dDeb = dDeb;
		this.dFin = dFin;
		this.uCrea = uCrea;
		this.uSupp = uSupp;
		listeAmende = new LinkedList<>();
		listeAmende.add(amende);
	}

	// GET - SET
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getdDeb() {
		return dDeb;
	}

	public void setdDeb(Date dDeb) {
		this.dDeb = dDeb;
	}

	public Date getdFin() {
		return dFin;
	}

	public void setdFin(Date dFin) {
		this.dFin = dFin;
	}

	public int getuCrea() {
		return uCrea;
	}

	public void setuCrea(int uCrea) {
		this.uCrea = uCrea;
	}

	public int getuSupp() {
		return uSupp;
	}

	public void setuSupp(int uSupp) {
		this.uSupp = uSupp;
	}
	
	public List<C_Amende> getListeAmende(){
		return listeAmende;
	}
	
	public void setListeAmende(List<C_Amende> listeAmende) {
		this.listeAmende = listeAmende;
	}
	
	// Methodes
	public boolean creer() {
		return Global.getFactory().getTypeInfractionDAO().creer(this);
	}
		
	public boolean modifier() {
		return Global.getFactory().getTypeInfractionDAO().modifier(this);
	}
		
	public boolean supprimer() {
		return Global.getFactory().getTypeInfractionDAO().supprimer(this);
	}
		
	public C_TypeInfraction trouver(int id) {
		return Global.getFactory().getTypeInfractionDAO().rechercher(id);
	}
		
	public static List<C_TypeInfraction> lister(){
		return Global.getFactory().getTypeInfractionDAO().lister();
	}
	
	@Override
    public String toString() {
        return this.libelle + " - " + this.description + " - " + this.montant;
    }
}
