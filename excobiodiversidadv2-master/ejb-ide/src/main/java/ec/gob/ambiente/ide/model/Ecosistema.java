package ec.gob.ambiente.ide.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;





/**
 * The persistent class for the ge000_ecosistema database table.
 * 
 */
@Entity
@Table(name="ge000_ecosistema_a", schema="g_biota")

public class Ecosistema implements Serializable {
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
	 * Codigo ecosistemas
	 */
	
	@Getter
	@Setter	
	@Column(name="fcodec")
	private String ceco;
	
	/**
	 * Nombre ecosistema
	 */
	@Getter
	@Setter	
	@Column(name="nameco")
	private String nam;
	
	
	
	
	
	  
	
}