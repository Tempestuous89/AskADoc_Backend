package com.Medical.dao.requests;

import com.Medical.security.role.Role;
import com.Medical.security.user.Gender;
import com.Medical.dao.entities.Certificate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorUpdateDataRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @NotEmpty(message = "Roles are mandatory")
    private List<Role> roles;

    private boolean accountLocked;
    private boolean enabled;

    @NotNull(message = "Created date is mandatory")
    private LocalDate createdDate;

    @NotNull(message = "Last modified date is mandatory")
    private LocalDate lastModifiedDate;

    @NotBlank(message = "Speciality is mandatory")
    private String speciality;

    @NotBlank(message = "Education is mandatory")
    private String education;

    @NotBlank(message = "Work place is mandatory")
    private String workPlace;

    @NotBlank(message = "Position is mandatory")
    private String position;

    @Min(value = 0, message = "Work experience years must be non-negative")
    private int workExperienceYears;

    private String awards;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String contactPhone;

    @Email(message = "Contact email should be valid")
    private String contactEmail;

    private String aboutMe;
    private String specializationDetails;
    private String workExperienceDetails;
    private String furtherTraining;
    private String achievementsAndAwards;
    private String scientificWorks;

    private byte[] profileImage;

    private List<Certificate> certificates;
}
