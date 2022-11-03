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

import Model.BEANS.C_Percepteur;
import Model.BEANS.Global;

@Path("Percepteur")
public class A_Percepteur {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerPercepteur(
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole){
		C_Percepteur percept = new C_Percepteur();
		int role = Global.tryParseInt(strRole);
		
		percept.setMdp(mdp);
		percept.setNom(nom);
		percept.setPrenom(prenom);
		percept.setEmail(email);
		percept.setRole(role);
				
		if(percept.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(percept).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierPercepteur(
			@FormParam("id") String strId,
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade,
			@FormParam("actif") String strActif) {
		C_Percepteur percept = new C_Percepteur();
		int id = Global.tryParseInt(strId);
		int role = Global.tryParseInt(strRole);
		int actif = Global.tryParseInt(strActif);
		
		percept.setId(id);
		percept.setMdp(mdp);
		percept.setNom(nom);
		percept.setPrenom(prenom);
		percept.setEmail(email);
		percept.setRole(role);
		percept.setBrigade(brigade);
		percept.setActif(actif);
		
		if(percept.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerPercepteur(@PathParam("id")int id) {
		C_Percepteur percept = new C_Percepteur();
		percept.setId(id);
		
		if(percept.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverPercepteur(@PathParam("id")int id) {
		C_Percepteur percept = new C_Percepteur();
		percept = percept.trouver(id);
		
		if(percept != null) {
			if(percept.getRole() == 3)
				return Response.status(Response.Status.OK).entity(percept).build();
			else
				return Response.status(Response.Status.NO_CONTENT).entity(null).build();
		}
			
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerPercepteur() {
		return Response.status(Response.Status.OK).entity(C_Percepteur.lister()).build();
	}
}
