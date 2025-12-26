package clinic.core;

import java.util.ArrayList;

/**
 * 診所（Clinic）類別
 * 作為整個診所系統的核心
 * 管理科別與等候佇列
 */
public class Clinic {

    /** 診所內的所有科別 */
    private ArrayList<Department> departments = new ArrayList();

    /** 全診所共用的等候佇列 */
    private WaitingQueue waitingQueue = new WaitingQueue();

    /**
     * 新增一個科別
     *
     * @param d 科別
     */
    public void addDepartment(Department d) {
        this.departments.add(d);
    }

    /**
     * 取得等候佇列
     *
     * @return WaitingQueue
     */
    public WaitingQueue getWaitingQueue() {
        return this.waitingQueue;
    }
}