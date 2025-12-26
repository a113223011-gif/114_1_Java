package clinic.interfaces;

/**
 * 可排隊（Queueable）介面
 * 定義可被加入等候佇列的物件行為
 */
public interface Queueable {

    /**
     * 取得排隊號碼
     *
     * @return 排隊號碼
     */
    int getQueueNumber();

    /**
     * 取得優先順序
     * 數字越小代表優先權越高
     *
     * @return 優先值
     */
    int getPriority();

    /**
     * 取得預估等候時間
     *
     * @return 等候時間（分鐘）
     */
    int getEstimatedWaitTime();

    /**
     * 設定預估等候時間
     *
     * @param var1 等候時間（分鐘）
     */
    void setEstimatedWaitTime(int var1);
}
