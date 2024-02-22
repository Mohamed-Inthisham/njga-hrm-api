package lk.zerocode.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "current_work_details")
@Data
public class CurrentWorkDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;
    private LocalDate startDate;

    private String workTelephone;
    @OneToOne
    private Employee employee;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Department department;

    @ManyToOne
    private EmpCategory empCategory;



}
