package clinic.core;

import java.time.LocalDateTime;

/**
 * 看診時段（TimeSlot）
 * 用於表示醫師可看診的時間區間
 */
public class TimeSlot {

    /** 看診開始時間 */
    private LocalDateTime startTime;

    /** 看診結束時間 */
    private LocalDateTime endTime;

    /** 是否已被預約 */
    private boolean isBooked;

    /**
     * 建構子：建立一個看診時段
     *
     * @param start 開始時間
     * @param end 結束時間
     */
    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.startTime = start;
        this.endTime = end;
        this.isBooked = false;
    }

    /**
     * 取得開始時間
     *
     * @return 開始時間
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * 取得結束時間
     *
     * @return 結束時間
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * 檢查此時段是否已被預約
     *
     * @return 是否已被預約
     */
    public boolean isBooked() {
        return this.isBooked;
    }

    /**
     * 設定時段是否被預約
     *
     * @param booked 是否已被預約
     */
    public void setBooked(boolean booked) {
        this.isBooked = booked;
    }

    /**
     * 判斷兩個 TimeSlot 是否為同一個時段
     *
     * @param obj 比較物件
     * @return 是否相同
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof TimeSlot other)) {
            return false;
        } else {
            return this.startTime.equals(other.startTime)
                    && this.endTime.equals(other.endTime);
        }
    }
}
