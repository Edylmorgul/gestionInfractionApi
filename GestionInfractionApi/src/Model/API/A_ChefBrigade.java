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

import Model.BEANS.C_ChefBrigade;
import Model.BEANS.Global;

@Path("ChefBrigade")
public class A_ChefBrigade {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerChefBrigade(
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole){
		C_ChefBrigade chef = new C_ChefBrigade();
		int role = Global.tryParseInt(strRole);
		
		chef.setMdp(mdp);
		chef.setNom(nom);
		chef.setPrenom(prenom);
		chef.setEmail(email);
		chef.setRole(role);
				
		if(chef.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(chef).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierChefBrigade(
			@FormParam("id") String strId,
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade,
			@FormParam("actif") String strActif) {
		C_ChefBrigade chef = new C_ChefBrigade();
		int id = Global.tryParseInt(strId);
		int role = Global.tryParseInt(strRole);
		int actif = Global.tryParseInt(strActif);
		
		chef.setId(id);
		chef.setMdp(mdp);
		chef.setNom(nom);
		chef.setPrenom(prenom);
		chef.setEmail(email);
		chef.setRole(role);
		chef.setBrigade(brigade);
		chef.setActif(actif);
		
		if(chef.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerChefBrigade(@PathParam("id")int id) {
		C_ChefBrigade chef = new C_ChefBrigade();
		chef.setId(id);
		
		if(chef.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverChefBrigade(@PathParam("id")int id) {
		C_ChefBrigade chef = new C_ChefBrigade();
		chef = chef.trouver(id);
		
		if(chef != null) {
			if(chef.getRole() == 2)
				return Response.status(Response.Status.OK).entity(chef).build();
			else
				return Response.status(Response.Status.NO_CONTENT).entity(null).build();
		}

		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerChefBrigade() {
		return Response.status(Response.Status.OK).entity(C_ChefBrigade.lister()).build();
	}
}
