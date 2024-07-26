package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.VacancyMapper;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Vacancy> getAllVacancies() {
        String sql = "SELECT * FROM vacancies";
        return jdbcTemplate.query(sql, new VacancyMapper());
    }

    public Optional<Vacancy> getVacancyById(int id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new VacancyMapper(), id)
                )
        );
    }

    public void create(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) " +
                "VALUES (:name, :description, :categoryId, :salary, :expFrom, :expTo, :isActive, :authorId, :createdDate, :updateTime)";
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", vacancy.getName())
                        .addValue("description", vacancy.getDescription())
                        .addValue("categoryId", vacancy.getCategoryId())
                        .addValue("salary", vacancy.getSalary())
                        .addValue("expFrom", vacancy.getExpFrom())
                        .addValue("expTo", vacancy.getExpTo())
                        .addValue("isActive", vacancy.getIsActive())
                        .addValue("authorId", vacancy.getAuthorId())
                        .addValue("createdDate", vacancy.getCreatedDate())
                        .addValue("updateTime", vacancy.getUpdateTime())
        );
    }

    public Integer create(String name, String description, Integer categoryId, Integer salary, Integer expFrom, Integer expTo, Boolean isActive, Integer authorId, LocalDateTime createdDate, LocalDateTime updateTime) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, categoryId);
            ps.setInt(4, salary);
            ps.setInt(5, expFrom);
            ps.setInt(6, expTo);
            ps.setBoolean(7, isActive);
            ps.setInt(8, authorId);
            ps.setTimestamp(9, java.sql.Timestamp.valueOf(createdDate));
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(updateTime));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}