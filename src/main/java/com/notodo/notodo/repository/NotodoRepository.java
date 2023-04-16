package com.notodo.notodo.repository;

import com.notodo.notodo.entity.Notodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotodoRepository extends JpaRepository<Notodo, Long> {
}
