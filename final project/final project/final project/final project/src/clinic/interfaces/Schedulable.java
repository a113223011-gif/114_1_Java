package clinic.interfaces;

import clinic.core.TimeSlot;
import java.util.ArrayList;

/**
 * 可排程（Schedulable）介面
 * 定義具有時段管理功能的物件
 */
public interface Schedulable {

    /**
     * 取得可預約的時段清單
     *
     * @return 可用時段列表
     */
    ArrayList<TimeSlot> getAvailableSlots();

    /**
     * 預約指定時段
     *
     * @param var1 時段
     * @return 是否預約成功
     */
    boolean bookSlot(TimeSlot var1);

    /**
     * 取消指定時段
     *
     * @param var1 時段
     * @return 是否取消成功
     */
    boolean cancelSlot(TimeSlot var1);
}

