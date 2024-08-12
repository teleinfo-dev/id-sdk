package cn.teleinfo.idhub.manage.doip.server.enums;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2023/11/16
 * @description DOIP标准操作类型和扩展操作类型
 */
public enum DoipOp {
    
    HELLO("0.DOIP/Op.Hello"),
    LIST_OPS("0.DOIP/Op.ListOperations"),
    RETRIEVE("0.DOIP/Op.Retrieve"),
    CREATE("0.DOIP/Op.Create"),
    UPDATE("0.DOIP/Op.Update"),
    DELETE("0.DOIP/Op.Delete"),
    SEARCH("0.DOIP/Op.Search"),
    // Extension("0.DOIP/Op.Extension"),
    UNKNOWN("0.DOIP/Op.Unknown");

    private final String name;

    DoipOp(String displayName) {
        this.name = displayName;
    }

    public static DoipOp getDoOp(String opStr){
        for (DoipOp op : DoipOp.values()) {
            if (op.getName().equals(opStr)) {
                return op;
            }
        }
        return DoipOp.UNKNOWN;
    }

    public String getName() {
        return name;
    }
}
