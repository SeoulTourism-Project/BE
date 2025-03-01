package github.tourism.data.repository.statistic;

import github.tourism.data.entity.statistic.RankPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankPlaceRepository extends JpaRepository<RankPlace,Integer> {
    List<RankPlace> findByYearMonth(String yearMonth);
}
