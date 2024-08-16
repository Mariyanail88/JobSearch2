package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 70, message = "Age must be less than or equal to 70")
    private Integer age;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 24, message = "Length must be >= 4 and <= 24")
    // @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$", message = "Password should contain at least one uppercase letter, one number")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
   // @Pattern(regexp = "^\\+\\d{1} \\d{3} \\d{3}-\\d{2}-\\d{2}$", message = "Phone number must be in the format +X XXX XXX-XX-XX")
    private String phoneNumber;

    private String avatar;

    private String accountType;

    private boolean enabled;
}