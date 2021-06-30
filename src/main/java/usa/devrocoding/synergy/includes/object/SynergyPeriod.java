package usa.devrocoding.synergy.includes.object;

import lombok.Getter;

public enum SynergyPeriod{

    TICK(1),
    SECOND(TICK.getPeriod()*20),
    MINUTE(SECOND.getPeriod() * 60),
    QUARTER(MINUTE.getPeriod() * 15),
    HALF_AN_HOUR(QUARTER.getPeriod() * 2),
    HOUR(HALF_AN_HOUR.getPeriod() * 2),
    DAY(HOUR.getPeriod() * 24),
    WEEK(DAY.getPeriod() * 7),
    MONTH((WEEK.getPeriod() * 52) / 12);

    @Getter
    private long period;

    SynergyPeriod(long period){
        this.period = period;
    }

    public long getCustom(long value){
        return this.getPeriod() * value;
    }


}
