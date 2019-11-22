package com.booklibrary.app.configuration.changelogs.V3.actions;

import com.booklibrary.app.models.nosql.PenaltyPlan;

import java.util.ArrayList;
import java.util.List;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V3/resources/penalty/PENALTY_PLANS.yml")
//@ConfigurationProperties
//@Configuration
//@Slf4j
public class PresetPenaltyPlansCollection {

    private static List<PenaltyPlan> penalties = new ArrayList<>();

    public void setPenalties(List<PenaltyPlan> penalties) {
        PresetPenaltyPlansCollection.penalties = penalties;
    }

    public List<PenaltyPlan> getPenalties() {
        return penalties;
    }

    public static List<PenaltyPlan> presetPenaltyPlansCollection() {
        return penalties;
    }
}
