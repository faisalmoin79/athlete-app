package com.supersapiens.athlete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Check;
import org.springframework.lang.Nullable;

import com.sun.istack.NotNull;
import com.supersapiens.athlete.enums.SportTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
// lombok data annotation if you want to learn more, see below link
// https://javabydeveloper.com/lombok-data-annotation/#google_vignette
@Entity
@SequenceGenerator(name = "ATHLETE_ID_SEQ", sequenceName = "ATHLETE_ID_SEQ", initialValue = 5, allocationSize = 1)
public class Athlete {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQ")
	private Long id;

	// TODO: Fill in other athlete details+
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Check(constraints = "CHECK (primarySport IN ('CYCLING', 'RUNNING', 'SWIMMING'))")
	private SportTypeEnum primarySport;
	
	@Check(constraints = "CHECK (secondarySport IN ('CYCLING', 'RUNNING', 'SWIMMING'))")
	@Enumerated(EnumType.STRING)
	private SportTypeEnum secondarySport;
}
