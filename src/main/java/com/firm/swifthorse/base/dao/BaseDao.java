package com.firm.swifthorse.base.dao;
import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.firm.swifthorse.base.entity.BaseEntity;

@NoRepositoryBean
public interface BaseDao<T extends BaseEntity> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T> {
}