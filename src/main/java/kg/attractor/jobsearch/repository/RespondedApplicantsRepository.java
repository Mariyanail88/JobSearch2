package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondedApplicantsRepository extends JpaRepository<RespondedApplicant, Integer> {
    List<RespondedApplicant> findByResumeId(Integer resumeId);
    List<RespondedApplicant> findByVacancyId(Integer vacancyId);
}