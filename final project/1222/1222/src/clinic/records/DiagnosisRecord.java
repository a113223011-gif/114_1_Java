package clinic.records;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import java.time.LocalDate;

/**
 * 診斷紀錄（DiagnosisRecord）
 * 繼承自 MedicalRecord
 * 用於記錄醫師對病患的診斷結果
 */
public class DiagnosisRecord extends clinic.records.MedicalRecord {

    /** 診斷內容 */
    private String diagnosis;

    /**
     * 建構子：建立一筆診斷紀錄
     *
     * @param recordId 紀錄編號
     * @param patient 病患
     * @param doctor 醫師
     * @param date 診斷日期
     * @param diagnosis 診斷結果
     */
    public DiagnosisRecord(String recordId, Patient patient, Doctor doctor, LocalDate date, String diagnosis) {
        super(recordId, patient, doctor, date);
        this.diagnosis = diagnosis;
    }

    /**
     * 取得診斷結果
     *
     * @return 診斷內容
     */
    public String getDiagnosis() {
        return this.diagnosis;
    }
}
