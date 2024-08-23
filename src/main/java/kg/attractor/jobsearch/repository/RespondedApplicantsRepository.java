package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplicants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondedApplicantsRepository extends JpaRepository<RespondedApplicants, Integer> {
    List<RespondedApplicants> findByResumeId(Integer resumeId);
    List<RespondedApplicants> findByVacancyId(Integer vacancyId);
}