package com.omnie.model.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by limpid on 4/28/17.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "sources", schema = "play-db@mongoUnitDevServer")
public class ImageSource implements Serializable {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "source_id" )
	private String imageId;


	@Column( name = "width" )
	private int width;

	@Column( name = "height" )
	private int height;

	@Column( name = "type" )
	private String type;

	@Column( name = "extension" )
	private String extension;

	@Lob
	@Column( name = "source" )
	private byte[] imageSource;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	private Image image;




}
