package clinic.appointments;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import clinic.core.TimeSlot;
import java.time.LocalDateTime;

/**
 * 預約（Appointment）父類別
 * 作為所有預約類型的共同基底類別
 * 例如：一般預約、緊急預約、追蹤回診、遠距看診等
 */
public class Appointment {

    /** 預約編號 */
    protected String appointmentId;

    /** 預約的病人 */
    protected Patient patient;

    /** 看診醫師 */
    protected Doctor doctor;

    /** 預約時段 */
    protected TimeSlot timeSlot;

    /** 建立預約的時間 */
    protected LocalDateTime createdTime;

    /** 預估等候時間（分鐘） */
    protected int estimatedWaitTime;

    /**
     * 建構子：建立一筆預約資料
     *
     * @param patient 病人
     * @param doctor 醫師
     * @param timeSlot 看診時段
     */
    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        this.createdTime = LocalDateTime.now();
    }

    /**
     * 設定預估等候時間
     *
     * @param minutes 等候分鐘數
     */
    public void setEstimatedWaitTime(int minutes) {
        this.estimatedWaitTime = minutes;
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
     * 取得預約時段
     *
     * @return TimeSlot 物件
     */
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    /**
     * 取得預約的病人
     *
     * @return 病人
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * 取得預約建立時間
     *
     * @return 建立時間
     */
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }
}

