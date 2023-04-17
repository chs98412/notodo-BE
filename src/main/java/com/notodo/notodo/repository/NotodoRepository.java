package com.notodo.notodo.repository;

import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotodoRepository extends JpaRepository<Notodo, Long> {
    List<Notodo> findByMemberAndAdded(Member member, LocalDate added);
}
