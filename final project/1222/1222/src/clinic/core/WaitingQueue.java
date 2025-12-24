package clinic.core;

import clinic.appointments.Appointment;
import clinic.appointments.EmergencyAppointment;
import clinic.appointments.FollowUpAppointment;
import clinic.appointments.RegularAppointment;
import clinic.appointments.TelemedicineAppointment;
import clinic.interfaces.Queueable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 等候佇列（WaitingQueue）
 * 用於管理病患的排隊順序與等候時間
 */
public class WaitingQueue {

    /** 預約等候清單 */
    private final ArrayList<Appointment> queue = new ArrayList<>();

    /**
     * 新增病患至等候佇列
     *
     * @param appointment 預約物件
     */
    public void addPatient(Appointment appointment) {
        this.queue.add(appointment);
        this.updateQueue();
        this.updateEstimatedWaitTime();
    }

    /**
     * 依預約類型與病患條件重新排序佇列
     */
    private void updateQueue() {
        this.queue.sort(Comparator.comparingInt((Appointment a) -> {

            // 緊急預約優先權最高
            if (a instanceof EmergencyAppointment) {
                return 1;
            } else {
                int age = a.getPatient().getAge();

                // 一般年齡區間
                if (age < 65 && age > 6) {
                    if (a instanceof FollowUpAppointment) {
                        return 3;
                    } else if (a instanceof RegularAppointment) {
                        return 4;
                    } else {
                        return a instanceof TelemedicineAppointment ? 5 : 6;
                    }
                } else {
                    // 幼童與高齡者優先
                    return 2;
                }
            }
        }).thenComparing(Appointment::getCreatedTime));
    }

    /**
     * 重新計算所有病患的預估等候時間
     */
    private void updateEstimatedWaitTime() {
        int minutesPerPatient = 10;
        int time = 0;

        for (Appointment a : this.queue) {
            if (a instanceof Queueable) {
                ((Queueable) a).setEstimatedWaitTime(time);
                time += minutesPerPatient;
            }
        }
    }

    /**
     * 取得下一位看診病患
     *
     * @return 下一位病患的預約資料
     */
    public Appointment getNextPatient() {
        if (this.queue.isEmpty()) {
            return null;
        } else {
            Appointment next = this.queue.remove(0);
            this.updateEstimatedWaitTime();
            return next;
        }
    }

    /**
     * 取得目前等候人數
     *
     * @return 佇列大小
     */
    public int size() {
        return this.queue.size();
    }
}
