package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Category ID is mandatory")
    private Integer categoryId;

    @NotNull(message = "Salary is mandatory")
    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    private Integer salary;

    @NotNull(message = "Experience from is mandatory")
    @Min(value = 0, message = "Experience from must be greater than or equal to 0")
    private Integer expFrom;

    @NotNull(message = "Experience to is mandatory")
    @Min(value = 0, message = "Experience to must be greater than or equal to 0")
    private Integer expTo;

    @NotNull(message = "Is active is mandatory")
    private Boolean isActive;

    @NotNull(message = "Author ID is mandatory")
    private Integer authorId;

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDateTime createdDate;

    @PastOrPresent(message = "Update time must be in the past or present")
    private LocalDateTime updateTime;
}