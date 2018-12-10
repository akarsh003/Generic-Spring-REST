package com.example.demo.department;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import com.example.demo.common.ApplicationRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Department;


//@RepositoryRestResource(exported=false/*,excerptProjection = InlineRecordsDepartment.class*/)
public interface DeptRepo extends ApplicationRepository<Department, Integer> {

	public Department findBydeptid(int id);
	
//	@RestResource()
//	public Page<T> findByid(int id,Pageable pageable);
//	@RestResource()
//	public Page<Department> findAll(Pageable pageable);
//	Page<Department> findAll(Specification spec,Pageable pageable);

//	public int findDeptId();
	
//	Department findByEmployee(int id);
}


