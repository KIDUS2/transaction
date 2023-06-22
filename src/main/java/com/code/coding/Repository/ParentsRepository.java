package com.code.coding.Repository;

import com.code.coding.Model.Parent;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ParentsRepository extends JpaRepository<Parent, Long> {
}
