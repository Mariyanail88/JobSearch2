package main.java.kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {
    private Integer id;
    private Integer  resume_id;
    private Integer years;
    private String company_name;
    private String position;
    private String responsibilities;

}