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

import Model.BEANS.C_TypeVehicule;
import Model.BEANS.Global;

@Path("TypeVehicule")
public class A_TypeVehicule {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerTypeVehicule(
			@FormParam("libelle") String libelle,
			@FormParam("description") String description){
		C_TypeVehicule vehi = new C_TypeVehicule();
		
		vehi.setLibelle(libelle);
		vehi.setDescription(description);
				
		if(vehi.creer())
			return Response.status(Response.Status.CREATED).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(vehi).build();				
	}	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifierTypeVehicule(
			@FormParam("id") String strId,
			@FormParam("libelle") String libelle,
			@FormParam("description") String description) {
		C_TypeVehicule vehi = new C_TypeVehicule();
		int id = Global.tryParseInt(strId);
		
		vehi.setId(id);
		vehi.setLibelle(libelle);
		vehi.setDescription(description);
		
		if(vehi.modifier())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response supprimerTypeVehicule(@PathParam("id")int id) {
		C_TypeVehicule vehi = new C_TypeVehicule();
		vehi.setId(id);
		
		if(vehi.supprimer())
			return Response.status(Response.Status.OK).entity(true).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trouverTypeVehicule(@PathParam("id")int id) {
		C_TypeVehicule vehi = new C_TypeVehicule();
		vehi = vehi.trouver(id);
		
		if(vehi != null)
			return Response.status(Response.Status.OK).entity(vehi).build();
		else
			return Response.status(Response.Status.NO_CONTENT).entity(null).build();
	}
	
	@GET
	@Path("lister")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listerTypeVehicule() {
		return Response.status(Response.Status.OK).entity(C_TypeVehicule.lister()).build();
	}
}
