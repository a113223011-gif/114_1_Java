package clinic.billing;

import clinic.actors.Patient;
import clinic.interfaces.Billable;

/**
 * 帳單（Bill）類別
 * 實作 Billable 介面
 * 用於計算病患看診費用、保險給付與實際需支付金額
 */
public class Bill implements Billable {

    /** 病患資料 */
    private Patient patient;

    /** 基本診療費用 */
    private double baseFee;

    /** 保險給付比例（例如 0.7 代表 70%） */
    private double insuranceCoverage;

    /**
     * 建構子：建立一筆帳單
     *
     * @param patient 病患
     * @param baseFee 基本費用
     * @param insuranceCoverage 保險給付比例
     */
    public Bill(Patient patient, double baseFee, double insuranceCoverage) {
        this.patient = patient;
        this.baseFee = baseFee;
        this.insuranceCoverage = insuranceCoverage;
    }

    /**
     * 計算總費用
     *
     * @return 總費用
     */
    public double calculateFee() {
        return this.baseFee;
    }

    /**
     * 計算保險給付金額
     *
     * @return 保險給付金額
     */
    public double getInsuranceCoverage() {
        return this.baseFee * this.insuranceCoverage;
    }

    /**
     * 計算病患實際需支付金額
     *
     * @return 病患應付金額
     */
    public double getPatientPayable() {
        return this.baseFee - this.getInsuranceCoverage();
    }

    /**
     * 列印收據內容
     */
    public void printReceipt() {
        System.out.println("===== 收據 =====");
        System.out.println("病患: " + this.patient.getName());
        System.out.println("總費用: $" + this.baseFee);
        System.out.println("保險覆蓋: $" + this.getInsuranceCoverage());
        System.out.println("應付金額: $" + this.getPatientPayable());
        System.out.println("================");
    }
}