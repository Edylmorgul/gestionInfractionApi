package Model.BEANS;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Model.DAO.ConnectDatabase;
import Model.DAO.D_Amende;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C_Amende implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Attributs
	private int id = 0;
	private C_TypeVehicule vehi = null;
	private C_Policier pol = null;
	private C_Amende.C_Contrevenant con = null; // Classe interne
	private String immatriculation = "";
	private Date instant = null; // Date de l'infraction
	private String ville = null; // Lieu de l'infraction
	private double montant = 0.0;
	private String commentaire = "";
	private int valide = 0;
	private Date dValide = null; // Date validation
	private int envoye = 0;
	private Date dEnvoye = null; // Date envoye
	private List<C_TypeInfraction> listeInfra = null;
	
	// Constructeurs
	public C_Amende() {
		this.listeInfra = new LinkedList<>();
	}
	
	public C_Amende(C_TypeVehicule vehi, C_Policier pol, String immatriculation, String pays, String ville, String commentaire) {
		this.vehi = vehi;
		this.pol = pol;
		this.immatriculation = immatriculation;
		this.ville = ville;
		this.commentaire = commentaire;
		this.listeInfra = new LinkedList<>();
	}
	
	public C_Amende(C_TypeVehicule vehi, C_Policier pol, C_Contrevenant con, String immatriculation, Date instant, String ville, Double montant, String commentaire, int valide, int envoye) {
		this.vehi = vehi;
		this.pol = pol;
		this.con = con;
		this.immatriculation = immatriculation;
		this.instant = instant;
		this.ville = ville;
		this.montant = montant;
		this.commentaire = commentaire;
		this.valide = valide;
		this.envoye = envoye;
		this.listeInfra = new LinkedList<>();
	}
	
	public C_Amende(C_TypeInfraction infra, C_TypeVehicule vehi, C_Policier pol, String immatriculation, String pays, String ville, double montant, String commentaire) {
		this.vehi = vehi;
		this.pol = pol;
		this.immatriculation = immatriculation;
		this.ville = ville;
		this.montant = montant;
		this.commentaire = commentaire;
		this.listeInfra = new LinkedList<>();
		listeInfra.add(infra);
	}

	// GET - SET
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public C_TypeVehicule getTypeVehi() {
		return vehi;
	}
	
	public void setTypeVehi(C_TypeVehicule vehi) {
		this.vehi = vehi;
	}
	
	public C_Policier getPolicier() {
		return pol;
	}
	
	public void setPolicier(C_Policier pol) {
		this.pol = pol;
	}
	
	public C_Contrevenant getContrevenant() {
		return con;
	}
	
	public void setContrevenant(C_Contrevenant con) {
		this.con = con;
	}
	
	public String getImm() {
		return immatriculation;
	}
	
	public void setImm(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	
	public Date getInstant() {
		return instant;
	}
	
	public void setInstant(Date instant) {
		this.instant = instant;
	}
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getValide() {
		return valide;
	}
	
	public void setValide(int valide) {
		this.valide = valide;
	}
	
	public int getEnvoye() {
		return envoye;
	}
	
	public void setEnvoye(int envoye) {
		this.envoye = envoye;
	}
	
	public Date getdValide() {
		return dValide;
	}

	public void setdValide(Date dValide) {
		this.dValide = dValide;
	}

	public Date getdEnvoye() {
		return dEnvoye;
	}

	public void setdEnvoye(Date dEnvoye) {
		this.dEnvoye = dEnvoye;
	}
	
	public List<C_TypeInfraction> getListeInfra(){
		return listeInfra;
	}
	
	public void setListTypeInfra(List<C_TypeInfraction> listeInfra) {
		this.listeInfra = listeInfra;
	}
	
	// Methodes
	public boolean creer() {
		return Global.getFactory().getAmendeDAO().creer(this);
	}
		
	public boolean modifier() {
		return Global.getFactory().getAmendeDAO().modifier(this);
	}
		
	public boolean supprimer() {
		return Global.getFactory().getAmendeDAO().supprimer(this);
	}
		
	public C_Amende trouver(int id) {
		return Global.getFactory().getAmendeDAO().rechercher(id);
	}
		
	public static List<C_Amende> lister(){
		return Global.getFactory().getAmendeDAO().lister();
	}
	
	public void ajouterInfraction(C_TypeInfraction infra) {
		listeInfra.add(new C_TypeInfraction(this, infra.getId(), infra.getLibelle(), infra.getDescription(), infra.getMontant()
				,infra.getdDeb(), infra.getdFin(), infra.getuCrea(), infra.getuSupp()));
	}
	
	public static boolean verifierAssurance(String immatriculation) {
		Connection conn = ConnectDatabase.getInstance();
		D_Amende dao = new D_Amende(conn);
		return dao.verifierAssurance(immatriculation);
	}
	
	@Override
    public String toString() {
        return this.immatriculation + " - " + this.instant + " - " + this.ville
        		+ " - " + this.montant + " - " + this.commentaire;
    }
	
	// Classe interne contrevenant ==> Composition
	public class C_Contrevenant{
		
		// Attributs
		private String prenom = "";
		private String nom = "";	
		private String pays = "";
		private String ville = "";
		private int codePostal = 0;
		private String rue = "";
		private int numero = 0;
		private String boite = "";
		
		// Constructeur
		public C_Contrevenant() {}
		
		public C_Contrevenant(String nom, String prenom,String pays, String ville, int codePostal, String rue, int numero, String boite) {
			this.nom = nom;
			this.prenom = prenom;
			this.pays = pays;
			this.ville = ville;
			this.codePostal = codePostal;
			this.rue = rue;
			this.numero = numero;
			this.boite = boite;			
		}
		
		// GET - SET		
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
		
		public String getPays() {
			return pays;
		}

		public void setPays(String pays) {
			this.pays = pays;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public int getCodePostal() {
			return codePostal;
		}

		public void setCodePostal(int codePostal) {
			this.codePostal = codePostal;
		}

		public String getRue() {
			return rue;
		}

		public void setRue(String rue) {
			this.rue = rue;
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public String getBoite() {
			return boite;
		}

		public void setBoite(String boite) {
			this.boite = boite;
		}
		
		@Override
	    public String toString() {
	        return this.nom + " - " + this.prenom + " - " + this.pays
	        		+ " - " + this.ville + " - " + this.codePostal + " - "
	        		+this.rue + " - " + this.numero + " - " + this.boite;
	    }
	}
}
