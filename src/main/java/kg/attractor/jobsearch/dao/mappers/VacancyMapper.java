package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements RowMapper<Vacancy> {
    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Vacancy.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .categoryId(rs.getInt("category_id"))
                .salary(rs.getInt("salary"))
                .expFrom(rs.getInt("exp_from"))
                .expTo(rs.getInt("exp_to"))
                .isActive(rs.getBoolean("is_active"))
                .authorId(rs.getInt("author_id"))
                .createdDate(rs.getTimestamp("created_date").toLocalDateTime())
                .updateTime(rs.getTimestamp("update_time").toLocalDateTime())
                .build();
    }
    public static VacancyDto toDto(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }

        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthorId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }

}