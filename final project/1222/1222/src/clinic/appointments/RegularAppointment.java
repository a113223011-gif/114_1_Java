package clinic.appointments;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import clinic.core.TimeSlot;
import clinic.interfaces.Queueable;

/**
 * 一般預約（RegularAppointment）
 * 繼承自 Appointment
 * 並實作 Queueable 介面，用於排隊與叫號系統
 */
public class RegularAppointment extends clinic.appointments.Appointment implements Queueable {

    /** 全體一般預約的流水號計數器 */
    private static int counter = 1;

    /** 排隊號碼 */
    private int queueNumber;

    /** 預估等候時間（分鐘） */
    private int estimatedWaitTime;

    /**
     * 建構子：建立一般預約
     *
     * @param patient 病人
     * @param doctor 醫師
     * @param slot 看診時段
     */
    public RegularAppointment(Patient patient, Doctor doctor, TimeSlot slot) {
        super(patient, doctor, slot);
        this.queueNumber = counter++;
        this.estimatedWaitTime = 0;
    }

    /**
     * 取得排隊號碼
     *
     * @return 排隊號碼
     */
    public int getQueueNumber() {
        return this.queueNumber;
    }

    /**
     * 取得優先順序
     * 數字越小代表優先權越高
     *
     * @return 一般預約優先值
     */
    public int getPriority() {
        return 3;
    }

    /**
     * 取得預估等候時間
     *
     * @return 等候時間（分鐘）
     */
    public int getEstimatedWaitTime() {
        return this.estimatedWaitTime;
    }

    /**
     * 設定預估等候時間
     *
     * @param minutes 等候分鐘數
     */
    public void setEstimatedWaitTime(int minutes) {
        this.estimatedWaitTime = minutes;
    }
}
