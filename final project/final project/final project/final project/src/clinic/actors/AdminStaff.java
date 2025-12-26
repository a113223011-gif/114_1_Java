package clinic.actors;

import clinic.appointments.EmergencyAppointment;
import clinic.core.TimeSlot;
import clinic.core.WaitingQueue;

/**
 * 行政人員（AdminStaff）類別
 * 繼承自 Person
 * 負責處理緊急插號與行政相關操作
 */
public class AdminStaff extends Person {

    /**
     * 建構子：建立一位行政人員
     *
     * @param id 行政人員編號
     * @param name 姓名
     * @param contactInfo 聯絡資訊
     */
    public AdminStaff(String id, String name, String contactInfo) {
        super(id, name, contactInfo);
    }

    /**
     * 新增緊急掛號（插隊）
     *
     * @param patient 病人
     * @param doctor 指定醫師
     * @param slot 時段
     * @param queue 等候佇列
     * @return 緊急預約物件
     */
    public EmergencyAppointment addEmergencyAppointment(
            Patient patient,
            Doctor doctor,
            TimeSlot slot,
            WaitingQueue queue) {

        // 建立緊急預約
        EmergencyAppointment emergency = new EmergencyAppointment(patient, doctor);

        // 加入等待佇列
        queue.addPatient(emergency);

        // 顯示插號訊息
        System.out.println("[行政人員加號] "
                + patient.getName()
                + "（緊急插隊，依現場安排）");

        return emergency;
    }
}