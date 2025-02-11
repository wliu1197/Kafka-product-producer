package com.kafka.ms.products.repository;

import com.kafka.ms.products.model.db.CreatedProductEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatedProductEventRepository extends JpaRepository<CreatedProductEventDetails, Integer> {
}
