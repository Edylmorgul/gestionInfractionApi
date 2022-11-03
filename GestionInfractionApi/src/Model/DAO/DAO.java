package Model.DAO;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
	
	protected Connection connect = null;
	
	public DAO(Connection conn){
		this.connect = conn;
	}
	
    /**
     * Permet de créer une entrée dans la base de données
     * @param obj
     */
    public abstract boolean creer(T obj);
    
    /**
     * Permet de mettre à jour les données d'une entrée dans la base 
     * @param obj
     */ 
    public abstract boolean modifier(T obj);

    /**
     * Permet la suppression d'une entrée de la base
     * @param obj
     */  
    public abstract boolean supprimer(T obj);
    
    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */  
    public abstract T rechercher(int id);
  
    /**
     * Permet d'obtenir tous les objets
     * @return
     */   
    public abstract List<T> lister();
}
