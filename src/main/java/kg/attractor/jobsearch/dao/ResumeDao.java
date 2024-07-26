package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;

    // Получение резюме по категории
    public List<Resume> getResumesByCategory(int categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = ?";
        return jdbcTemplate.query(sql, new ResumeMapper(), categoryId);
    }

    // Получение резюме, созданных пользователем
    public List<Resume> getResumesByUserId(int userId) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = ?";
        return jdbcTemplate.query(sql, new ResumeMapper(), userId);
    }
}