package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean 
public interface ApplicationRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor {
}
