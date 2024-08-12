package cn.teleinfo.idhub.manage.doip.server.enums;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2023/11/16
 * @description DOIP标准和扩展type
 */
public enum DoType {

    DO("0.TYPE/DO"),
    // DOList("0.TYPE/DOList"),
    // Metadata("0.TYPE/DO.Metadata"),
    DOIP_SERVICE_INFO("0.TYPE/DO.DOIPServiceInfo"),
    DOIP_OPERATION("0.TYPE/DO.DOIPOperation"),
    
    DOIP_META("0.TYPE/DO.DOIPMETA"),
    UN_KNOWN("0.TYPE/UnKnow");

    private final String name;

    DoType(String displayName) {
        this.name = displayName;
    }

    public static DoType getDoType(String typeStr){
        for (DoType type : DoType.values()) {
            if (type.getName().equals(typeStr)) {
                return type;
            }
        }
        return DoType.UN_KNOWN;
    }

    public String getName() {
        return name;
    }
}
