package ec.gob.ambiente.ide.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;





/**
 * The persistent class for the area protegida database table.
 * 
 */
@Entity
@Table(name="fa210_snap_a", schema="h_demarcacion")

public class AreaProtegida implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Clave primaria
	 */
	@Getter
	@Setter
	@Id	
	@Column(name="gid")
	private Integer id;
	
	/**
	 * Codigo snap
	 */
	
	@Getter
	@Setter	
	@Column(name="csnap")
	private String csnap;
	
	/**
	 * Nombre area
	 */
	@Getter
	@Setter	
	@Column(name="nam")
	private String nam;
	
	
	
	
	
	  
	
}