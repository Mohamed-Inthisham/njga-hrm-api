package lk.zerocode.api.service.impl;

import lk.zerocode.api.controller.request.CurrentWorkDetailRequest;
import lk.zerocode.api.controller.response.CurrentWorkDetailResponse;
import lk.zerocode.api.controller.response.IdResponse;
import lk.zerocode.api.exceptions.*;
import lk.zerocode.api.model.*;
import lk.zerocode.api.repository.*;
import lk.zerocode.api.service.CurrentWorkDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentWorkDetailServiceImpl implements CurrentWorkDetailService {

    private final EmployeeRepository employeeRepository;
    private final CurrentWorkDetailRepository currentWorkDetailRepository;
    private final BranchesRepository branchesRepository;
    private final DepartmentRepository departmentRepository;
    private final EmpCategoryRepository empCategoryRepository;

    public void saveWorkDetail(Long empId, CurrentWorkDetailRequest currentWorkDetailRequest) throws EmployeeNotFoundException,BranchNotFoundException ,DepartmentNotFoundException,EmpCategoryNotFoundException{

       Employee employee = employeeRepository.findById(empId).orElseThrow(
               () -> new EmployeeNotFoundException("Employee not found")
       );
        System.out.println("category "+currentWorkDetailRequest.getEmpCategory());
        System.out.println("category type "+currentWorkDetailRequest.getEmpCategoryType());
        System.out.println("emp id "+empId);
        System.out.println("branch code "+currentWorkDetailRequest.getBranchCode());
        System.out.println("employee code "+currentWorkDetailRequest.getEmpCode());

        Branch branch = branchesRepository.findBranchByBranchCode(currentWorkDetailRequest.getBranchCode()).orElseThrow(
                () -> new BranchNotFoundException("that branch not found")
        );

        Department department = departmentRepository.findDepartmentByDepId(currentWorkDetailRequest.getDepId()).orElseThrow(
                ()-> new DepartmentNotFoundException("that department not in the database")
        );

        EmpCategory empCategory = empCategoryRepository.findEmpCategoriesByEmpCategoryAndEmpType(currentWorkDetailRequest.getEmpCategory(),currentWorkDetailRequest.getEmpCategoryType()).orElseThrow(
                ()-> new EmpCategoryNotFoundException("that employee category not having database")
        );


           CurrentWorkDetail currentWorkDetail = new CurrentWorkDetail();

           currentWorkDetail.setEmployee(employee);
           currentWorkDetail.setBranch(branch);
           currentWorkDetail.setDepartment(department);
           currentWorkDetail.setEmpCategory(empCategory);

           currentWorkDetail.setWorkTelephone(currentWorkDetailRequest.getWorkTelephone());
           currentWorkDetail.setDesignation(currentWorkDetailRequest.getDesignation());
           currentWorkDetail.setStartDate(currentWorkDetailRequest.getStartDate());
           currentWorkDetail.setEmpCode(currentWorkDetailRequest.getEmpCode());

           currentWorkDetailRepository.save(currentWorkDetail);
    }

    public IdResponse deleteDetails(Long empId)throws EmployeeNotFoundException{

        Employee employee = employeeRepository.findById(empId).orElseThrow(
                ()-> new EmployeeNotFoundException("that employee not in the database")
        );

        CurrentWorkDetail currentWorkDetail = employee.getCurrentWorkDetails();
        currentWorkDetailRepository.delete(currentWorkDetail);

        return IdResponse.builder().id(empId).message("deleted").build();
    }

    @Override
    public CurrentWorkDetailResponse getDetails(Long empId)throws EmployeeNotFoundException{

        Employee employee = employeeRepository.findById(empId).orElseThrow(
                ()-> new EmployeeNotFoundException("That employee not in the database")
        );

        CurrentWorkDetail currentWorkDetail = employee.getCurrentWorkDetails();

        return CurrentWorkDetailResponse.builder()
                .id(currentWorkDetail.getId())
                .designation(currentWorkDetail.getDesignation())
                .startDate(currentWorkDetail.getStartDate())
                .workTelephone(currentWorkDetail.getWorkTelephone())
                .branchCode(currentWorkDetail.getBranch().getBranchCode())
                .depId(currentWorkDetail.getDepartment().getDepId())
                .empCategory(currentWorkDetail.getEmpCategory().getEmpCategory())
                .empCategoryType(currentWorkDetail.getEmpCategory().getEmpType())
                .empCode(currentWorkDetail.getEmpCode())
                .build();
    }


}
