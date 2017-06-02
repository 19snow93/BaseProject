package com.cpgc.baseproject.utils;


import com.cpgc.baseproject.R;

import java.io.Serializable;

/**
 * Activity转场动画
 * Created by jaminchanks on 2017/3/7.
 */
public enum ActivityTransition implements Serializable {
    RIGHT_TO_LEFT(R.anim.in_from_right, R.anim.out_to_left), //从右到左
    LEFT_TO_RIGHT(R.anim.in_from_left, R.anim.out_to_right),
    DOWN_TO_UP(R.anim.pop_from_bottom_activity, R.anim.anim_no_effect), //从底部到上面
    UP_TO_DOWN(R.anim.anim_no_effect, R.anim.pop_exit_bottom_activity),
    ALPHA_IN(R.anim.alpha_in, R.anim.alpha_out),
    AlPHA_OUT(R.anim.anim_no_effect, R.anim.alpha_out), //逐渐消失
    NO_EFFECT(R.anim.anim_no_effect, R.anim.anim_no_effect);

    public static final String TRANSITION_ANIMATION = "transition_animation";

    public int inAnimId;
    public int outAnimId;

    ActivityTransition(int inAnimId, int outAnimId) {
        this.inAnimId = inAnimId;
        this.outAnimId = outAnimId;
    }

    /**
     * 默认的切换方式
     * @return
     */
    public static ActivityTransition getDefaultInTransition() {
        return RIGHT_TO_LEFT;
    }

    public static ActivityTransition getDefaultOutTransition() {
        return LEFT_TO_RIGHT;
    }
}
