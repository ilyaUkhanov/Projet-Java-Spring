package com.g6.nfp121.models.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserStatisticsDistanceModel {
    LocalDate day;
    Double distance;

    public Double addDistance(Double distanceToAdd) {
        return distance += distanceToAdd;
    }
}
