package vivaha_vedika.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PROFILE")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="CODE_NO")
    private String codeNo;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="GENDER")
    private String gender;

    @Column(name="DOB")
    private String dob;

    @Column(name="BIRTH_YEAR")
    private String birthYear;

    @Column(name="QUALIFICATION")
    private String qualification;

    @Column(name="JOB")
    private String job;

    @Column(name="CASTE")
    private String caste;

    @Column(name="JOB_ADDRESS")
    private String jobAddress;

    @Column(name="SALARY")
    private Double salary;

    @Column(name="NATIVE_ADDRESS")
    private String nativeAddress;

    @Column(name="MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name="ALTERNATE_MOBILE_NUMBER")
    private String alternateMobileNumber;

    @Column(name="HEIGHT")
    private String height;

    @Column(name="IMAGE_URL")
    private String imageUrl;

    @Column(name="STAR")
    private String star;

    @Column(name="PROPERTY")
    private String property;

    @Column(name="REQUIREMENT")
    private String requirement;
}
