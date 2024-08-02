package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> getResume() {
        String sql = """
                select * from resumes
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public Optional<Resume> getResumeById(long id) {
        String sql = """
                select * from resumes
                where ID = ?
                """;
        return Optional.ofNullable(DataAccessUtils.singleResult(
                template.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
        ));
    }

    public List<Resume> getResumesByCategoryId(Integer categoryId) {
        String sql = """
                select * from resumes
                where CATEGORY_ID = ?
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }

    public List<Resume> getResumesByUserId(Integer userId) {
        String sql = """
                select * from RESUMES
                where APPLICANT_ID = ?
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }

    public void addResume(Resume resume) {
        String sql = """
                insert into RESUMES(APPLICANT_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE, CREATED_DATE, UPDATE_TIME)
                values (:applicantId, :name, :categoryId, :salary, :isActive,  :createdDate, :updateTime);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("applicantId", resume.getApplicantId())
                .addValue("name", resume.getName())
                .addValue("categoryId", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("isActive", resume.getIsActive())
                .addValue("createdDate", resume.getCreatedDate())
                .addValue("updateTime", resume.getUpdateTime()));
    }

    public void delete(Integer id) {
        String sql = """
                delete from RESUMES
                where ID=?
                """;
        template.update(sql, id);
    }

    // Метод для сохранения нового резюме
    public void save(Resume resume) {
        String sql = """
                insert into resumes (APPLICANT_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE, CREATED_DATE, UPDATE_TIME)
                values (:applicantId, :name, :categoryId, :salary, :isActive, :createdDate, :updateTime)
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("applicantId", resume.getApplicantId())
                .addValue("name", resume.getName())
                .addValue("categoryId", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("isActive", resume.getIsActive())
                .addValue("createdDate", resume.getCreatedDate())
                .addValue("updateTime", resume.getUpdateTime()));
    }

    // Метод для обновления существующего резюме
    public void update(Resume resume) {
        String sql = """
                update resumes set
                APPLICANT_ID = :applicantId,
                NAME = :name,
                CATEGORY_ID = :categoryId,
                SALARY = :salary,
                IS_ACTIVE = :isActive,
                CREATED_DATE = :createdDate,
                UPDATE_TIME = :updateTime
                where ID = :id
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("applicantId", resume.getApplicantId())
                .addValue("name", resume.getName())
                .addValue("categoryId", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("isActive", resume.getIsActive())
                .addValue("createdDate", resume.getCreatedDate())
                .addValue("updateTime", resume.getUpdateTime())
                .addValue("id", resume.getId()));
    }
    public List<Resume> getAllResumes() {
        String sql = """
                select * from resumes
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }
}