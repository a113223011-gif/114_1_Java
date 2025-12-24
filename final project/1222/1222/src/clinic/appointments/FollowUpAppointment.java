package clinic.appointments;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import clinic.core.TimeSlot;

/**
 * 追蹤回診預約（FollowUpAppointment）
 * 繼承自 Appointment
 * 用於記錄與前一次看診相關聯的回診預約
 */
public class FollowUpAppointment extends clinic.appointments.Appointment {

    /** 前一次的預約紀錄 */
    private clinic.appointments.Appointment previousAppointment;

    /**
     * 建構子：建立追蹤回診預約
     *
     * @param patient 病人
     * @param doctor 醫師
     * @param slot 看診時段
     * @param previous 前一次預約
     */
    public FollowUpAppointment(
            Patient patient,
            Doctor doctor,
            TimeSlot slot,
            clinic.appointments.Appointment previous) {

        super(patient, doctor, slot);
        this.previousAppointment = previous;
    }

    /**
     * 取得前一次的預約紀錄
     *
     * @return 前次預約
     */
    public clinic.appointments.Appointment getPreviousAppointment() {
        return this.previousAppointment;
    }
}