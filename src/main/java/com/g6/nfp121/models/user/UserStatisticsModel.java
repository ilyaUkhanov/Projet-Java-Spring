package com.g6.nfp121.models.user;

import java.util.List;

import lombok.Data;

@Data
public class UserStatisticsModel {
    private Double totalDistance;
    private Long totalTime;
    private int ongoingChallenges;
    private int finishedChallenges;
    private int abandonnedChallenges;
    private List<UserStatisticsDistanceModel> dailyDistance;
}
