package com.discoperi.model.mongo.entities;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Data
@Entity
@Table( name = "employee", schema = "play-db@mongoUnit" )
public class Employee {

	@Id @GeneratedValue( strategy = AUTO )
	@Column( name = "id" )
	private ObjectId id;

	@Column( name = "name" )
	private String name;

	@Column( name = "age" )
	private Integer age;

	@Column( name = "sex" )
	private String sex;
}
