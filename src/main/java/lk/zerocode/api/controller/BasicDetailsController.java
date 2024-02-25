package lk.zerocode.api.controller;

import lk.zerocode.api.controller.request.BasicDetailsRequest;
import lk.zerocode.api.controller.response.BasicDetailsResponse;
import lk.zerocode.api.controller.response.IdResponse;
import lk.zerocode.api.exceptions.EmployeeNotFoundException;
import lk.zerocode.api.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class BasicDetailsController {

    private EmployeeService employeeService;
    @PostMapping("/employee")
    public IdResponse addEmployeeDetails(@RequestBody BasicDetailsRequest basicDetailsRequest)throws EmployeeNotFoundException {
        return employeeService.saveBasicDetails(basicDetailsRequest);
    }
    @GetMapping("/employee/{emp_id}")
    public BasicDetailsResponse getEmployeeByEmpId(@PathVariable("emp_id") String id) throws EmployeeNotFoundException{
        return employeeService.getByEmpId(id);
    }
    @GetMapping("/employee2/{emp_email}")
    public BasicDetailsResponse getEmployeeByEmail(@PathVariable("emp_email") String email) throws EmployeeNotFoundException{
        return employeeService.getByEmpEmail(email);
    }

    @GetMapping("/employees")
    public List<BasicDetailsResponse> getAllBasicDetails() throws EmployeeNotFoundException{
        return employeeService.getAll();
    }

    public BasicDetailsResponse update(@PathVariable ("emp_id") String id, @RequestBody BasicDetailsRequest basicDetailsRequest) throws EmployeeNotFoundException{
        return employeeService.updateBasicDetails(id, basicDetailsRequest);
    }
}
