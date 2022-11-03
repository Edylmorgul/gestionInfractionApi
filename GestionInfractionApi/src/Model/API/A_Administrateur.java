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

import Model.BEANS.C_Administrateur;
import Model.BEANS.Global;

@Path("Administrateur")
public class A_Administrateur {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerAdministrateur(
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole){
		C_Administrateur admin = new C_Administrateur();
		int role = Global.tryParseInt(strRole);
		
		admin.setMdp(mdp);
		admin.setNom(nom);
		admin.setPrenom(prenom);
		admin.setEmail(email);
		admin.setRole(role);
				
		if(admin.creer())
			return Response.status(Response.Status.CREATED).header("Location", admin).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(admin).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierAdministrateur(
			@FormParam("id") String strId,
			@FormParam("mdp") String mdp,
			@FormParam("nom") String nom,
			@FormParam("prenom") String prenom,
			@FormParam("email") String email,
			@FormParam("role") String strRole,
			@FormParam("brigade") String brigade,
			@FormParam("actif") String strActif) {
		C_Administrateur admin = new C_Administrateur();
		int id = Global.tryParseInt(strId);
		int role = Global.tryParseInt(strRole);
		int actif = Global.tryParseInt(strActif);
		
		admin.setId(id);
		admin.setMdp(mdp);
		admin.setNom(nom);
		admin.setPrenom(prenom);
		admin.setEmail(email);
		admin.setRole(role);
		admin.setBrigade(brigade);
		admin.setActif(actif);
		
		if(admin.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerAdministrateur(@PathParam("id")int id) {
		C_Administrateur admin = new C_Administrateur();
		admin.setId(id);
		
		if(admin.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverAdministrateur(@PathParam("id")int id) {
		C_Administrateur admin = new C_Administrateur();
		admin = admin.trouver(id);
		
		if(admin != null) {
			if(admin.getRole() == 4)
				return Response.status(Response.Status.OK).entity(admin).build();
			else
				return Response.status(Response.Status.NO_CONTENT).entity(null).build();
		}
			
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerAdministrateur() {
		return Response.status(Response.Status.OK).entity(C_Administrateur.lister()).build();
	}
}
