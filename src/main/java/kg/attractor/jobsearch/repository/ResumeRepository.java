package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    List<Resume> findByCategoryId(Integer categoryId);

    List<Resume> findByApplicantId(Integer userId);

    @Query("SELECT r FROM Resume r " +
            "JOIN RespondedApplicants ra ON r.id = ra.resumeId " +
            "JOIN Vacancy v ON v.id = ra.vacancyId " +
            "JOIN User u ON u.id = v.authorId " +
            "WHERE u.id = :employerId")
    List<Resume> findResumesRespondedToEmployerVacancies(@Param("employerId") Integer employerId);
}
