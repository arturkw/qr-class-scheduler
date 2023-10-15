package org.ahk.qrclassscheduler.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ahk.qrclassscheduler.activity.model.ActivityDto;
import org.ahk.qrclassscheduler.security.SecurityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
class ActivityController {

    private final ActivityService activityService;
    private final SecurityService securityService;

    @GetMapping()
    List<ActivityDto> activities(@CookieValue(value = "JSESSIONID") String sessionId, @RequestBody ActivitiesRequest qrCode) {
        //TODO: find sth better than cookie... ie JWT token..
        return activityService.activitiesDto(qrCode.classRoomQrCode, sessionId);
    }

    @PostMapping()
    void registerActivity(@RequestBody RegisterActivityRequest request) {
        activityService.registerActivity(request.registrationQrCode, securityService.loggedUser().getUsername());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ActivitiesRequest {
        String classRoomQrCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class RegisterActivityRequest {
        String registrationQrCode;
    }

}
