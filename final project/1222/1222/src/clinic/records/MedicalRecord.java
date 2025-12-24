package clinic.records;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import java.time.LocalDate;

/**
 * 病歷紀錄（MedicalRecord）抽象類別
 * 作為所有病歷類型的共同父類別
 */
public abstract class MedicalRecord {

    /** 病歷編號 */
    protected String recordId;

    /** 病患 */
    protected Patient patient;

    /** 看診醫師 */
    protected Doctor doctor;

    /** 看診日期 */
    protected LocalDate date;

    /**
     * 建構子：建立病歷基本資料
     *
     * @param recordId 病歷編號
     * @param patient 病患
     * @param doctor 醫師
     * @param date 日期
     */
    public MedicalRecord(String recordId, Patient patient, Doctor doctor, LocalDate date) {
        this.recordId = recordId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    /** 取得病歷編號 */
    public String getRecordId() {
        return this.recordId;
    }

    /** 取得病患 */
    public Patient getPatient() {
        return this.patient;
    }

    /** 取得醫師 */
    public Doctor getDoctor() {
        return this.doctor;
    }

    /** 取得看診日期 */
    public LocalDate getDate() {
        return this.date;
    }
}