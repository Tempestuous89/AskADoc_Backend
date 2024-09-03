package com.Medical.dao.requests;

import com.Medical.security.user.Gender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorUpdateDataRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Speciality is required")
    private String speciality;

    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Work place is required")
    private String workPlace;

    @NotBlank(message = "Position is required")
    private String position;

    @Min(value = 0, message = "Work experience years must be non-negative")
    @Max(value = 100, message = "Work experience years cannot exceed 100")
    private int workExperienceYears;

    private String awards;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String contactPhone;

    @Email(message = "Invalid contact email format")
    private String contactEmail;

    @Size(max = 1000, message = "About me section cannot exceed 1000 characters")
    private String aboutMe;

    @Size(max = 500, message = "Specialization details cannot exceed 500 characters")
    private String specializationDetails;

    @Size(max = 1000, message = "Work experience details cannot exceed 1000 characters")
    private String workExperienceDetails;

    @Size(max = 500, message = "Further training details cannot exceed 500 characters")
    private String furtherTraining;

    @Size(max = 500, message = "Achievements and awards cannot exceed 500 characters")
    private String achievementsAndAwards;

    @Size(max = 1000, message = "Scientific works description cannot exceed 1000 characters")
    private String scientificWorks;
    
    // private MultipartFile profileImage;

    // private List<MultipartFile> certificates;
}
