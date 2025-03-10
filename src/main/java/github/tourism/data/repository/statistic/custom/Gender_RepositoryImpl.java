package github.tourism.data.repository.statistic.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import github.tourism.data.entity.statistic.Gender_Statistic;
import github.tourism.web.dto.statistic.GenderTop7DTO;
import github.tourism.web.dto.statistic.QGenderTop7DTO;
import jakarta.persistence.EntityManager;

import java.util.List;

import static github.tourism.data.entity.statistic.QGender_Statistic.gender_Statistic;

public class Gender_RepositoryImpl implements GenderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Gender_RepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GenderTop7DTO> findTop7ByYearAndMonth(int year,int month){
        return queryFactory
                .select(new QGenderTop7DTO(gender_Statistic.country, gender_Statistic.total_population,
                        gender_Statistic.male_population, gender_Statistic.female_population))
                .from(gender_Statistic)
                .where(gender_Statistic.year.eq(year)
                        .and(gender_Statistic.month.eq(month)))
                .orderBy(gender_Statistic.total_population.desc())
                .limit(7)
                .fetch();
    };

    @Override
    public List<GenderTop7DTO> findTop7CountriesByYear(int year) {
        return queryFactory
                .select(Projections.constructor(GenderTop7DTO.class,
                                gender_Statistic.country,
                                gender_Statistic.total_population.sum(),
                                gender_Statistic.male_population.sum(),
                                gender_Statistic.female_population.sum()))
                .from(gender_Statistic)
                .where(gender_Statistic.year.eq(year))
                .groupBy(gender_Statistic.country)
                .orderBy(gender_Statistic.total_population.sum().desc())
                .limit(7)
                .fetch();
    }


}
