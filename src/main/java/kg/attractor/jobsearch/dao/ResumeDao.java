package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
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
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Resume> getAllResumes() {
        String sql = "SELECT * FROM resumes";
        return jdbcTemplate.query(sql, new ResumeMapper());
    }

    public Optional<Resume> getResumeById(int id) {
        String sql = "SELECT * FROM resumes WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new ResumeMapper(), id)
                )
        );
    }

    public void create(Resume resume) {
        String sql = "INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) " +
                "VALUES (:applicantId, :name, :categoryId, :salary, :isActive, :createdDate, :updateTime)";
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("applicantId", resume.getApplicantId())
                        .addValue("name", resume.getName())
                        .addValue("categoryId", resume.getCategoryId())
                        .addValue("salary", resume.getSalary())
                        .addValue("isActive", resume.getIsActive())
                        .addValue("createdDate", resume.getCreatedDate())
                        .addValue("updateTime", resume.getUpdateTime())
        );
    }

    public Integer create(int applicantId, String name, Integer categoryId, double salary, Boolean isActive, LocalDateTime createdDate, LocalDateTime updateTime) {
        String sql = "INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, applicantId);
            ps.setString(2, name);
            ps.setInt(3, categoryId);
            ps.setDouble(4, salary);
            ps.setBoolean(5, isActive);
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(createdDate));
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(updateTime));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}