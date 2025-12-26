package clinic;

// 匯入系統中會用到的角色類別
import clinic.actors.AdminStaff;
import clinic.actors.Doctor;
import clinic.actors.Patient;

// 匯入預約相關類別
import clinic.appointments.Appointment;
import clinic.appointments.EmergencyAppointment;
import clinic.appointments.FollowUpAppointment;
import clinic.appointments.TelemedicineAppointment;

// 匯入帳單類別
import clinic.billing.Bill;

// 匯入核心功能類別
import clinic.core.TimeSlot;
import clinic.core.WaitingQueue;

// 匯入病歷相關類別
import clinic.records.DiagnosisRecord;
import clinic.records.ExaminationRecord;
import clinic.records.MedicalRecord;
import clinic.records.PrescriptionRecord;

// Java 標準函式庫
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系統主程式 Main
 * 功能包含：
 * 1. 建立測試資料（醫師、病患、預約）
 * 2. 模擬掛號與看診流程
 * 3. 等候佇列管理
 * 4. 病歷與帳單處理
 * 5. 互動式操作選單
 */
public class Main {

    // 病患資料檔案路徑
    private static final Path PATIENTS_FILE = Paths.get("patients.csv");

    // 病歷資料檔案路徑
    private static final Path MEDICAL_FILE = Paths.get("medical_records.csv");

    // 病歷流水號計數器（確保多次產生不重複）
    private static final AtomicInteger recordCounter = new AtomicInteger(10);

    // 病歷編號日期格式
    private static final DateTimeFormatter ID_DATE_FMT;

    public static void main(String[] args) {
        try {
            // 建立醫師物件
            Doctor drSmith = new Doctor("D001", "小靜靜", "smith@clinic.com", "心臟科");
            drSmith.setDoctorId(1);
            // 建立醫師的可預約時段
            TimeSlot slot1 = new TimeSlot(LocalDateTime.of(2025, 12, 16, 9, 0), LocalDateTime.of(2025, 12, 16, 9, 30));
            TimeSlot slot2 = new TimeSlot(LocalDateTime.of(2025, 12, 16, 9, 30), LocalDateTime.of(2025, 12, 16, 10, 0));
            TimeSlot slot3 = new TimeSlot(LocalDateTime.of(2025, 12, 16, 10, 0), LocalDateTime.of(2025, 12, 16, 10, 30));
            TimeSlot slot4 = new TimeSlot(LocalDateTime.of(2025, 12, 16, 10, 30), LocalDateTime.of(2025, 12, 16, 11, 0));
            // 將時段加入醫師排程
            drSmith.addTimeSlot(slot1);
            drSmith.addTimeSlot(slot2);
            drSmith.addTimeSlot(slot3);
            drSmith.addTimeSlot(slot4);
            // 建立行政人員
            AdminStaff admin = new AdminStaff("A001", "櫃檯小陳", "admin@clinic.com");
            // 建立測試病患
            Patient p1 = new Patient("P001", "小明", "alice@email.com", 30);
            Patient p2 = new Patient("P002", "阿伯", "bob@email.com", 70);
            Patient p3 = new Patient("P003", "小華", "charlie@email.com", 5);
            Patient p4 = new Patient("P004", "大衛", "david@email.com", 40);
            // 建立一般與回診預約
            Appointment ap1 = drSmith.bookAppointment(p1, slot1);
            Appointment ap2 = drSmith.bookAppointment(p2, slot2);
            Appointment ap3 = drSmith.bookAppointment(p3, slot3);
            FollowUpAppointment ap4 = new FollowUpAppointment(p4, drSmith, slot4, ap1);
            // 建立等候佇列並加入預約
            WaitingQueue queue = new WaitingQueue();
            queue.addPatient(ap1);
            queue.addPatient(ap2);
            queue.addPatient(ap3);
            queue.addPatient(ap4);
            // 新增緊急掛號
            TimeSlot emergencySlot = new TimeSlot(LocalDateTime.of(2025, 12, 16, 11, 0), LocalDateTime.of(2025, 12, 16, 11, 30));
            admin.addEmergencyAppointment(p2, drSmith, emergencySlot, queue);
            // 依序呼叫病患
            System.out.println("\n=== 呼叫病患順序 ===");
            while(queue.size() > 0) {
                Appointment next = queue.getNextPatient();
                String type = getAppointmentType(next);
                PrintStream var10000 = System.out;
                String var10001 = next.getPatient().getName();
                var10000.println(var10001 + "（" + type + "）");
                // 建立病歷紀錄
                DiagnosisRecord diag = new DiagnosisRecord(nextRecordId(), next.getPatient(), drSmith, LocalDate.now(), "輕微發燒");
                PrescriptionRecord pres = new PrescriptionRecord(nextRecordId(), next.getPatient(), drSmith, LocalDate.now(), "撲熱息痛 500mg");
                ExaminationRecord exam = new ExaminationRecord(nextRecordId(), next.getPatient(), drSmith, LocalDate.now(), "一般檢查報告");
                // 將病歷加入病患並寫入檔案
                next.getPatient().addMedicalRecord(diag);
                saveMedicalRecord(diag);
                next.getPatient().addMedicalRecord(pres);
                saveMedicalRecord(pres);
                next.getPatient().addMedicalRecord(exam);
                saveMedicalRecord(exam);
                // 建立並列印帳單
                Bill bill = new Bill(next.getPatient(), 1000.0, 0.7);
                bill.printReceipt();
                System.out.println("-------------------");
            }

            System.out.println("\n=== 例外測試 ===");

            try {
                System.out.println("【測試 1】不在排班內");
                TimeSlot invalidSlot = new TimeSlot(LocalDateTime.of(2025, 12, 17, 9, 0), LocalDateTime.of(2025, 12, 17, 9, 30));
                drSmith.bookAppointment(p1, invalidSlot);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                System.out.println("【測試 2】時段已被預約");
                drSmith.bookAppointment(p4, slot1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                System.out.println("【測試 3】醫師當日預約數達上限");

                for(int i = 0; i < 30; ++i) {
                    Patient temp = new Patient("PX" + i, "病患" + i, "x@mail.com", 40);
                    TimeSlot t = new TimeSlot(LocalDateTime.of(2025, 12, 18, i, 0), LocalDateTime.of(2025, 12, 18, i, 30));
                    drSmith.addTimeSlot(t);
                    drSmith.bookAppointment(temp, t);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Scanner scanner = new Scanner(System.in);
            ArrayList<Patient> patients = new ArrayList<>();
            if (Files.exists(PATIENTS_FILE)) {
                try {
                    for(String l : Files.readAllLines(PATIENTS_FILE)) {
                        String[] parts = l.split(",");
                        if (parts.length >= 4) {
                            try {
                                String id = parts[0];
                                String name = parts[1];
                                String contact = parts[2];
                                int age = Integer.parseInt(parts[3]);
                                patients.add(new Patient(id, name, contact, age));
                            } catch (NumberFormatException var53) {
                                System.out.println("忽略不正確的年齡值: " + parts[3]);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("載入病患檔案失敗: " + e.getMessage());
                }
            }

            addIfMissing(patients, p1);
            addIfMissing(patients, p2);
            addIfMissing(patients, p3);
            addIfMissing(patients, p4);
            ArrayList<Doctor> doctors = new ArrayList<>();
            doctors.add(drSmith);

            for(int id = 0; id <= 50; ++id) {
                boolean exists = false;

                for(Doctor d : doctors) {
                    if (d.getDoctorId() != null && d.getDoctorId() == id) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    Doctor d = new Doctor("D" + String.format("%03d", id), "醫師" + id, "doc" + id + "@clinic.com", "一般科");
                    d.setDoctorId(id);
                    int offset = id % 4;
                    LocalDateTime base = LocalDateTime.of(2025, 12, 16, 9 + offset, 0);

                    for(int s = 0; s < 4; ++s) {
                        LocalDateTime start = base.plusMinutes(30L * (long)s);
                        d.addTimeSlot(new TimeSlot(start, start.plusMinutes(30L)));
                    }

                    doctors.add(d);
                }
            }

            boolean running = true;

            while(true) {
                int choice;
                while(true) {
                    if (!running) {
                        scanner.close();
                        return;
                    }
                    // 以下程式為互動式選單系統
                    // 功能包含：
                    // - 病患管理
                    // - 預約管理
                    // - 緊急掛號
                    // - 等候佇列處理
                    // - 病歷查詢與新增
                    // 主要用於模擬實際診所系統操作流程

                    System.out.println("\n=== 互動操作選單 ===");
                    System.out.println("1. 列出病患");
                    System.out.println("2. 新增病患");
                    System.out.println("3. 列出醫師可預約時段");
                    System.out.println("4. 預約時段");
                    System.out.println("5. 新增緊急掛號");
                    System.out.println("6. 處理等候佇列（呼叫下一位）");
                    System.out.println("7. 查看病患病歷");
                    System.out.println("8. 新增病歷紀錄");
                    System.out.println("0. 結束");
                    System.out.print("選擇操作: ");
                    String line = scanner.nextLine();

                    try {
                        choice = Integer.parseInt(line.trim());
                        break;
                    } catch (Exception var56) {
                        System.out.println("請輸入數字選項");
                    }
                }

                switch (choice) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        System.out.println("--- 病患清單 ---");

                        for(int i = 0; i < patients.size(); ++i) {
                            Patient pt = patients.get(i);
                            System.out.println(i + ": " + pt.getId() + " " + pt.getName() + " (" + pt.getContactInfo() + ")");
                        }
                        break;
                    case 2:
                        System.out.print("輸入病患編號 (或輸入 r 隨機產生): ");
                        String nidInput = scanner.nextLine().trim();
                        Patient np;
                        if (nidInput.equalsIgnoreCase("r")) {
                            np = generateRandomPatient(patients);
                            patients.add(np);
                        } else {
                            String nid2 = nidInput.isEmpty() ? readNonEmpty(scanner, "輸入病患編號: ") : nidInput;
                            String nname = readNonEmpty(scanner, "輸入病患姓名: ");
                            String ncontact = readNonEmpty(scanner, "輸入聯絡資訊(Email): ");
                            int nage = readIntUpTo(scanner, "輸入年齡: ", 150);
                            np = new Patient(nid2, nname, ncontact, nage);
                            patients.add(np);
                        }

                        try {
                            String var140 = np.getId();
                            String out = var140 + "," + np.getName() + "," + np.getContactInfo() + "," + np.getAge() + System.lineSeparator();
                            Files.write(PATIENTS_FILE, out.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (IOException ex) {
                            System.out.println("無法儲存病患: " + ex.getMessage());
                        }

                        System.out.println("已新增病患: " + np.getName());
                        break;
                    case 3:
                        System.out.println("--- 醫師清單 ---");

                        for(Doctor ddoc : doctors) {
                            String idStr = ddoc.getDoctorId() == null ? "(no id)" : String.valueOf(ddoc.getDoctorId());
                            PrintStream var139 = System.out;
                            String var144 = ddoc.getName();
                            var139.println(var144 + " (id=" + idStr + ", spec=" + ddoc.getSpecialty() + ", slots=" + ddoc.getSchedule().size() + ")");
                        }

                        int did = readIntUpTo(scanner, "輸入醫師編號 (0-50): ", 50);
                        Doctor selDoc = null;

                        for(Doctor ddoc : doctors) {
                            if (ddoc.getDoctorId() != null && ddoc.getDoctorId() == did) {
                                selDoc = ddoc;
                                break;
                            }
                        }

                        if (selDoc == null) {
                            System.out.println("找不到醫師編號: " + did);
                        } else {
                            ArrayList<TimeSlot> avail = selDoc.getAvailableSlotsForPatient();
                            if (avail.isEmpty()) {
                                System.out.println("無可預約時段");
                            } else {
                                for(int i = 0; i < avail.size(); ++i) {
                                    TimeSlot ts = avail.get(i);
                                    System.out.println(i + ": " + ts.getStartTime() + " ~ " + ts.getEndTime());
                                }
                            }
                        }
                        break;
                    case 4:
                        System.out.println("--- 選擇要預約的病患 ---");

                        for(int i = 0; i < patients.size(); ++i) {
                            System.out.println(i + ": " + patients.get(i).getName());
                        }

                        int pid = readIntUpTo(scanner, "選擇病患編號: ", patients.size() - 1);
                        System.out.println("--- 選擇醫師 ---");

                        for(Doctor d : doctors) {
                            String idStr = d.getDoctorId() == null ? "(no id)" : String.valueOf(d.getDoctorId());
                            PrintStream var138 = System.out;
                            String var143 = d.getName();
                            var138.println(var143 + " (id=" + idStr + ")");
                        }

                        int selectedDocId = readIntUpTo(scanner, "輸入醫師編號 (0-50): ", 50);
                        Doctor doc = null;

                        for(Doctor d : doctors) {
                            if (d.getDoctorId() != null && d.getDoctorId() == selectedDocId) {
                                doc = d;
                                break;
                            }
                        }

                        if (doc == null) {
                            System.out.println("找不到醫師編號: " + selectedDocId);
                        } else {
                            System.out.println("選擇預約類型: 1. 一般  2. 回診  3. 緊急  4. 視訊");
                            int atype = readIntUpTo(scanner, "輸入類型 (1-4): ", 4);
                            switch (atype) {
                                case 1:
                                    ArrayList<TimeSlot> slots = doc.getAvailableSlotsForPatient();
                                    if (slots.isEmpty()) {
                                        System.out.println("無可預約時段");
                                    } else {
                                        System.out.println("--- 選擇時段 ---");

                                        for(int i = 0; i < slots.size(); ++i) {
                                            System.out.println(i + ": " + slots.get(i).getStartTime() + " ~ " + slots.get(i).getEndTime());
                                        }

                                        int sid = readIntUpTo(scanner, "選擇時段編號: ", slots.size() - 1);
                                        TimeSlot chosen = slots.get(sid);

                                        try {
                                            Appointment newAp = doc.bookAppointment(patients.get(pid), chosen);
                                            queue.addPatient(newAp);
                                            System.out.println("一般預約成功，已加入等候佇列");
                                        } catch (Exception e) {
                                            System.out.println("預約失敗: " + e.getMessage());
                                        }
                                    }
                                    continue;
                                case 2:
                                    ArrayList<Appointment> prevs = new ArrayList<>();
                                    ArrayList<String> prevDocNames = new ArrayList<>();

                                    for(Doctor dd : doctors) {
                                        for(Appointment a : dd.getAppointments()) {
                                            if (a.getPatient() != null && a.getPatient().getId().equals(patients.get(pid).getId())) {
                                                prevs.add(a);
                                                prevDocNames.add(dd.getName());
                                            }
                                        }
                                    }

                                    if (prevs.isEmpty()) {
                                        System.out.println("找不到該病患的先前預約，請先建立一般預約");
                                    } else {
                                        System.out.println("--- 選擇先前預約作為回診參考 (顯示: 時段 / 醫師 / 建立時間) ---");

                                        for(int i = 0; i < prevs.size(); ++i) {
                                            Appointment a = prevs.get(i);
                                            String ts = a.getTimeSlot() == null ? "(無時段)" : a.getTimeSlot().getStartTime() + "~" + a.getTimeSlot().getEndTime();
                                            String docName = prevDocNames.get(i);
                                            System.out.println(i + 1 + ": " + ts + " / " + docName + " / 建立時間:" + a.getCreatedTime());
                                        }

                                        int prevChoice = readIntUpTo(scanner, "選擇先前預約編號 (1-" + prevs.size() + "): ", prevs.size());
                                        int prevIdx = prevChoice - 1;
                                        ArrayList<TimeSlot> followSlots = doc.getAvailableSlotsForPatient();
                                        if (followSlots.isEmpty()) {
                                            System.out.println("無可預約時段");
                                            continue;
                                        }

                                        System.out.println("--- 選擇回診時段 ---");

                                        for(int i = 0; i < followSlots.size(); ++i) {
                                            System.out.println(i + ": " + followSlots.get(i).getStartTime() + " ~ " + followSlots.get(i).getEndTime());
                                        }

                                        int fsid = readIntUpTo(scanner, "選擇時段編號: ", followSlots.size() - 1);
                                        TimeSlot fChosen = followSlots.get(fsid);
                                        FollowUpAppointment fua = new FollowUpAppointment(patients.get(pid), doc, fChosen, prevs.get(prevIdx));
                                        doc.getAppointments().add(fua);
                                        queue.addPatient(fua);
                                        System.out.println("回診預約成功，已加入等候佇列");
                                    }
                                    continue;
                                case 3:
                                    LocalDateTime sdt = readDateTime(scanner, "請輸入緊急時段起始時間（格式: YYYY-MM-DDTHH:MM）: ");
                                    LocalDateTime edt = readDateTime(scanner, "請輸入緊急時段結束時間（格式同上）: ");
                                    if (sdt != null && edt != null) {
                                        try {
                                            TimeSlot emSlot = new TimeSlot(sdt, edt);
                                            admin.addEmergencyAppointment(patients.get(pid), doc, emSlot, queue);
                                            System.out.println("已新增緊急掛號並加入佇列");
                                        } catch (Exception e) {
                                            System.out.println("新增緊急掛號失敗: " + e.getMessage());
                                        }
                                        continue;
                                    }

                                    System.out.println("取消新增緊急掛號");
                                    continue;
                                case 4:
                                    ArrayList<TimeSlot> tSlots = doc.getAvailableSlotsForPatient();
                                    if (tSlots.isEmpty()) {
                                        System.out.println("無可預約時段");
                                    } else {
                                        System.out.println("--- 選擇視訊時段 ---");

                                        for(int i = 0; i < tSlots.size(); ++i) {
                                            System.out.println(i + ": " + tSlots.get(i).getStartTime() + " ~ " + tSlots.get(i).getEndTime());
                                        }

                                        int tsid = readIntUpTo(scanner, "選擇時段編號: ", tSlots.size() - 1);
                                        TimeSlot tChosen = tSlots.get(tsid);
                                        TelemedicineAppointment ta = new TelemedicineAppointment(patients.get(pid), doc, tChosen);
                                        doc.getAppointments().add(ta);
                                        queue.addPatient(ta);
                                        System.out.println("視訊預約成功，已加入等候佇列");
                                    }
                            }
                        }
                        break;
                    case 5:
                        System.out.println("--- 新增緊急掛號 ---");
                        System.out.println("選擇病患: ");

                        for(int i = 0; i < patients.size(); ++i) {
                            System.out.println(i + ": " + patients.get(i).getName());
                        }

                        int epid = readIntUpTo(scanner, "選擇病患編號: ", patients.size() - 1);
                        System.out.println("選擇醫師: ");

                        for(Doctor d : doctors) {
                            String idStr = d.getDoctorId() == null ? "(no id)" : String.valueOf(d.getDoctorId());
                            PrintStream var137 = System.out;
                            String var142 = d.getName();
                            var137.println(var142 + " (id=" + idStr + ")");
                        }

                        int edocid = readIntUpTo(scanner, "輸入醫師編號 (0-50): ", 50);
                        Doctor edoc = null;

                        for(Doctor d : doctors) {
                            if (d.getDoctorId() != null && d.getDoctorId() == edocid) {
                                edoc = d;
                                break;
                            }
                        }

                        if (edoc == null) {
                            System.out.println("找不到醫師編號: " + edocid);
                        } else {
                            LocalDateTime sdt = readDateTime(scanner, "請輸入緊急時段起始時間（格式: YYYY-MM-DDTHH:MM）: ");
                            LocalDateTime edt = readDateTime(scanner, "請輸入緊急時段結束時間（格式同上）: ");
                            if (sdt != null && edt != null) {
                                try {
                                    TimeSlot emSlot = new TimeSlot(sdt, edt);
                                    admin.addEmergencyAppointment(patients.get(epid), edoc, emSlot, queue);
                                    System.out.println("已新增緊急掛號並加入佇列");
                                } catch (Exception e) {
                                    System.out.println("新增緊急掛號失敗: " + e.getMessage());
                                }
                                break;
                            }

                            System.out.println("取消新增緊急掛號");
                        }
                        break;
                    case 6:
                        if (queue.size() == 0) {
                            System.out.println("佇列為空");
                        } else {
                            Appointment next2 = queue.getNextPatient();
                            String type2 = getAppointmentType(next2);
                            PrintStream var136 = System.out;
                            String var141 = next2.getPatient().getName();
                            var136.println("呼叫: " + var141 + "（" + type2 + "）");
                            DiagnosisRecord d = new DiagnosisRecord(nextRecordId(), next2.getPatient(), drSmith, LocalDate.now(), "門診診斷");
                            PrescriptionRecord pr = new PrescriptionRecord(nextRecordId(), next2.getPatient(), drSmith, LocalDate.now(), "一般藥品");
                            ExaminationRecord er = new ExaminationRecord(nextRecordId(), next2.getPatient(), drSmith, LocalDate.now(), "一般檢查報告");
                            next2.getPatient().addMedicalRecord(d);
                            saveMedicalRecord(d);
                            next2.getPatient().addMedicalRecord(pr);
                            saveMedicalRecord(pr);
                            next2.getPatient().addMedicalRecord(er);
                            saveMedicalRecord(er);
                            Bill b = new Bill(next2.getPatient(), 1000.0, 0.7);
                            b.printReceipt();
                            System.out.println("-------------------");
                        }
                        break;
                    case 7:
                        viewPatientRecords(scanner, patients);
                        break;
                    case 8:
                        addMedicalRecordInteractive(scanner, patients, doctors);
                        break;
                    default:
                        System.out.println("無效選項");
                }
            }
        } catch (Exception e) {
            // 捕捉系統層級錯誤
            System.out.println("系統錯誤：" + e.getMessage());
        }
    }
    /**
     * 產生新的病歷編號
     * 格式：R + 日期 + 流水號
     */
    private static String nextRecordId() {
        String date = LocalDate.now().format(ID_DATE_FMT);
        int n = recordCounter.incrementAndGet();
        return "R" + date + "-" + String.format("%04d", n);
    }
    /**
     * 將病歷資料寫入 CSV 檔案
     */
    private static void saveMedicalRecord(MedicalRecord rec) {
        // 依照病歷類型寫入不同內容
        if (rec != null) {
            String type = rec.getClass().getSimpleName();
            String pid = rec.getPatient() == null ? "" : rec.getPatient().getId();
            String pname = rec.getPatient() == null ? "" : rec.getPatient().getName();
            String did = "";
            String dname = "";
            Doctor docObj = rec.getDoctor();
            if (docObj != null) {
                Integer docIdObj = docObj.getDoctorId();
                did = docIdObj == null ? docObj.getName() : String.valueOf(docIdObj);
                dname = docObj.getName();
            }

            String date = Optional.ofNullable(rec.getDate()).map(LocalDate::toString).orElse("");
            String content = "";
            if (rec instanceof DiagnosisRecord) {
                content = ((DiagnosisRecord)rec).getDiagnosis();
            } else if (rec instanceof PrescriptionRecord) {
                content = ((PrescriptionRecord)rec).getPrescription();
            } else if (rec instanceof ExaminationRecord) {
                content = ((ExaminationRecord)rec).getExaminationDetails();
            }

            String var10000 = String.join(",", rec.getRecordId(), type, pid, pname, did, dname, date, content);
            String line = var10000 + System.lineSeparator();

            try {
                Files.write(MEDICAL_FILE, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("無法寫入醫療紀錄檔: " + e.getMessage());
            }

        }
    }
    /**
     * 回傳預約類型的中文名稱
     */
    private static String getAppointmentType(Appointment next) {
        if (next instanceof EmergencyAppointment) {
            return "緊急掛號";
        } else if (next instanceof FollowUpAppointment) {
            return "回診";
        } else {
            return next instanceof TelemedicineAppointment ? "視訊看診" : "一般掛號";
        }
    }
    /**
     * 讀取不可為空的字串輸入
     */
    private static String readNonEmpty(Scanner scanner, String prompt) {
        while(true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }

            System.out.println("輸入不可為空");
        }
    }
    /**
     * 讀取指定範圍內的整數
     */
    private static int readIntUpTo(Scanner scanner, String prompt, int max) {
        int min = 0;

        while(true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();

            try {
                int v = Integer.parseInt(s);
                if (v >= min && v <= max) {
                    return v;
                }

                System.out.println("請輸入介於 " + min + " 到 " + max + " 的數字");
            } catch (NumberFormatException var6) {
                System.out.println("請輸入有效數字");
            }
        }
    }
    /**
     * 讀取日期時間格式輸入
     */
    private static LocalDateTime readDateTime(Scanner scanner, String prompt) {
        while(true) {
            System.out.print(prompt + " (輸入 q 取消): ");
            String s = scanner.nextLine().trim();
            if (s.equalsIgnoreCase("q")) {
                return null;
            }

            try {
                return LocalDateTime.parse(s);
            } catch (Exception var4) {
                System.out.println("格式錯誤，請使用 YYYY-MM-DDTHH:MM，例如 2025-12-16T11:00");
            }
        }
    }
    /**
     * 若病患不存在清單中則加入
     */
    private static void addIfMissing(ArrayList<Patient> list, Patient p) {
        for(Patient x : list) {
            if (x.getId().equals(p.getId())) {
                return;
            }
        }

        list.add(p);

        try {
            String var10000 = p.getId();
            String out = var10000 + "," + p.getName() + "," + p.getContactInfo() + "," + p.getAge() + System.lineSeparator();
            Files.write(PATIENTS_FILE, out.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException var4) {
            System.out.println("無法儲存病患（忽略）: " + var4.getMessage());
        }

    }
    /**
     * 產生隨機病患資料
     */
    private static Patient generateRandomPatient(ArrayList<Patient> existing) {
        Random rnd = new Random();
        int attempts = 0;

        String id;
        boolean ok;
        do {
            id = "PR" + String.format("%04d", rnd.nextInt(10000));
            ok = true;

            for(Patient p : existing) {
                if (p.getId().equals(id)) {
                    ok = false;
                    break;
                }
            }

            ++attempts;
        } while(attempts <= 20 && !ok);

        String name = "隨機病患" + rnd.nextInt(1000);
        String contact = "rand" + rnd.nextInt(10000) + "@example.com";
        int age = 1 + rnd.nextInt(100);
        return new Patient(id, name, contact, age);
    }
    /**
     * 顯示病患的所有病歷紀錄
     */
    private static void viewPatientRecords(Scanner scanner, ArrayList<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("無病患資料");
        } else {
            System.out.println("--- 病患清單 ---");

            for(int i = 0; i < patients.size(); ++i) {
                System.out.println(i + ": " + patients.get(i).getName());
            }

            int idx = readIntUpTo(scanner, "選擇病患編號: ", patients.size() - 1);
            Patient p = patients.get(idx);
            ArrayList<MedicalRecord> mh = p.getMedicalHistory();
            if (mh.isEmpty()) {
                System.out.println("此病患目前無病歷紀錄");
            } else {
                System.out.println("--- 病歷：" + p.getName() + " ---");

                for(MedicalRecord r : mh) {
                    String type = r.getClass().getSimpleName();
                    String id = r.getRecordId();
                    String date = r.getDate() == null ? "" : r.getDate().toString();
                    String doc = r.getDoctor() == null ? "" : r.getDoctor().getName();
                    String detail = recordDetail(r);
                    System.out.println(type + " | " + id + " | " + date + " | " + doc + " | " + detail);
                }

            }
        }
    }
    /**
     * 依病歷型態取得病歷內容文字
     */
    private static String recordDetail(MedicalRecord r) {
        if (r == null) {
            return "";
        } else if (r instanceof DiagnosisRecord) {
            return ((DiagnosisRecord)r).getDiagnosis();
        } else if (r instanceof PrescriptionRecord) {
            return ((PrescriptionRecord)r).getPrescription();
        } else {
            return r instanceof ExaminationRecord ? ((ExaminationRecord)r).getExaminationDetails() : "";
        }
    }
    /**
     * 互動式新增病歷紀錄
     */
    private static void addMedicalRecordInteractive(Scanner scanner, ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        System.out.println("--- 新增病歷紀錄 ---");

        Patient patient;
        int pid;
        for(patient = null; patient == null; patient = patients.get(pid)) {
            System.out.println("選擇病患:");

            for(int i = 0; i < patients.size(); ++i) {
                System.out.println(i + ": " + patients.get(i).getName());
            }

            pid = readIntUpTo(scanner, "輸入病患編號: ", patients.size() - 1);
        }

        Doctor doctor = null;

        while(doctor == null) {
            System.out.println("選擇醫師:");

            for(Doctor d : doctors) {
                String idStr = d.getDoctorId() == null ? "(no id)" : String.valueOf(d.getDoctorId());
                PrintStream var10000 = System.out;
                String var10001 = d.getName();
                var10000.println(var10001 + " (id=" + idStr + ")");
            }

            int did = readIntUpTo(scanner, "輸入醫師編號 (0-50): ", 50);

            for(Doctor d : doctors) {
                if (d.getDoctorId() != null && d.getDoctorId() == did) {
                    doctor = d;
                    break;
                }
            }
        }

        LocalDate date = null;

        while(date == null) {
            System.out.print("輸入日期 (YYYY-MM-DD) 或直接按 Enter 使用今天日期: ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.isEmpty()) {
                date = LocalDate.now();
            } else {
                try {
                    date = LocalDate.parse(dateInput);
                } catch (Exception var12) {
                    System.out.println("日期格式錯誤，請重新輸入");
                }
            }
        }

        System.out.println("選擇病歷類型:");
        System.out.println("1. 診斷紀錄");
        System.out.println("2. 處方籤");
        System.out.println("3. 檢查報告");
        int recordType = readIntUpTo(scanner, "輸入類型 (1-3): ", 3);
        String recordId = nextRecordId();
        String content = null;
        switch (recordType) {
            case 1:
                content = readNonEmpty(scanner, "輸入診斷內容: ");
                DiagnosisRecord diag = new DiagnosisRecord(recordId, patient, doctor, date, content);
                patient.addMedicalRecord(diag);
                saveMedicalRecord(diag);
                System.out.println("診斷紀錄已新增");
                break;
            case 2:
                content = readNonEmpty(scanner, "輸入處方內容: ");
                PrescriptionRecord pres = new PrescriptionRecord(recordId, patient, doctor, date, content);
                patient.addMedicalRecord(pres);
                saveMedicalRecord(pres);
                System.out.println("處方籤已新增");
                break;
            case 3:
                content = readNonEmpty(scanner, "輸入檢查報告內容: ");
                ExaminationRecord exam = new ExaminationRecord(recordId, patient, doctor, date, content);
                patient.addMedicalRecord(exam);
                saveMedicalRecord(exam);
                System.out.println("檢查報告已新增");
        }

    }
    // 初始化病歷編號日期格式
    static {
        ID_DATE_FMT = DateTimeFormatter.BASIC_ISO_DATE;
    }
}

