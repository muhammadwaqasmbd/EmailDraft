package com.email.draft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.email.draft.model.Draft;


@Repository
public interface DraftRepository extends JpaRepository<Draft, Long>{
}