package projects.blue_whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="MAINTENANCE")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="TYPE")
    private String type;

    @Column(name="NAME")
    private String name;

    @Column(name="MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name="DATE")
    private String date;

    @Column(name="MAINTENANCE_DATE")
    private String maintenanceDate;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="COMMENT", columnDefinition = "TEXT")
    private String comment;
}
