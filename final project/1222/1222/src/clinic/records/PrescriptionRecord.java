package clinic.records;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import java.time.LocalDate;

/**
 * 處方紀錄（PrescriptionRecord）
 * 繼承自 MedicalRecord
 * 用於記錄醫師開立的處方內容
 */
public class PrescriptionRecord extends MedicalRecord {

    /** 處方內容 */
    private String prescription;

    /**
     * 建構子：建立一筆處方紀錄
     *
     * @param recordId 紀錄編號
     * @param patient 病患
     * @param doctor 醫師
     * @param date 日期
     * @param prescription 處方內容
     */
    public PrescriptionRecord(String recordId, Patient patient, Doctor doctor, LocalDate date, String prescription) {
        super(recordId, patient, doctor, date);
        this.prescription = prescription;
    }

    /**
     * 取得處方內容
     *
     * @return 處方內容
     */
    public String getPrescription() {
        return this.prescription;
    }
}
