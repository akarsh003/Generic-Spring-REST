package com.ndg.springdemo.department;

import com.ndg.springdemo.common.ApplicationRepository;
import com.ndg.springdemo.model.Department;


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


