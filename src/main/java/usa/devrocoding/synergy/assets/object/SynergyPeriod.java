package usa.devrocoding.synergy.assets.object;

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

    public SynergyPeriod a(int value){
        this.period *= 2;
        return this;
    }


}
