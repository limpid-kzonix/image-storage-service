package com.discoperi.model.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by limpid on 4/28/17.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image", schema = "play-db")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private String id;

	@Column(name = "name")
	private String name;
}
