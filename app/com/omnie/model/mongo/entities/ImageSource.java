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
@Table()
public class ImageSource implements Serializable {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "image_id" )
	private String imageId;

	@Column( name = "image_name" )
	private String imageName;

	@Column( name = "width" )
	private int width;

	@Column( name = "height" )
	private int height;

	@Column( name = "type" )
	private String type;

	@Column( name = "extension" )
	private String extension;

	@Lob
	@Column( name = "picture_orginal" )
	private byte[] orginalImageSource;




}
