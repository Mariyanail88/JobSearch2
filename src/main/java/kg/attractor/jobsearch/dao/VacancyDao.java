package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.dao.mappers.VacancyMapper;
import kg.attractor.jobsearch.model.User;
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

    // Получение всех вакансий
    public List<Vacancy> getAllVacancies() {
        String sql = "SELECT * FROM vacancies";
        return jdbcTemplate.query(sql, new VacancyMapper());
    }

    // Получение вакансий по категории
    public List<Vacancy> getVacanciesByCategory(int categoryId) {
        String sql = "SELECT * FROM vacancies WHERE category_id = ?";
        return jdbcTemplate.query(sql, new VacancyMapper(), categoryId);
    }

    // Получение вакансий, на которые откликнулся пользователь
    public List<Vacancy> getVacanciesByUserId(int userId) {
        String sql = "SELECT v.* FROM vacancies v " +
                "JOIN responded_applicants ra ON v.id = ra.vacancy_id " +
                "WHERE ra.resume_id IN (SELECT id FROM resumes WHERE applicant_id = ?)";
        return jdbcTemplate.query(sql, new VacancyMapper(), userId);
    }

    // Получение откликнувшихся соискателей на вакансию
    public List<User> getApplicantsByVacancyId(int vacancyId) {
        String sql = "SELECT u.* FROM users u " +
                "JOIN resumes r ON u.id = r.applicant_id " +
                "JOIN responded_applicants ra ON r.id = ra.resume_id " +
                "WHERE ra.vacancy_id = ?";
        return jdbcTemplate.query(sql, new UserMapper(), vacancyId);
    }

    // Получение вакансии по ID
    public Optional<Vacancy> getVacancyById(int id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new VacancyMapper(), id)
                )
        );
    }


}