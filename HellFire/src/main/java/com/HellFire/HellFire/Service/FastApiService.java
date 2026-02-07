package com.HellFire.HellFire.Service;

import com.HellFire.HellFire.DTO.ResumeRequest;
import com.HellFire.HellFire.DTO.Round3Submission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FastApiService {

    private final RestTemplate restTemplate;

    @Value("${fastapi.url}")
    private String fastApiUrl;

    public FastApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAtsScore(ResumeRequest resumeRequest) {
        String url = fastApiUrl + "/recom";
        // Using query parameters as per the python snippet
        String resumeData = resumeRequest.getResumeContent(); // Assuming content is what they mean by userdata
        if (resumeData == null || resumeData.isEmpty()) {
            resumeData = "Default User Data";
        }

        // Construct URL with params manually
        String finalUrl = url + "?userdata=" + resumeData;

        // POST request with query params
        return restTemplate.postForObject(finalUrl, null, String.class);
    }

    public String getInterviewQuestions() {
        String url = fastApiUrl + "/resaom";
        String jobDescription = "Frontend Engineer";
        String finalUrl = url + "?job_description=" + jobDescription;
        return restTemplate.postForObject(finalUrl, null, String.class);
    }

    public String getRound3Questions() {
        return "Please specify endpoint for fetching Round 3 questions or use Round 2 questions.";
    }

    public String evaluateRound3(Round3Submission submission) {
        String url = fastApiUrl + "/reom";
        // /reom takes params: qestions, answers
        String questionsStr = String.join(" | ", submission.getQuestions());
        String answersStr = String.join(" | ", submission.getAnswers());

        String finalUrl = url + "?qestions=" + questionsStr + "&answers=" + answersStr;
        return restTemplate.postForObject(finalUrl, null, String.class);
    }
}
