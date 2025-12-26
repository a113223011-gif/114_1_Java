package clinic.actors;

import clinic.records.MedicalRecord;
import java.util.ArrayList;

/**
 * 病人（Patient）類別
 * 繼承自 Person，代表診所中的病人角色
 * 負責儲存病人的年齡與病歷紀錄
 */
public class Patient extends Person {

    /** 病人年齡 */
    private int age;

    /** 病歷紀錄清單 */
    private ArrayList<MedicalRecord> medicalHistory;

    /**
     * 建構子：建立一位病人
     *
     * @param id 病人編號
     * @param name 病人姓名
     * @param contactInfo 聯絡資訊
     * @param age 病人年齡
     */
    public Patient(String id, String name, String contactInfo, int age) {
        super(id, name, contactInfo);
        this.age = age;
        // 初始化病歷清單
        this.medicalHistory = new ArrayList();
    }

    /**
     * 取得病人年齡
     *
     * @return 年齡
     */
    public int getAge() {
        return this.age;
    }

    /**
     * 取得病人的病歷紀錄清單
     *
     * @return 病歷 ArrayList
     */
    public ArrayList<MedicalRecord> getMedicalHistory() {
        return this.medicalHistory;
    }

    /**
     * 新增一筆病歷紀錄
     *
     * @param record 病歷資料
     */
    public void addMedicalRecord(MedicalRecord record) {
        this.medicalHistory.add(record);
    }
}

