package com.discoperi.model.mongo.entities;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.List;

/**
 * Created by limpid on 4/28/17.
 */
@Entity
@Data
@Table(name = "galleries", schema = "play-db@mongoUnit")
public class Gallery {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ObjectId id;

	@Column(name = "group")
	private String group;

	@Embedded
	@Column(name = "image_list")
	private List<Image> images;
}
