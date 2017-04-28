package com.discoperi.model.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Created by limpid on 4/28/17.
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@Column(name = "image_name")
	private String imageName;
	@Column(name = "width")
	private String width;
	@Column(name = "height")
	private String height;
	@Column(name = "type")
	private String type;
	@Column(name = "extension")
	private String extension;
	@Lob
	private byte[] imageSource;

}
