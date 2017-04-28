package com.discoperi.model.mongo.entities;

import org.bson.types.ObjectId;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Entity
@Table(name = "department", schema = "play-db@mongoUnit")
public class Department {
  @Id @GeneratedValue(strategy = AUTO)
  @Column(name = "id")
  private ObjectId id;
  @Column(name = "name")
  private String name;

}
