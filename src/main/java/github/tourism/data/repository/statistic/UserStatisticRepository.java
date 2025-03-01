package github.tourism.data.repository.statistic;

import github.tourism.data.entity.statistic.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticRepository extends JpaRepository<Statistic, Integer> {

    List<Statistic> findByCategory(String category);
    @Query("SELECT s FROM Statistic s JOIN Map m ON s.mapId = m.mapId WHERE m.category = :category ORDER BY s.placeLikeNum DESC")
    List<Statistic> findByCategoryOrderByPlaceLikeNum(@Param("category") String category);
}
