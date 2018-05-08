package top.sillyfan.service.chatroom.constants;

/**
 * 用户相关常量定义
 */
public class UserDef {

    /**
     * 性别
     */
    enum Gender {
        Unknown(0, "未知(保密)"),
        Male(1, "男性"),
        Female(2, "女性"),
        Other(3, "其他");

        public static Gender from(final Integer code) {
            for (Gender item : Gender.values()
                    ) {
                if (item.getCode().equals(code)) {
                    return item;
                }
            }
            return Unknown;
        }

        Gender(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private Integer code;

        private String desc;

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}
