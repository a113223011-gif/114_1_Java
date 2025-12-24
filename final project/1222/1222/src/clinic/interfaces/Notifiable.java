package clinic.interfaces;

import clinic.appointments.Appointment;

/**
 * 可通知（Notifiable）介面
 * 定義系統中具有通知功能的角色
 * 例如發送提醒或通知訊息
 */
public interface Notifiable {

    /**
     * 發送看診提醒
     *
     * @param var1 預約資料
     */
    void sendReminder(Appointment var1);

    /**
     * 發送一般通知訊息
     *
     * @param var1 通知內容
     */
    void sendNotification(String var1);

    /**
     * 取得使用者偏好的聯絡方式
     *
     * @return 聯絡方式（例如電話、Email）
     */
    String getPreferredContactMethod();
}


