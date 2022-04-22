package me.util.animations;


public class Linear extends Easing {

	public static final double easeNone(double progression, double startValue, double endValue, double duration) {
		return endValue * progression / duration + startValue;
	}

	public static final double easeIn(double progression, double startValue, double endValue, double duration) {
		return endValue * progression / duration + startValue;
	}

	public static final double easeOut(double progression, double startValue, double endValue, double duration) {
		return endValue * progression / duration + startValue;
	}

	public static final double easeInOut(double progression, double startValue, double endValue, double duration) {
		return endValue * progression / duration + startValue;
	}

}