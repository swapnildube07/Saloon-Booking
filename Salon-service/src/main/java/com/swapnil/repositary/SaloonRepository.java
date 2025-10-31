package com.swapnil.repositary;

import com.swapnil.modal.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaloonRepository extends JpaRepository<Saloon, Long> {

    Saloon findByOwnerId(Long id);

    Saloon findByCity(String city);

    @Query(
            "SELECT s FROM Saloon s WHERE " +
                    "LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                    "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                    "LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Saloon> searchSaloon(@Param("keyword") String keyword);
}
