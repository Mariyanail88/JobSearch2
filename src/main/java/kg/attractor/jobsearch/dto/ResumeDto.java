package kg.attractor.jobsearch.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeDto {

    @NotNull(message = "Applicant ID cannot be null")
    Integer applicantId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    String name;

    @NotNull(message = "Category ID cannot be null")
    Integer categoryId;

    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    double salary;

    @NotNull(message = "Active status cannot be null")
    Boolean isActive;

    @NotNull(message = "Created date cannot be null")
    LocalDateTime createdDate;

    @NotNull(message = "Update time cannot be null")
    LocalDateTime updateTime;
}