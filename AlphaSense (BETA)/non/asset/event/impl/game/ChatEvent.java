package non.asset.event.impl.game;

import non.asset.event.cancelable.CancelableEvent;

public class ChatEvent extends CancelableEvent {
	private String msg;

	public ChatEvent(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
