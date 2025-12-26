package clinic.core;

import clinic.actors.Doctor;
import java.util.ArrayList;

/**
 * 科別（Department）類別
 * 代表診所中的一個醫療科別
 * 管理該科別底下的醫師
 */
public class Department {

    /** 科別名稱 */
    private String name;

    /** 該科別的醫師清單 */
    private ArrayList<Doctor> doctors;

    /**
     * 建構子：建立一個科別
     *
     * @param name 科別名稱
     */
    public Department(String name) {
        this.name = name;
        this.doctors = new ArrayList();
    }

    /**
     * 新增醫師至科別
     *
     * @param doctor 醫師
     */
    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
    }

    /**
     * 取得該科別的醫師清單
     *
     * @return 醫師列表
     */
    public ArrayList<Doctor> getDoctors() {
        return this.doctors;
    }

    /**
     * 取得科別名稱
     *
     * @return 科別名稱
     */
    public String getName() {
        return this.name;
    }
}
