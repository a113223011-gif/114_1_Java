package clinic.appointments;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import clinic.core.TimeSlot;

/**
 * 遠距看診預約（TelemedicineAppointment）
 * 繼承自 Appointment
 * 用於線上或視訊方式的看診預約
 */
public class TelemedicineAppointment extends clinic.appointments.Appointment {

    /**
     * 建構子：建立遠距看診預約
     *
     * @param patient 病人
     * @param doctor 醫師
     * @param slot 看診時段
     */
    public TelemedicineAppointment(Patient patient, Doctor doctor, TimeSlot slot) {
        super(patient, doctor, slot);
    }
}