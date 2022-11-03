package Model.BEANS;

import java.io.Serializable;
import java.util.List;

public class C_TypeVehicule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// Attributs
	private int id = 0;
	private String libelle = "";
	private String description = "";
	
	// Constructeurs
	public C_TypeVehicule() {}
	
	public C_TypeVehicule(String libelle,String description) {
		this.libelle = libelle;
		this.description = description;
	}

	// GET - SET
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	// Methodes
	public boolean creer() {
		return Global.getFactory().getTypeVehiculeDAO().creer(this);
	}
		
	public boolean modifier() {
		return Global.getFactory().getTypeVehiculeDAO().modifier(this);
	}
		
	public boolean supprimer() {
		return Global.getFactory().getTypeVehiculeDAO().supprimer(this);
	}
		
	public C_TypeVehicule trouver(int id) {
		return Global.getFactory().getTypeVehiculeDAO().rechercher(id);
	}
		
	public static List<C_TypeVehicule> lister(){
		return Global.getFactory().getTypeVehiculeDAO().lister();
	}
	
	@Override
    public String toString() {
        return this.libelle + " - " + this.description;
    }
}
