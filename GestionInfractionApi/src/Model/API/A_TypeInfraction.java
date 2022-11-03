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

import Model.BEANS.C_TypeInfraction;
import Model.BEANS.Global;

@Path("TypeInfraction")
public class A_TypeInfraction {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerTypeInfraction(
			@FormParam("libelle") String libelle,
			@FormParam("montant") String strMontant,
			@FormParam("description") String description,			
			@FormParam("uCrea") String struCrea){
		C_TypeInfraction infra = new C_TypeInfraction();
		double montant = Global.tryParseDouble(strMontant);
		int uCrea = Global.tryParseInt(struCrea);
		
		infra.setLibelle(libelle);
		infra.setDescription(description);
		infra.setMontant(montant);
		infra.setuCrea(uCrea);
								
		if(infra.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(infra).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierTypeInfraction(
			@FormParam("id") String strId,
			@FormParam("libelle") String libelle,
			@FormParam("montant") String strMontant,
			@FormParam("description") String description,
			@FormParam("uSupp") String struSupp) {
		C_TypeInfraction infra = new C_TypeInfraction();
		int id = Global.tryParseInt(strId);
		double montant = Global.tryParseDouble(strMontant);
		int uSupp = Global.tryParseInt(struSupp);
		
		infra.setId(id);
		infra.setLibelle(libelle);
		infra.setDescription(description);
		infra.setMontant(montant);
		infra.setuSupp(uSupp);
		
		if(infra.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerTypeInfraction(@PathParam("id")int id, 
			@FormParam("uSupp") String struSupp) {
		C_TypeInfraction infra = new C_TypeInfraction();
		int uSupp = Global.tryParseInt(struSupp);
		
		infra.setId(id);
		infra.setuSupp(uSupp);
		
		if(infra.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverTypeInfraction(@PathParam("id")int id) {
		C_TypeInfraction infra = new C_TypeInfraction();
		infra = infra.trouver(id);
		
		if(infra != null)
			return Response.status(Response.Status.OK).entity(infra).build();
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerTypeInfraction() {
		return Response.status(Response.Status.OK).entity(C_TypeInfraction.lister()).build();
	}
}
