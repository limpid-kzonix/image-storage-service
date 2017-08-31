package com.omnie.model.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by limpid on 5/1/17.
 */

@Entity
@Data
@Table( name = "images", schema = "play-db@mongoUnitDevServer")
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {

	@Id
	@Column( name = "image_id" )
	@GeneratedValue( strategy = GenerationType.AUTO )
	private String imageId;

	@Column( name = "name" )
	private String name;

	@OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private List<ImageSource> imageSources;



}
