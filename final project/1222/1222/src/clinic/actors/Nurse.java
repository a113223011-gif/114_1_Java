package clinic.actors;

/**
 * 護理師（Nurse）類別
 * 繼承自 Person
 * 目前僅作為角色識別，未額外擴充功能
 */
public class Nurse extends Person {

    /**
     * 建構子：建立一位護理師
     *
     * @param id 護理師編號
     * @param name 護理師姓名
     * @param contactInfo 聯絡資訊
     */
    public Nurse(String id, String name, String contactInfo) {
        super(id, name, contactInfo);
    }
}
