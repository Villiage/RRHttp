package com.me.myhttp;

public class WeatherTip {


    /**
     * tips : {"observe":{"0":"你若安好，便是晴天~","1":"天太热了，吃个西瓜~"}}
     */

    private TipsBean tips;

    public TipsBean getTips() {
        return tips;
    }

    public void setTips(TipsBean tips) {
        this.tips = tips;
    }

    public static class TipsBean {
        /**
         * observe : {"0":"你若安好，便是晴天~","1":"天太热了，吃个西瓜~"}
         */

        private String observe;

        public String getObserve() {
            return observe;
        }

        public void setObserve(String observe) {
            this.observe = observe;
        }


    }

}
