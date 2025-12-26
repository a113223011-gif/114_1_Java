package clinic.actors;
/**
 * 抽象類別 Person
 * 作為診所系統中「人」的共同父類別
 * 例如：病人（Patient）、醫師（Doctor）、行政人員（AdminStaff）
 */
public abstract class Person {

    /** 人員唯一識別編號 */
    protected String id;

    /** 人員姓名 */
    protected String name;

    /** 聯絡資訊（例如 Email 或電話） */
    protected String contactInfo;

    /**
     * 建構子：建立一個 Person 物件
     *
     * @param id 人員編號
     * @param name 人員姓名
     * @param contactInfo 聯絡資訊
     */
    public Person(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    /**
     * 取得人員編號
     *
     * @return 人員 id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 取得人員姓名
     *
     * @return 人員 name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得人員聯絡資訊
     *
     * @return 聯絡資訊
     */
    public String getContactInfo() {
        return this.contactInfo;
    }
}

