package Model.API;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.BEANS.C_Amende;
import Model.BEANS.C_Policier;
import Model.BEANS.C_TypeInfraction;
import Model.BEANS.C_TypeVehicule;
import Model.BEANS.Global;

@Path("Amende")
public class A_Amende {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerAmende(
			@FormParam("typeVehicule") String strIdTypeVehicule,
			@FormParam("immatriculation") String immatriculation,
			@FormParam("lieu") String lieu,
			@FormParam("montant") String strMontant,
			@FormParam("policier") String strIdPolicier,						
			@FormParam("commentaire") String commentaire,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("pays") String pays,
			@FormParam("ville") String ville,
			@FormParam("codePostal") String strCodePostal,
			@FormParam("rue") String rue,
			@FormParam("numero") String strNumero,
			@FormParam("boite") String boite,
			@FormParam("listeInfraction") String listeInfraction){
		C_Amende amende = new C_Amende();
		C_TypeVehicule vehi = new C_TypeVehicule();
		C_Policier pol = new C_Policier();
		C_TypeInfraction infra = null;
		int idTypeVehicule = Global.tryParseInt(strIdTypeVehicule);
		int idPolicier = Global.tryParseInt(strIdPolicier);
		double montant = Global.tryParseDouble(strMontant);
		int codePostal;
		int numero;
		if(strCodePostal == null || strCodePostal.equals(""))
			codePostal = 0;
		else
			codePostal = Global.tryParseInt(strCodePostal);
		
		if(strNumero == null || strNumero.equals(""))
			numero = 0;
		else
			numero = Global.tryParseInt(strNumero);
		
		vehi = vehi.trouver(idTypeVehicule);
		pol = pol.trouver(idPolicier);
		amende.setTypeVehi(vehi);
		amende.setImm(immatriculation);
		amende.setVille(lieu);
		amende.setMontant(montant);
		amende.setPolicier(pol);
		amende.setCommentaire(commentaire);
		C_Amende.C_Contrevenant con = amende.new C_Contrevenant(nom, prenom, pays, ville, codePostal, rue, numero, boite);
    	amende.setContrevenant(con);
		
		// Reception liste infraction
		String[] tabInfraction = listeInfraction.split(",");
    	for(int i = 0; i<tabInfraction.length; i++) {
    		tabInfraction[i] = tabInfraction[i].replace("[", "");
    		tabInfraction[i] = tabInfraction[i].replace("]", "");
    		tabInfraction[i] = tabInfraction[i].replace(" ", "");
    		// 4.4.1 Convertir string en int
    		int id = Global.tryParseInt(tabInfraction[i]);
    		
    		// 4.4.2 Rechercher l'infraction
    		infra = new C_TypeInfraction();
    		infra = infra.trouver(id);
    		
    		// 4.4.3 Ajouter dans liste infraction amende
    		amende.ajouterInfraction(infra);
    	}
				
		if(amende.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(amende).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierAmende(
			@FormParam("id") String strId,
			@FormParam("policier") String strIdPolicier,
			@FormParam("commentaire") String commentaire,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("pays") String pays,
			@FormParam("ville") String ville,
			@FormParam("codePostal") String strCodePostal,
			@FormParam("rue") String rue,
			@FormParam("numero") String strNumero,
			@FormParam("boite") String boite,
			@FormParam("valide") String strValide,
			@FormParam("envoye") String strEnvoye) {
		C_Amende amende = new C_Amende();
		C_Policier pol = new C_Policier();
		int id = Global.tryParseInt(strId);
		int idPolicier = Global.tryParseInt(strIdPolicier);
		int valide = Global.tryParseInt(strValide);
		int envoye = Global.tryParseInt(strEnvoye);
		int codePostal;
		int numero;
		if(strCodePostal == null || strCodePostal.equals(""))
			codePostal = 0;
		else
			codePostal = Global.tryParseInt(strCodePostal);
		
		if(strNumero == null || strNumero.equals(""))
			numero = 0;
		else
			numero = Global.tryParseInt(strNumero);
		
		pol = pol.trouver(idPolicier);
		amende.setId(id);		
		amende.setPolicier(pol);
		amende.setCommentaire(commentaire);
		amende.setValide(valide);
		amende.setEnvoye(envoye);
		C_Amende.C_Contrevenant con = amende.new C_Contrevenant(nom, prenom, pays, ville, codePostal, rue, numero, boite);
    	amende.setContrevenant(con);
		
		if(amende.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerAmende(@PathParam("id")int id) {
		C_Amende amende = new C_Amende();
		amende.setId(id);
		
		if(amende.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverAmende(@PathParam("id")int id) {
		C_Amende amende = new C_Amende();
		amende = amende.trouver(id);
		
		if(amende != null)
			return Response.status(Response.Status.OK).entity(amende).build();
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerAmende() {
		return Response.status(Response.Status.OK).entity(C_Amende.lister()).build();
	}
	
	@POST
	@Path("verifierAssurance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifierAssurance(
			@FormParam("immatriculation") String immatriculation) {
		
		return Response.status(Response.Status.OK).entity(C_Amende.verifierAssurance(immatriculation)).build();			
	}
}
