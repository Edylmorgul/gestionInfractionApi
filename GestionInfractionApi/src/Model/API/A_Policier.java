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

import Model.BEANS.C_Policier;
import Model.BEANS.Global;

@Path("Policier")
public class A_Policier {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerPolicier(
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade){		
		C_Policier pol = new C_Policier();
		int role = Global.tryParseInt(strRole);
		
		pol.setMdp(mdp);
		pol.setNom(nom);
		pol.setPrenom(prenom);
		pol.setEmail(email);
		pol.setRole(role);
		pol.setBrigade(brigade);
				
		if(pol.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(pol).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierPolicier(
			@FormParam("id") String strId,
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade,
			@FormParam("actif") String strActif) {
		C_Policier pol = new C_Policier();
		int id = Global.tryParseInt(strId);
		int role = Global.tryParseInt(strRole);
		int actif = Global.tryParseInt(strActif);
		
		pol.setId(id);
		pol.setMdp(mdp);
		pol.setNom(nom);
		pol.setPrenom(prenom);
		pol.setEmail(email);
		pol.setRole(role);
		pol.setBrigade(brigade);
		pol.setActif(actif);
		
		if(pol.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerPolicier(@PathParam("id")int id) {
		C_Policier pol = new C_Policier();
		pol.setId(id);
		
		if(pol.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverPolicier(@PathParam("id")int id) {
		C_Policier pol = new C_Policier();
		pol = pol.trouver(id);
		
		if(pol != null) {
			// Afin d'éviter que la recherche sorte autre chose qu'un policier
			if(pol.getRole() == 1)
				return Response.status(Response.Status.OK).entity(pol).build();
			else
				return Response.status(Response.Status.NO_CONTENT).entity(null).build();
		}
			
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerPolicier() {
		return Response.status(Response.Status.OK).entity(C_Policier.lister()).build();
	}
}
