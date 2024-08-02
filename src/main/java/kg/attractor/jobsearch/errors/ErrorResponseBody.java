package kg.attractor.jobsearch.errors;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorResponseBody {
    private String error;
    private Map<String, List<String>> reasons;
}
