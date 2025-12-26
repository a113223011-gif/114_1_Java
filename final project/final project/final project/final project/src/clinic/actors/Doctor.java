package clinic.actors;

import clinic.appointments.Appointment;
import clinic.appointments.RegularAppointment;
import clinic.core.TimeSlot;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * 醫師（Doctor）類別
 * 繼承自 Person
 * 負責管理專長、看診時段與預約紀錄
 */
public class Doctor extends Person {

    /** 醫師專長 */
    private final String specialty;

    /** 醫師可看診的時段清單 */
    private final ArrayList<TimeSlot> schedule;

    /** 醫師已被預約的掛號紀錄 */
    private final ArrayList<Appointment> appointments;

    /** 每日最大看診人數上限 */
    private static final int DAILY_LIMIT = 30;

    /** 醫師編號最大值 */
    public static final int MAX_DOCTOR_ID = 50;

    /** 醫師編號 */
    private Integer doctorId;

    /**
     * 建構子：建立一位醫師
     *
     * @param id 醫師識別碼
     * @param name 醫師姓名
     * @param contactInfo 聯絡資訊
     * @param specialty 醫師專長
     */
    public Doctor(String id, String name, String contactInfo, String specialty) {
        super(id, name, contactInfo);
        this.specialty = specialty;
        this.schedule = new ArrayList();
        this.appointments = new ArrayList();
    }

    /**
     * 取得醫師專長
     *
     * @return 專長
     */
    public String getSpecialty() {
        return this.specialty;
    }

    /**
     * 新增可看診時段
     *
     * @param slot 時段
     */
    public void addTimeSlot(TimeSlot slot) {
        this.schedule.add(slot);
    }

    /**
     * 病人預約掛號
     *
     * @param patient 病人
     * @param slot 預約時段
     * @return 成功建立的預約物件
     */
    public Appointment bookAppointment(Patient patient, TimeSlot slot) {

        // 檢查該時段是否在醫師可看診清單中
        if (!this.schedule.contains(slot)) {
            throw new IllegalStateException("醫師在此時段不可預約");
        } else {

            // 檢查該時段是否已被其他病人預約
            for (Appointment a : this.appointments) {
                if (a.getTimeSlot() != null && a.getTimeSlot().equals(slot)) {
                    throw new IllegalStateException("此時段已被預約");
                }
            }

            // 檢查是否超過每日掛號上限
            if (this.appointments.size() >= DAILY_LIMIT) {
                throw new IllegalStateException("醫師當日預約已達上限");
            } else {

                // 建立一般預約
                Appointment appointment = new RegularAppointment(patient, this, slot);
                this.appointments.add(appointment);

                // 顯示掛號成功訊息
                PrintStream out = System.out;
                out.println("掛號成功：" + patient.getName()
                        + " 預約 " + slot.getStartTime()
                        + " ~ " + slot.getEndTime());

                return appointment;
            }
        }
    }

    /**
     * 取得目前尚未被預約的時段清單
     *
     * @return 可預約時段
     */
    public ArrayList<TimeSlot> getAvailableSlotsForPatient() {
        ArrayList<TimeSlot> available = new ArrayList();

        for (TimeSlot slot : this.schedule) {
            boolean booked = false;

            for (Appointment a : this.appointments) {
                if (a.getTimeSlot() != null && a.getTimeSlot().equals(slot)) {
                    booked = true;
                    break;
                }
            }

            if (!booked) {
                available.add(slot);
            }
        }

        return available;
    }

    /** 取得醫師時段表 */
    public ArrayList<TimeSlot> getSchedule() {
        return this.schedule;
    }

    /** 取得醫師的預約紀錄 */
    public ArrayList<Appointment> getAppointments() {
        return this.appointments;
    }

    /** 取得醫師編號 */
    public Integer getDoctorId() {
        return this.doctorId;
    }

    /**
     * 設定醫師編號（限制 0～50）
     *
     * @param doctorId 醫師編號
     */
    public void setDoctorId(Integer doctorId) {
        if (doctorId == null) {
            this.doctorId = null;
        } else if (doctorId >= 0 && doctorId <= MAX_DOCTOR_ID) {
            this.doctorId = doctorId;
        } else {
            throw new IllegalArgumentException("醫師編號必須介於 0 到 50");
        }
    }

    /**
     * @deprecated 舊版方法，已被 doctorId 取代
     */
    @Deprecated
    public String getDoctorNumber() {
        return this.doctorId == null ? null : String.valueOf(this.doctorId);
    }

    /**
     * @deprecated 舊版方法，支援字串格式的醫師編號
     */
    @Deprecated
    public void setDoctorNumber(String doctorNumber) {
        if (doctorNumber == null) {
            this.doctorId = null;
        } else {
            String digits = doctorNumber.replaceAll("\\D", "");
            if (digits.isEmpty()) {
                throw new IllegalArgumentException("無法從字串擷取醫師編號: " + doctorNumber);
            } else {
                try {
                    int v = Integer.parseInt(digits);
                    this.setDoctorId(v);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("無效的醫師編號: " + doctorNumber);
                }
            }
        }
    }
}
