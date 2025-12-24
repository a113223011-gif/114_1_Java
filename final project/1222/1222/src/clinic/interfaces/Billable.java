package clinic.interfaces;

/**
 * 可計費（Billable）介面
 * 定義所有可產生帳單的物件必須實作的計費相關方法
 */
public interface Billable {

    /**
     * 計算總費用
     *
     * @return 總費用
     */
    double calculateFee();

    /**
     * 取得保險給付金額
     *
     * @return 保險給付金額
     */
    double getInsuranceCoverage();

    /**
     * 取得病患實際需支付金額
     *
     * @return 病患應付金額
     */
    double getPatientPayable();
}
