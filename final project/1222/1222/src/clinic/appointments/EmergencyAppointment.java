package clinic.appointments;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import clinic.core.TimeSlot;

/**
 * 緊急預約（EmergencyAppointment）
 * 繼承自 Appointment
 * 用於處理無固定時段的緊急插號情況
 */
public class EmergencyAppointment extends clinic.appointments.Appointment {

    /**
     * 建構子：建立一筆緊急預約
     * 緊急預約不指定固定時段，因此 TimeSlot 為 null
     *
     * @param patient 病人
     * @param doctor 醫師
     */
    public EmergencyAppointment(Patient patient, Doctor doctor) {
        super(patient, doctor, (TimeSlot)null);
    }
}

