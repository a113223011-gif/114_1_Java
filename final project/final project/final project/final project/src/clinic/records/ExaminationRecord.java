package clinic.records;

import clinic.actors.Doctor;
import clinic.actors.Patient;
import java.time.LocalDate;

/**
 * 檢查紀錄（ExaminationRecord）
 * 繼承自 MedicalRecord
 * 用於記錄病患的檢查項目與結果
 */
public class ExaminationRecord extends clinic.records.MedicalRecord {

    /** 檢查內容說明 */
    private String examinationDetails;

    /**
     * 建構子：建立一筆檢查紀錄
     *
     * @param recordId 紀錄編號
     * @param patient 病患
     * @param doctor 醫師
     * @param date 檢查日期
     * @param details 檢查內容
     */
    public ExaminationRecord(String recordId, Patient patient, Doctor doctor, LocalDate date, String details) {
        super(recordId, patient, doctor, date);
        this.examinationDetails = details;
    }

    /**
     * 取得檢查內容
     *
     * @return 檢查內容
     */
    public String getExaminationDetails() {
        return this.examinationDetails;
    }
}
