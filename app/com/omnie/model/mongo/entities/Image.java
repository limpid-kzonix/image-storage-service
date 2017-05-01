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
@Table( name = "images", schema = "play-db@mongoUnit" )
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {

	@Id
	@Column( name = "image_id" )
	@GeneratedValue( strategy = GenerationType.AUTO )
	private String imageId;

	@Column( name = "name" )
	private String name;

	@ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinTable( name = "image_sources", joinColumns = {
			@JoinColumn( name = "image_id", nullable = false, updatable = false )
	}, inverseJoinColumns = {
			@JoinColumn( name = "source_id", nullable = false, updatable = false )
	} )
	private List<ImageSource> imageSources;



}