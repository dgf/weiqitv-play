package gatherer.listener;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

class GamePlayerStatus {

    private int seconds;

    private int byo;

    private int prisoners;

    public int getByo() {
        return byo;
    }

    public int getPrisoners() {
        return prisoners;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setByo(int byo) {
        this.byo = byo;
    }

    public void setPrisoners(int prisoners) {
        this.prisoners = prisoners;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
