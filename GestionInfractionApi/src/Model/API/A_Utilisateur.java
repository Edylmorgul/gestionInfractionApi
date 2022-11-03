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

import Model.BEANS.C_Utilisateur;
import Model.BEANS.Global;

@Path("Utilisateur")
public class A_Utilisateur {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerUtilisateur(
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade){
		C_Utilisateur uti = new C_Utilisateur();
		int role = Global.tryParseInt(strRole);
		
		uti.setMdp(mdp);
		uti.setNom(nom);
		uti.setPrenom(prenom);
		uti.setEmail(email);
		uti.setRole(role);
		uti.setBrigade(brigade);
		
				
		if(uti.creer())
			return Response.status(Response.Status.CREATED).header("Location", uti.getId()).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(uti).build();				
	}	
	
	// Même problème que pour DELETE avec le no content ==> donc passage au OK
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierUtilisateur(
			@FormParam("id") String strId,
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade,
			@FormParam("actif") String strActif) {
		C_Utilisateur uti = new C_Utilisateur();
		int id = Global.tryParseInt(strId);
		int role = Global.tryParseInt(strRole);
		int actif = Global.tryParseInt(strActif);
		
		uti.setId(id);
		uti.setMdp(mdp);
		uti.setNom(nom);
		uti.setPrenom(prenom);
		uti.setEmail(email);
		uti.setRole(role);
		uti.setBrigade(brigade);
		uti.setActif(actif);
		
		if(uti.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	// Première version ==> No content ==> problème, génére une exception dans la reponse pour le client car contenu vide
	/*@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerUtilisateur(@PathParam("id")int id) {
		C_Utilisateur uti = new C_Utilisateur();
		uti.setId(id);
		
		if(uti.supprimer())
			return Response.status(Response.Status.NO_CONTENT).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}*/
	
	// Deuxième version ==> OK ==> Ne génére aucune exception car renvoi d'une reponse côté client
	// Les deux fonctionne, la version no content génére juste une exception côté client malgré la suppression effectuée sans soucis
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerUtilisateur(@PathParam("id")int id) {
		C_Utilisateur uti = new C_Utilisateur();
		uti.setId(id);
		
		if(uti.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverUtilisateur(@PathParam("id")int id) {
		C_Utilisateur uti = new C_Utilisateur();
		uti = uti.trouver(id);
		
		if(uti != null)
			return Response.status(Response.Status.OK).entity(uti).build();
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerUtilisateur() {
		return Response.status(Response.Status.OK).entity(C_Utilisateur.lister()).build();
	}
}
