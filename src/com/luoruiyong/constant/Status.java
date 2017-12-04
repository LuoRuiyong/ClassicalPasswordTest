package com.luoruiyong.constant;

public class Status {
	public static final String READY = "��Կ����֤";
	public static final String SECRET_KEY_AVAILABLE = "��Կ��Ч";
	public static final String SECRET_KEY_INVALID = "��Կ��Ч";
	public static final String ENCRYPT_SUCCEED = "���ܳɹ�";
	public static final String ENCRYPT_FAILED = "����ʧ��";
	public static final String DECRYPT_SUCCEED = "���ܳɹ�";
	public static final String DECRYPT_FAILED = "����ʧ��";
	public static final String EXHAUST_CRACK = "��ٷ��ƽ������ʾ";
	public static final String PROBABILITY_CRACK_SUCCEED = "�����ƽ�ɹ�";
	public static final String PROBABILITY_CRACK_FAILED = "�����ƽ�ʧ��";
	public static final String ENCRYPT_ANALYSIS = "���ܷ���";
	public static final String DECRYPT_ANALYSIS = "���ܷ���";
	public static final String CRACK_ANALYSIS = "�ƽ����";
	
	public interface OnStatusChangedListener{
		void OnStatusChanged(String status);
	}
}
