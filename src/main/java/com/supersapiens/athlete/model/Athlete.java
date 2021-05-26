package com.supersapiens.athlete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Check;

import static com.supersapiens.athlete.constant.AthleteAppConstants.ValidationMsg.FIRST_NAME_IS_REQUIRED;
import static com.supersapiens.athlete.constant.AthleteAppConstants.ValidationMsg.LAST_NAME_IS_REQUIRED;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.supersapiens.athlete.enums.SportTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
//@JacksonXmlRootElement(localName = "Athlete")
public class Athlete {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQ")
//	@JacksonXmlProperty
	private Long id;

	@NotBlank(message = FIRST_NAME_IS_REQUIRED)
//	@JacksonXmlProperty
 	@Column(nullable = false)
	private String firstName;
	
	@NotBlank(message = LAST_NAME_IS_REQUIRED)
	@Column(nullable = false)
//	@JacksonXmlProperty
	private String lastName;
	
//	@NotBlank(message = "primarySport is required.")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
//	@JacksonXmlProperty
	@Check(constraints = "CHECK (primarySport IN ('CYCLING', 'RUNNING', 'SWIMMING'))")
	private SportTypeEnum primarySport;
	
	@Check(constraints = "CHECK (secondarySport IN ('CYCLING', 'RUNNING', 'SWIMMING'))")
	@Enumerated(EnumType.STRING)
//	@JacksonXmlProperty
	private SportTypeEnum secondarySport;
}
