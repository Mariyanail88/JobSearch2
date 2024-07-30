package kg.attractor.jobsearch.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeDto {
    Integer applicantId;
    String name;
    Integer categoryId;
    double salary;
    Boolean isActive;
    LocalDateTime createdDate;
    LocalDateTime updateTime;
}