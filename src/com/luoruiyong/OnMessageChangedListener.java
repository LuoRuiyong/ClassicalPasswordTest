package com.luoruiyong;

import com.luoruiyong.bean.Message;

public interface OnMessageChangedListener {
	void onArithmeticTypeChanged(Message message);
	void onSecretKeyChanged(Message message);
	void onPlaintextChanged(Message message);
	void onCiphertextChanged(Message message);
	void onOnlyStatusChanged(String status);
}
