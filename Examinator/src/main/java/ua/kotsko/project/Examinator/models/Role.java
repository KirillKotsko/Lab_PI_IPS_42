package ua.kotsko.project.Examinator.models;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
}
